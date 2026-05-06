public class TodoApp {
    public static void main(String[] args) throws Exception {
        TodoUI todo = new TodoUI(new TodoListService());
        todo.start();
    }
}
