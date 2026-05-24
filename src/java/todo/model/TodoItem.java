package todo.model;

public class TodoItem {
    private final int id;
    private final String task;
    private String status;
    private final String addedAt;


    public TodoItem(int id, String task, String status, String addedAt) {
        this.id = id;
        this.task = task;
        this.status = status;
        this.addedAt = addedAt;
    }

    public static Builder builder() {
        return new Builder();
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

    public static class Builder {
        private int id;
        private String task;
        private String status;
        private String addedAt;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder task(String task) {
            this.task = task;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder addedAt(String addedAt) {
            this.addedAt = addedAt;
            return this;
        }

        public TodoItem build() {
            return new TodoItem(id, task, status, addedAt);
        }
    }
    
}
