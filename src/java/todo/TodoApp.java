package todo;

import todo.repository.TodoSQLite;
import todo.repository.TodoRepository;
import todo.service.TodoListService;
import todo.service.TodoService;
import todo.views.TodoUI;

public class TodoApp {
    public static void main(String[] args) {
        TodoRepository repository = TodoSQLite.getInstance();
        TodoService service = new TodoListService(repository);
        TodoUI todo = new TodoUI(service);
        todo.start();
    }
}
