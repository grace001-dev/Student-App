import java.io.*;
import java.util.*;

public class StudentDatabase {
    private static final String FILE_NAME = "students.txt";

    public static void saveStudent(Student s) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(s.toString());
            writer.newLine();
        }
    }

    public static Student searchStudentById(String id) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromString(line);
                if (s != null && s.getId().equalsIgnoreCase(id)) {
                    return s;
                }
            }
        }
        return null;
    }

    public static List<Student> searchStudentByName(String name) throws IOException {
        List<Student> results = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromString(line);
                if (s != null && s.getName().toLowerCase().contains(name.toLowerCase())) {
                    results.add(s);
                }
            }
        }
        return results;
    }

    public static List<Student> getAllStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromString(line);
                if (s != null) {
                    students.add(s);
                }
            }
        }
        return students;
    }

    public static boolean deleteStudent(String id) throws IOException {
        List<Student> students = getAllStudents();
        boolean found = students.removeIf(s -> s.getId().equalsIgnoreCase(id));
        
        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Student s : students) {
                    writer.write(s.toString());
                    writer.newLine();
                }
            }
        }
        return found;
    }
}
