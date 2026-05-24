package todo.service;

import todo.model.TodoItem;
import todo.repository.TodoRepository;

import java.util.List;

public class TodoListService implements TodoService {

    private final TodoRepository repository;

    public TodoListService(TodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<TodoItem> getAll() {
        return repository.findAll();
    }

    @Override
    public TodoItem add(String task) {
        return repository.insert(task);
    }

    @Override
    public TodoItem changeStatus(int id, String status) {
        return repository.updateStatus(id, status);
    }

    @Override
    public void remove(int id) {
        repository.deleteById(id);
    }
}
