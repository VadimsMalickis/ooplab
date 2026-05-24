package todo.service;

public class TaskValidationService {

    public boolean isValidTask(String value) {
        if (value == null || value.length() < 3) {
            return false;
        }
        return value.matches("[a-zA-Z0-9 ]+");
    }
}
