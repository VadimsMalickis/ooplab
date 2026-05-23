package todo.model;

public class TodoItem {
    private final int id;
    private final String task;

    public TodoItem(int id, String task) {
        this.id = id;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }
}
