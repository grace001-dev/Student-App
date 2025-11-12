import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class StudentGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Record System");
        frame.setSize(450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Input fields
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField searchField = new JTextField();
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);

        JButton saveBtn = new JButton("Save Student");
        JButton searchBtn = new JButton("Search by ID");
        JButton viewAllBtn = new JButton("View All");

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

        saveBtn.setBounds(150, 140, 250, 30);
        frame.add(saveBtn);

        frame.add(new JLabel("Search by ID:")).setBounds(20, 190, 100, 25);
        searchField.setBounds(150, 190, 250, 25);
        frame.add(searchField);

        searchBtn.setBounds(150, 230, 250, 30);
        frame.add(searchBtn);

        viewAllBtn.setBounds(150, 270, 250, 30);
        frame.add(viewAllBtn);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(20, 320, 400, 120);
        frame.add(scrollPane);

        frame.setVisible(true);

        // Button actions
        saveBtn.addActionListener(_ -> {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            String course = courseField.getText().trim();

            if (name.isEmpty() || id.isEmpty() || course.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required!");
                return;
            }

            Student s = new Student(name, id, course);
            try {
                StudentDatabase.saveStudent(s);
                JOptionPane.showMessageDialog(frame, "Student saved!");
                nameField.setText("");
                idField.setText("");
                courseField.setText("");
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
                            "Course: " + found.getCourse());
                } else {
                    outputArea.setText("No student found with ID: " + searchId);
                }
            } catch (IOException e) {
                outputArea.setText("Error searching student.");
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
                          .append("\n");
                    }
                    outputArea.setText(sb.toString());
                }
            } catch (IOException e) {
                outputArea.setText("Error loading records.");
            }
        });
    }
}


