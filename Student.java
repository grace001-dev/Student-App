import java.io.Serializable;

public class Student implements Serializable {
    private String name;
    private String id;
    private String course;
    private double gpa;

    public Student(String name, String id, String course, double gpa) {
        this.name = name;
        this.id = id;
        this.course = course;
        this.gpa = gpa;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public String getCourse() { return course; }
    public double getGPA() { return gpa; }
    public void setGPA(double gpa) { this.gpa = gpa; }

    @Override
    public String toString() {
        return id + "," + name + "," + course + "," + String.format("%.2f", gpa);
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            try {
                double gpa = Double.parseDouble(parts[3]);
                return new Student(parts[1], parts[0], parts[2], gpa);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[a-zA-Z\\s'-]+$") && name.length() >= 2 && name.length() <= 50;
    }

    public static boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return id.matches("^[a-zA-Z0-9]{3,10}$");
    }

    public static boolean isValidCourse(String course) {
        if (course == null || course.trim().isEmpty()) {
            return false;
        }
        return course.matches("^[a-zA-Z0-9\\s'-]+$") && course.length() >= 2 && course.length() <= 50;
    }

    public static boolean isValidGPA(String gpaStr) {
        if (gpaStr == null || gpaStr.trim().isEmpty()) {
            return false;
        }
        try {
            double gpa = Double.parseDouble(gpaStr);
            return gpa >= 0.0 && gpa <= 4.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getValidationError(String name, String id, String course, String gpaStr) {
        if (!isValidName(name)) {
            return "Invalid Name: Must be 2-50 characters, letters only (no numbers).";
        }
        if (!isValidId(id)) {
            return "Invalid ID: Must be 3-10 alphanumeric characters (no spaces).";
        }
        if (!isValidCourse(course)) {
            return "Invalid Course: Must be 2-50 characters, letters and numbers only.";
        }
        if (!isValidGPA(gpaStr)) {
            return "Invalid GPA: Must be a number between 0.0 and 4.0.";
        }
        return null;
    }
}

