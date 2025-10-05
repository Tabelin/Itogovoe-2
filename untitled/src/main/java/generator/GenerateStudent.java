package generator;

import data.Student;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateStudent {
    private static final Random random = new Random();

    private static String generateRandomGroupNumber() {
    return String.valueOf(100 + random.nextInt(900));
    }

    private static float generateRandomAverageScore() {
        float raw = 2.0f + random.nextFloat() * 3.0f; // от 2.0 до 5.0
        return Math.round(raw * 100) / 100.0f;
    }

    private static Integer generateRandomReportCardNumber() {
        return 10000 + random.nextInt(90000);
    }

    public static List<Student> generateRandomStudents(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Student.Builder()
            .setGroupNumber(generateRandomGroupNumber())
            .setAverageScore(generateRandomAverageScore())
            .setReportCardNumber(generateRandomReportCardNumber())
            .build())
        .collect(Collectors.toList());
    }
}
