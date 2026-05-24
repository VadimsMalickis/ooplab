package todo.service;

import java.util.List;

import todo.model.TodoItem;

public interface TodoService {
    List<TodoItem> getAll();

    TodoItem add(String task);

    TodoItem changeStatus(int id, String status);

    void remove(int id);
}
