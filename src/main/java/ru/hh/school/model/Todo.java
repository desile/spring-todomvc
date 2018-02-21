package ru.hh.school.model;

import javax.persistence.*;

@Entity(name="todo")
public class Todo {

    private Integer id;
    private String title;
    private Boolean completed;

    public Todo(){

    }

    public Todo(int id, String title, boolean completed){
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public Todo(String title){
        this.title = title;
        this.completed = false;
    }

    @Id
    @GeneratedValue
    @Column(name="id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name="completed")
    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
