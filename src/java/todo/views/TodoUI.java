package todo.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import todo.model.TodoItem;
import todo.repository.TodoDB;
import todo.service.TodoListService;

import java.awt.*;
import java.util.List;

public class TodoUI {
    private final TodoListService service;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField taskInput;

    public TodoUI(TodoListService service) {
        this.service = service;
    }

    public void start() {
        frame = new JFrame("Todo List");
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createComponents();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void createComponents() {
        String[] headers = {TodoDB.COL_ID, TodoDB.COL_TASK, TodoDB.COL_STATUS, TodoDB.COL_ADDED_AT};
        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        loadTableData();

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMaxWidth(60);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout());
        frame.add(panel, BorderLayout.SOUTH);

        taskInput = new JTextField(20);
        panel.add(taskInput);

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        panel.add(addButton);
        panel.add(removeButton);

        addButton.addActionListener(e -> addTask());
        removeButton.addActionListener(e -> removeTask());
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<TodoItem> items = service.getAll();
        for (TodoItem item : items) {
            tableModel.addRow(new Object[]{item.getId(), item.getTask(), item.getStatus(), item.getAddedAt()});
        }
    }

    private void addTask() {
        String task = taskInput.getText().trim();
        if (validateTask(task)) {
            TodoItem added = service.add(task);
            tableModel.addRow(new Object[]{
                added.getId(), added.getTask(), added.getStatus(), added.getAddedAt()
            });
            taskInput.setText("");
        } else {
            JOptionPane.showMessageDialog(
                frame,
                "Invalid task. Must be at least 3 characters and contain only letters, numbers, and spaces.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void removeTask() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Select a task to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        service.remove(id);
        tableModel.removeRow(selectedRow);
    }

    private boolean validateTask(String value) {
        if (value == null || value.length() < 3) return false;
        return value.matches("[a-zA-Z0-9 ]+");
    }
}

