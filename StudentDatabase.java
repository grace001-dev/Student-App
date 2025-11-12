import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase {
    private static final String FILE_NAME = "students.txt";

    public static void saveStudent(Student s) throws IOException {
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(s.getId() + "," + s.getName() + "," + s.getCourse());
            bw.newLine();
        }
    }

    public static Student searchStudentById(String id) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(id)) {
                    return new Student(parts[1], parts[0], parts[2]);
                }
            }
        }
        return null;
    }

    public static List<Student> getAllStudents() throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    students.add(new Student(parts[1], parts[0], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            // File might not exist yet, return empty list
        }
        return students;
    }
}




