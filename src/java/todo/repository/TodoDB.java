package todo.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import todo.model.TodoItem;

public class TodoDB {

    private static final String DB_URL = "jdbc:sqlite:todo.db";
    public static final String TABLE_NAME = "todo";
    public static final String COL_ID = "id";
    public static final String COL_TASK = "task";
    public static final String COL_STATUS = "status";
    public static final String COL_ADDED_AT = "added_at";

    public TodoDB() {
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initSchema() {
        StringBuilder sql = new StringBuilder()
            .append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(")
            .append(COL_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(COL_TASK).append(" TEXT NOT NULL,")
            .append(COL_STATUS).append(" TEXT NOT NULL CHECK (status IN ('in progress', 'completed', 'canceled'))")
            .append(" DEFAULT 'in progress',")
            .append(COL_ADDED_AT).append(" TEXT NOT NULL DEFAULT (datetime('now', 'localtime'))")
            .append(")");
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql.toString());
        } catch (SQLException e) {
            throw new RuntimeException("Schema init failed: " + e.getMessage());
        }
    }

    public List<TodoItem> findAll() {
        List<TodoItem> items = new ArrayList<>();
        StringBuilder sql = new StringBuilder()
            .append("SELECT ").append(COL_ID)
            .append(", ").append(COL_TASK)
            .append(", ").append(COL_STATUS)
            .append(", ").append(COL_ADDED_AT)
            .append(" FROM ").append(TABLE_NAME)
            .append(" ORDER BY ").append(COL_ADDED_AT);
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(
                    new TodoItem(
                        rs.getInt(COL_ID),
                        rs.getString(COL_TASK),
                        rs.getString(COL_STATUS),
                        rs.getString(COL_ADDED_AT)
                    )
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load tasks: " + e.getMessage());
        }
        return items;
    }

    public TodoItem insert(String task) {
        StringBuilder sql = new StringBuilder()
            .append("INSERT INTO ").append(TABLE_NAME)
            .append(" (").append(COL_TASK).append(")")
            .append(" VALUES (?)");
        try (Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, task);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int insertedId = keys.getInt(1);
                    return findById(insertedId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert task: " + e.getMessage());
        }
        throw new RuntimeException("Insert failed: no generated key returned.");
    }

    private TodoItem findById(int id) {
        StringBuilder sql = new StringBuilder()
            .append("SELECT ").append(COL_ID)
            .append(", ").append(COL_TASK)
            .append(", ").append(COL_STATUS)
            .append(", ").append(COL_ADDED_AT)
            .append(" FROM ").append(TABLE_NAME)
            .append(" WHERE ").append(COL_ID).append(" = ?");
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new TodoItem(
                        rs.getInt(COL_ID),
                        rs.getString(COL_TASK),
                        rs.getString(COL_STATUS),
                        rs.getString(COL_ADDED_AT)
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load inserted task: " + e.getMessage());
        }
        throw new RuntimeException("Insert succeeded, but inserted task could not be reloaded.");
    }

    public void deleteById(int id) {
        StringBuilder sql = new StringBuilder()
            .append("DELETE FROM ").append(TABLE_NAME)
            .append(" WHERE ").append(COL_ID).append(" = ?");
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task: " + e.getMessage());
        }
    }
}
