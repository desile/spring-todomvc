package ru.hh.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hh.school.dao.TodoDAOImpl;
import ru.hh.school.model.Todo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class TodoService {

    @Autowired
    private TodoDAOImpl todoDAO;

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public  @ResponseBody ResponseEntity<List<Todo>> getAll(@RequestParam(value = "completed", required = false) Boolean  completed,
                                            HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(todoDAO.listTodos(completed));
    }

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<Todo> add(@RequestBody Todo newTodo,
                            HttpServletRequest request, HttpServletResponse response) {
        if(newTodo.getId() != null){
            return ResponseEntity.unprocessableEntity().body(null);
        } else {
            todoDAO.save(newTodo);
            return ResponseEntity.ok(newTodo);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Todo> update(@RequestBody Todo newTodo,
                                     HttpServletRequest request, HttpServletResponse response) {
        if(newTodo.getId() == null){
            return ResponseEntity.unprocessableEntity().body(null);
        } else {
            todoDAO.update(newTodo);
            return ResponseEntity.ok(newTodo);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/todo", method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<Todo> delete(@RequestBody Todo newTodo,
                                                     HttpServletRequest request, HttpServletResponse response) {
        if(newTodo.getId() == null){
            return ResponseEntity.unprocessableEntity().body(null);
        } else {
            todoDAO.delete(newTodo);
            return ResponseEntity.ok(newTodo);
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/todo/clear_marked", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> clearMarked(HttpServletRequest request, HttpServletResponse response) {
        todoDAO.deleteCompleted();
        return ResponseEntity.ok(null);
    }

    @CrossOrigin
    @RequestMapping(value = "/todo/mark_all", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Void> completeAll(@RequestBody Boolean mark, HttpServletRequest request, HttpServletResponse response) {
        todoDAO.markAll(mark);
        return ResponseEntity.ok(null);
    }
}
