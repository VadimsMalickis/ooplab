
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TodoListService {
    private ArrayList<String> tasks;
    private final String filePath = "todo.csv";
    

    public TodoListService() {
        this.tasks = new ArrayList<>();
        loadTaskFromFile();
    }

    // Izlasīt todo.csv failu
    // Un papildināt tasks ArrayList ar datiem
    // no faila
    private void loadTaskFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                this.tasks.add(line);
            }
            
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage());
        }
    }

    private int getLastId() {
        String lastTask = this.tasks.get(this.tasks.size() -1);
        return Integer.valueOf(lastTask.split(",")[0]);
    }

    public void add(String task) {
        this.tasks.add(task);
    }

    public ArrayList<String> getTasks() {
        return this.tasks;
    }

    public void print() {
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println((i + 1) + ": " + this.tasks.get(i));
        }
    }

    // Pievienot updateFile() metodi
    // Kura atjauno/pārraksta .csv failu ar jauniem datiem
    // Izmantojot esošo tasks ArrayList masīvu
    private boolean updateFile() {
        return false;
    }

    // Rediģēt remove() metodi
    public void remove(int number) {
        if (number > 0 && number <= this.tasks.size()) {
            this.tasks.remove(number - 1);
        }
    }
}
