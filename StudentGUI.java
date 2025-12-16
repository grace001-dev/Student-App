import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class StudentGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Record System");
        frame.setSize(450, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Input fields
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField gpaField = new JTextField();
        JTextField searchField = new JTextField();
        JTextField searchNameField = new JTextField();
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton saveBtn = new JButton("Save Student");
        JButton searchBtn = new JButton("Search by ID");
        JButton searchNameBtn = new JButton("Search by Name");
        JButton viewAllBtn = new JButton("View All");
        JButton deleteBtn = new JButton("Delete Student");
        JTextField deleteField = new JTextField();

        // Labels and placement
        frame.add(new JLabel("Name:")).setBounds(20, 20, 100, 25);
        nameField.setBounds(150, 20, 250, 25);
        frame.add(nameField);

        frame.add(new JLabel("Student ID:")).setBounds(20, 60, 100, 25);
        idField.setBounds(150, 60, 250, 25);
        frame.add(idField);

        frame.add(new JLabel("Course:")).setBounds(20, 100, 100, 25);
        courseField.setBounds(150, 100, 250, 25);
        frame.add(courseField);

        frame.add(new JLabel("GPA:")).setBounds(20, 140, 100, 25);
        gpaField.setBounds(150, 140, 250, 25);
        frame.add(gpaField);

        saveBtn.setBounds(150, 180, 250, 30);
        frame.add(saveBtn);

        frame.add(new JLabel("Search by ID:")).setBounds(20, 230, 100, 25);
        searchField.setBounds(150, 230, 250, 25);
        frame.add(searchField);

        searchBtn.setBounds(150, 270, 250, 30);
        frame.add(searchBtn);

        frame.add(new JLabel("Search by Name:")).setBounds(20, 310, 100, 25);
        searchNameField.setBounds(150, 310, 250, 25);
        frame.add(searchNameField);

        searchNameBtn.setBounds(150, 350, 250, 30);
        frame.add(searchNameBtn);

        viewAllBtn.setBounds(150, 390, 250, 30);
        frame.add(viewAllBtn);

        frame.add(new JLabel("Delete by ID:")).setBounds(20, 430, 100, 25);
        deleteField.setBounds(150, 430, 250, 25);
        frame.add(deleteField);

        deleteBtn.setBounds(150, 470, 250, 30);
        frame.add(deleteBtn);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(20, 510, 400, 80);
        frame.add(scrollPane);

        frame.setVisible(true);

        // Button actions
        saveBtn.addActionListener(_ -> {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            String course = courseField.getText().trim();
            String gpa = gpaField.getText().trim();

            String validationError = Student.getValidationError(name, id, course, gpa);
            if (validationError != null) {
                JOptionPane.showMessageDialog(frame, validationError, "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Student s = new Student(name, id, course, Double.parseDouble(gpa));
            try {
                StudentDatabase.saveStudent(s);
                JOptionPane.showMessageDialog(frame, "Student saved!");
                nameField.setText("");
                idField.setText("");
                courseField.setText("");
                gpaField.setText("");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error saving student.");
            }
        });

        searchBtn.addActionListener(_ -> {
            String searchId = searchField.getText().trim();
            if (searchId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter ID to search!");
                return;
            }

            try {
                Student found = StudentDatabase.searchStudentById(searchId);
                if (found != null) {
                    outputArea.setText("Found Student:\n" +
                            "ID: " + found.getId() + "\n" +
                            "Name: " + found.getName() + "\n" +
                            "Course: " + found.getCourse() + "\n" +
                            "GPA: " + String.format("%.2f", found.getGPA()));
                } else {
                    outputArea.setText("No student found with ID: " + searchId);
                }
            } catch (IOException e) {
                outputArea.setText("Error searching student.");
            }
        });

        searchNameBtn.addActionListener(_ -> {
            String searchName = searchNameField.getText().trim();
            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter name to search!");
                return;
            }

            try {
                List<Student> found = StudentDatabase.searchStudentByName(searchName);
                if (!found.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Found ").append(found.size()).append(" student(s):\n\n");
                    for (Student s : found) {
                        sb.append("ID: ").append(s.getId())
                          .append(" | Name: ").append(s.getName())
                          .append(" | Course: ").append(s.getCourse())
                          .append(" | GPA: ").append(String.format("%.2f", s.getGPA()))
                          .append("\n");
                    }
                    outputArea.setText(sb.toString());
                } else {
                    outputArea.setText("No students found with name containing: " + searchName);
                }
            } catch (IOException e) {
                outputArea.setText("Error searching students.");
            }
        });

        viewAllBtn.addActionListener(_ -> {
            try {
                List<Student> students = StudentDatabase.getAllStudents();
                if (students.isEmpty()) {
                    outputArea.setText("No records found.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("All Students:\n");
                    for (Student s : students) {
                        sb.append("ID: ").append(s.getId())
                          .append(" | Name: ").append(s.getName())
                          .append(" | Course: ").append(s.getCourse())
                          .append(" | GPA: ").append(String.format("%.2f", s.getGPA()))
                          .append("\n");
                    }
                    outputArea.setText(sb.toString());
                }
            } catch (IOException e) {
                outputArea.setText("Error loading records.");
            }
        });

        deleteBtn.addActionListener(_ -> {
            String deleteId = deleteField.getText().trim();
            if (deleteId.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter ID to delete!");
                return;
            }

            try {
                boolean deleted = StudentDatabase.deleteStudent(deleteId);
                if (deleted) {
                    JOptionPane.showMessageDialog(frame, "Student deleted successfully!");
                    outputArea.setText("Student with ID " + deleteId + " has been deleted.");
                    deleteField.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "No student found with ID: " + deleteId);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(frame, "Error deleting student.");
            }
        });
    }
}


