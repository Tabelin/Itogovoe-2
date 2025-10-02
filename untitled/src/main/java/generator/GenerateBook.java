package generator;

import data.Book;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateBook {
    private static final Random random = new Random();

    private static final String[] TITLES = {
        "Война и мир", "Преступление и наказание", "Мастер и Маргарита",
        "Анна Каренина", "Обломов", "Евгений Онегин", "Мертвые души"
    };

    public static String generateRandomAuthor() {
        String randomSurname = RussianSurnameGenerator.generateSurname();
        return  randomSurname;
    }

    public static String generateRandomTitle() {
        return TITLES[random.nextInt(TITLES.length)];
    }

    public static int generateRandomNumOfPages() {
        return 50 + random.nextInt(951);
    }

    public static List<Book> generateRandomBooks(int count) {
    return IntStream.range(0, count)
        .mapToObj(i -> new Book.Builder()
            .setAuthor(generateRandomAuthor())
            .setTitle(generateRandomTitle())
            .setNumOfPages(generateRandomNumOfPages())
            .build())
        .collect(Collectors.toList());
    }
}
