package mercury.storage;

import mercury.MercuryException;
import mercury.task.Deadline;
import mercury.task.Event;
import mercury.task.Task;
import mercury.task.TaskList;
import mercury.task.Todo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Handles the loading and saving of tasks to a file.
 */
public class Storage {
    private final String filePath;
    private final Path path;

    /**
     * Constructs a new Storage instance.
     *
     * @param filePath The path to the file used for storage.
     */
    public Storage(String filePath) {
        this.filePath = Objects.requireNonNull(filePath, "File path must not be null");
        this.path = Path.of(this.filePath);
    }

    /**
     * Loads tasks from the storage file.
     *
     * @return An ArrayList of tasks loaded from the file.
     * @throws MercuryException If the file cannot be accessed.
     */
    public ArrayList<Task> load() throws MercuryException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(path)) {
            return tasks;
        }
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                Task task = parseLine(scanner.nextLine());
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            throw new MercuryException("Error loading file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves the current list of tasks to the storage file.
     *
     * @param tasks The list of tasks to save.
     * @throws MercuryException If the file cannot be written to.
     */
    public void save(TaskList tasks) throws MercuryException {
        Objects.requireNonNull(tasks, "Task list must not be null");
        try {
            Path directory = path.getParent();
            if (directory != null) {
                Files.createDirectories(directory);
            }
            try (BufferedWriter writer = Files.newBufferedWriter(
                    path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Task task : tasks.getAllTasks()) {
                    writer.write(task.toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new MercuryException("Error saving file: " + e.getMessage());
        }
    }

    private Task parseLine(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            return null;
        }
        char type = parts[0].charAt(0);
        String status = parts[1];
        Task task = switch (type) {
            case 'T' -> new Todo(parts[2]);
            case 'D' -> parseDeadline(parts);
            case 'E' -> parseEvent(parts);
            default -> null;
        };
        if (task != null && "X".equals(status)) {
            task.markAsDone();
        }
        return task;
    }

    private Task parseDeadline(String[] parts) {
        if (parts.length < 4) {
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(parts[3]);
            return new Deadline(parts[2], date);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private Task parseEvent(String[] parts) {
        if (parts.length < 4) {
            return null;
        }
        String timeInfo = parts[3];
        if (timeInfo.startsWith("(from:") && timeInfo.contains(" to:") && timeInfo.endsWith(")")) {
            int toIndex = timeInfo.indexOf(" to:");
            String from = timeInfo.substring(6, toIndex);
            String to = timeInfo.substring(toIndex + 4, timeInfo.length() - 1);
            return new Event(parts[2], from, to);
        }
        return null;
    }
}
