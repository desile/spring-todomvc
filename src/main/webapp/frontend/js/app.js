/*global Vue, todoStorage */

(function (exports) {

	'use strict';

	var filters = {
		all: function (todos) {
			return todos;
		},
		active: function (todos) {
			return todos.filter(function (todo) {
				return !todo.completed;
			});
		},
		completed: function (todos) {
			return todos.filter(function (todo) {
				return todo.completed;
			});
		}
	};

	exports.app = new Vue({

		// the root element that will be compiled
		el: '.todoapp',

		// app initial state
		data: {
			todos: [],
			newTodo: '',
			editedTodo: null,
			visibility: 'all',
            allDone: false
		},

		created: function () {
			this.loadTodos();
		},

		// computed properties
		// http://vuejs.org/guide/computed.html
		computed: {
			filteredTodos: function () {
				return filters[this.visibility](this.todos);
			},
			remaining: function () {
				return filters.active(this.todos).length;
			},
			allDone: {
				get: function () {
					return this.remaining === 0;
				},
				set: function (value) {
                    var self = this;
                    fetch('http://localhost:9999/api/todo/mark_all', {
                        mode: 'cors',
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(value)
                    }).then(function(response) {
                        if (response.ok){
                            self.todos.forEach(function (todo) {
                                todo.completed = value;
                            });
                        }
                    })

				}
			}
		},

		// methods that implement data logic.
		// note there's no DOM manipulation here at all.
		methods: {

			loadTodos: function () {
                var self = this;
                fetch('http://localhost:9999/api/todo', {
                    mode: 'cors',
                    method: 'GET'
                }).then(function(response) {
                    return response.json()
                }).then(function(body){
                	self.todos = body
                })
			},

			updateTodo: function (todo) {
                var self = this;
                fetch('http://localhost:9999/api/todo', {
                    mode: 'cors',
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(todo)
                }).then(function(response) {
                    return response.json()
                }).catch(function(){
                	self.cancelEdit(todo)
				})
			},


			pluralize: function (word, count) {
				return word + (count === 1 ? '' : 's');
			},

			addTodo: function () {
				var value = this.newTodo && this.newTodo.trim();
				var self = this;
				if (!value) {
					return;
				}
				var todoToAdd = { title: value, completed: false };
                fetch('http://localhost:9999/api/todo', {
                    mode: 'cors',
                    method: 'PUT',
					headers: {
                    	'Content-Type': 'application/json'
					},
					body: JSON.stringify(todoToAdd)
                }).then(function(response) {
                	if (response.ok){
                        return response.json()
                    }
                }).then(function(addedTodo){
                    self.todos.push(addedTodo);
                    self.newTodo = ''
                })
			},

			removeTodo: function (todo) {
				var self = this;
                fetch('http://localhost:9999/api/todo', {
                    mode: 'cors',
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(todo)
                }).then(function(response) {
                    if (response.ok){
                        var index = self.todos.indexOf(todo);
                        self.todos.splice(index, 1);
					}
                })
			},

			saveEditCache: function (todo) {
                this.beforeEditCache = {
                    title: todo.title,
                    completed: todo.completed
                };
			},

			editTodo: function (todo) {
				this.saveEditCache(todo);
				this.editedTodo = todo;
			},

			toggleCompleted: function (todo) {
                this.saveEditCache(todo);
				todo.completed = !todo.completed;
				this.updateTodo(todo);
			},

			doneEdit: function (todo) {
				if (!this.editedTodo) {
					return;
				}
				this.editedTodo = null;
				todo.title = todo.title.trim();
				if (!todo.title) {
					this.removeTodo(todo);
				} else {
					this.updateTodo(todo);
				}
			},

			cancelEdit: function (todo) {
				this.editedTodo = null;
				todo.title = this.beforeEditCache.title;
				todo.completed = this.beforeEditCache.completed;
			},

			removeCompleted: function () {
                var self = this;
                fetch('http://localhost:9999/api/todo/clear_marked', {
                    mode: 'cors',
                    method: 'POST'
                }).then(function(response) {
                	if (response.ok){
                        self.todos = filters.active(self.todos);
					}
                })
			}
		},

		// a custom directive to wait for the DOM to be updated
		// before focusing on the input field.
		// http://vuejs.org/guide/custom-directive.html
		directives: {
			'todo-focus': function (el, binding) {
				if (binding.value) {
					el.focus();
				}
			}
		}
	});

})(window);
