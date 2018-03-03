package ru.hh.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hh.school.dao.TodoDAOImpl;
import ru.hh.school.model.Todo;
import java.util.List;

@RequestMapping(value = "/api")
@RestController
public class TodoService {

    @Autowired
    private TodoDAOImpl todoDAO;

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public  @ResponseBody ResponseEntity<List<Todo>> getAll(@RequestParam(value = "completed", required = false) Boolean  completed) {
        return ResponseEntity.ok(todoDAO.listTodos(completed));
    }

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Todo> add(@RequestBody Todo newTodo) {
        if(newTodo.getId() != null){
            return ResponseEntity.unprocessableEntity().body(null);
        } else {
            todoDAO.save(newTodo);
            return ResponseEntity.ok(newTodo);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Todo> update(@RequestBody Todo newTodo) {
        if(newTodo.getId() == null){
            return ResponseEntity.unprocessableEntity().body(null);
        } else {
            todoDAO.update(newTodo);
            return ResponseEntity.ok(newTodo);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Todo> delete(@RequestBody Todo newTodo) {
        if(newTodo.getId() == null){
            return ResponseEntity.unprocessableEntity().body(null);
        } else {
            todoDAO.delete(newTodo);
            return ResponseEntity.ok(newTodo);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/todo/clear_marked", method = RequestMethod.POST)
    public ResponseEntity<Void> clearMarked() {
        todoDAO.deleteCompleted();
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @RequestMapping(value = "/todo/mark_all", method = RequestMethod.POST)
    public ResponseEntity<Void> completeAll(@RequestBody Boolean mark) {
        todoDAO.markAll(mark);
        return ResponseEntity.ok().build();
    }
}
