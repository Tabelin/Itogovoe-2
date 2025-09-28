package generator;

import data.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateStudent {
    private static final Random random = new Random();

    private static String generateRandomGroupNumber() {
        return "Гр" + (100 + random.nextInt(900));
    }

    private static float generateRandomAverageScore() {
        return 2.0f + random.nextFloat() * 3.0f; // от 2.0 до 5.0
    }

    private static String generateRandomReportCardNumber() {
        return (10000 + random.nextInt(90000))+"2025";
    }

    public static List<Student> generateRandomStudents(int count) {
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Student student = new Student.Builder()
                .setGroupNumber(generateRandomGroupNumber())
                .setAverageScore(generateRandomAverageScore())
                .setReportCardNumber(generateRandomReportCardNumber())
                .build();
            students.add(student);
        }
        return students;
    }

    public static void main(String[] args) {
        List<Student> students = generateRandomStudents(20);
        for (Student s : students) {
            System.out.println("Группа: " + s.getGroupNumber()
                + ", Средний балл: " + String.format("%.2f", s.getAverageScore())
                + ", Номер зачетки: " + s.getReportCardNumber());
        }
    }
}
