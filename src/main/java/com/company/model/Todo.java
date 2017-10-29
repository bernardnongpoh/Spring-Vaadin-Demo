package com.company.model;

import javax.persistence.*;

/**
 * Created by bernard-w on 29/10/17.
 */
@Entity
@Table(name = "TODO")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer todoId; // in your DB it should be todo_id

    private String task;

    public Integer getTodoId() {
        return todoId;
    }

    public void setTodoId(Integer todoId) {
        this.todoId = todoId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
       return task;
    }
}
