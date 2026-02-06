import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.ArrayList;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws MercuryException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length < 3) {
                    continue; // corrupt
                }

                String type = parts[0];
                String status = parts[1];
                String description = parts[2];
                
                Task task = null;
                switch (type) {
                    case "T":
                        task = new Todo(description);
                        break;
                    case "D":
                         if (parts.length >= 4) {
                            task = new Deadline(description, LocalDate.parse(parts[3]));
                         }
                        break;
                    case "E":
                        if (parts.length >= 4) {
                            String timeInfo = parts[3];
                            if (timeInfo.startsWith("(from:") && timeInfo.contains(" to:") && timeInfo.endsWith(")")) {
                                int toIndex = timeInfo.indexOf(" to:");
                                String from = timeInfo.substring(6, toIndex);
                                String to = timeInfo.substring(toIndex + 4, timeInfo.length() - 1);
                                task = new Event(description, from, to);
                            }
                        }
                        break;
                }
                
                if (task != null) {
                    if (status.equals("X")) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
        } catch (FileNotFoundException e) {
            throw new MercuryException("Error loading file");
        } catch (Exception e) {
            // ignore corrupt lines? or warn? 
            // Previous implementation warned, keeping it simple here or re-implement warning if needed.
            // But Storage usually just throws Exception to main, or handles it quietly.
        }
        return tasks;
    }

    public void save(TaskList tasks) throws MercuryException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks.getAllTasks()) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new MercuryException("Error saving file: " + e.getMessage());
        }
    }
}
