package todo;

import todo.service.TodoListService;
import todo.views.TodoUI;

public class TodoApp {
    public static void main(String[] args) {
        TodoUI todo = new TodoUI(new TodoListService());
        todo.start();
    }
}
