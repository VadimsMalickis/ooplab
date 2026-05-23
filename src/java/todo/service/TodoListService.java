package todo.service;

import todo.model.TodoItem;
import todo.repository.TodoDB;

import java.util.List;

public class TodoListService {

    private final TodoDB db;

    public TodoListService() {
        this.db = new TodoDB();
    }

    public List<TodoItem> getAll() {
        return db.findAll();
    }

    public TodoItem add(String task) {
        return db.insert(task);
    }

    public void remove(int id) {
        db.deleteById(id);
    }
}
