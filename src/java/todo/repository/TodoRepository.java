package todo.repository;

import java.util.List;

import todo.model.TodoItem;

public interface TodoRepository {
    List<TodoItem> findAll();

    TodoItem insert(String task);

    void deleteById(int id);
}
