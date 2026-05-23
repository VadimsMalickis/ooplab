package todo.model;

public class TodoItem {
    private final int id;
    private final String task;
    private String status;
    private final String addedAt;


    public TodoItem(int id, String task, String status, String addedAt) {
        this.id = id;
        this.task = task;
        this.addedAt = addedAt;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getAddedAt() {
        return this.addedAt;
    }
    
}
