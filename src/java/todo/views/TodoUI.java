package todo.views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import todo.model.TodoItem;
import todo.service.TaskValidationService;
import todo.service.TodoService;

import java.awt.*;
import java.util.List;

public class TodoUI {
    private static final String[] TABLE_HEADERS = {"id", "task", "status", "added_at"};

    private final TodoService service;
    private final TaskValidationService validationService;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField taskInput;

    public TodoUI(TodoService service, TaskValidationService validationService) {
        this.service = service;
        this.validationService = validationService;
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
        tableModel = new DefaultTableModel(TABLE_HEADERS, 0) {
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
        if (validationService.isValidTask(task)) {
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
}

