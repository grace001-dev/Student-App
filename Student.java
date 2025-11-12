public class Student {
    private String name;
    private String id;
    private String course;

    public Student(String name, String id, String course) {
        this.name = name;
        this.id = id;
        this.course = course;
    }

    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public String getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + course;
    }

    public static Student fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) return null;
        return new Student(parts[1], parts[0], parts[2]);
    }
}



