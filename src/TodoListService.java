
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TodoListService {
    private ArrayList<String> tasks;
    private final String filePath = "todo.csv";

    public TodoListService() {
        this.tasks = new ArrayList<>();
        loadTasksFromFile();
    }

    private void loadTasksFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                this.tasks.add(line);
            }
            
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    public void add(String task) {
        this.tasks.add(getLastId() + "," + task);
    }

    public ArrayList<String> getTasks() {
        return this.tasks;
    }

    public void print() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + ": " + this.tasks.get(i));
        }
    }

    public void remove(int number) {
        if (number > 0 && number <= this.tasks.size()) {
            this.tasks.remove(number - 1);
        }
    }
    private boolean updateFile() {
        return false;
    }
    
    private int getLastId() {
        String lastTask = this.tasks.get(this.tasks.size() -1);
        return Integer.valueOf(lastTask.split(",")[0]);
    }
}
