package generator;

import data.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Book book = new Book.Builder()
                .setAuthors(generateRandomAuthor())
                .setTitle(generateRandomTitle())
                .setNumOfPages(generateRandomNumOfPages())
                .build();
            books.add(book);
        }
        return books;
    }

    // public static void main(String[] args) {
    //     List<Book> books = generateRandomBooks(20);
    //     for (Book book : books) {
    //         System.out.println(book.getAuthor() + " - " + book.getTitle() + ", " + book.getNumOfPages() + " стр.");
    //     }
    // }
}
