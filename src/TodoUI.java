
import javax.swing.*;
import java.awt.*;

public class TodoUI {
    private TodoListService todoList;
    private JFrame frame;
    private JTable table;
    private JTextField taskInput;

    public TodoUI(TodoListService todoList) {
        this.todoList = todoList;
    }

    public void start() {
        frame = new JFrame("Todo List");
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents(frame);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String[][] arrayListToTableData() {
        int columns = 2;
        int rows = this.todoList.getTasks().size();
        String[][] data = new String[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (String task : todoList.getTasks()) {
                String[] parts = task.split(",");
                data[i][0] = parts[0];
                data[i][1] = parts[1];
            }
        }
        return data;
    }

    private void createComponents(JFrame frame) {
     
        String[] headerRow = {"id", "task"};
        String[][] data = arrayListToTableData();
        
        this.table = new JTable(data, headerRow);
        
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout());
        frame.add(panel, BorderLayout.SOUTH);

        taskInput = new JTextField(20);
        panel.add(taskInput);

        JButton addButton = new JButton("Add");
        panel.add(addButton);

        JButton removeButton = new JButton("Remove");
        panel.add(removeButton);

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
    }

    private void addTask() {
        String task = taskInput.getText();
        if (validateTask(task)) {
            todoList.add(task);
            // TODO add to table
            taskInput.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid task. Must be at least 3 characters long and contain only letters, numbers, and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeTask() {
        // Remove from table
        // Remove from file
    }

    private boolean validateTask(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("[a-zA-Z0-9 ]+");
    }

   
}
