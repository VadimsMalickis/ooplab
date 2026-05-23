package todo.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import todo.model.TodoItem;

public class TodoDB {

    private static final String DB_URL = "jdbc:sqlite:todo.db";

    public TodoDB() {
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS todo ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "task TEXT NOT NULL)";
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Schema init failed: " + e.getMessage());
        }
    }

    public List<TodoItem> findAll() {
        List<TodoItem> items = new ArrayList<>();
        String sql = "SELECT id, task FROM todo ORDER BY id";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new TodoItem(rs.getInt("id"), rs.getString("task")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load tasks: " + e.getMessage());
        }
        return items;
    }

    public TodoItem insert(String task) {
        String sql = "INSERT INTO todo (task) VALUES (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, task);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return new TodoItem(keys.getInt(1), task);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert task: " + e.getMessage());
        }
        throw new RuntimeException("Insert failed: no generated key returned.");
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task: " + e.getMessage());
        }
    }
}
