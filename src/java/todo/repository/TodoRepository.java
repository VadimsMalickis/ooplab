package todo.repository;

import java.util.List;

import todo.model.TodoItem;

public interface TodoRepository {
    List<TodoItem> findAll();

    TodoItem insert(String task);

    TodoItem updateStatus(int id, String status);

    void deleteById(int id);
}
