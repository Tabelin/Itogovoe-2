package input;

import data.Car;
import data.Student;
import data.Book;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;


public class ManualInput {

    private static final Scanner scanner = new Scanner(System.in);



    public static Car inputCar() {

        System.out.println("Введите данные автомобиля:");

        System.out.print("Модель: ");
        String model = scanner.nextLine().trim();

        int power;
        while (true) {
            try {
                System.out.print("Мощность (л.с.): ");
                power = Integer.parseInt(scanner.nextLine().trim());
                if (power <= 0) {
                    System.out.println("Мощность должна быть положительной.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }

        int yearOfManufacture;
        while (true) {
            try {
                System.out.print("Год выпуска: ");
                yearOfManufacture = Integer.parseInt(scanner.nextLine().trim());
                if (yearOfManufacture < 1700 || yearOfManufacture > 2025) {
                    System.out.println("Год должен быть между 1900 и 2025.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число.");
            }
        }


        return new Car.Builder()
                .setModel(model)
                .setPower(power)
                .setYearOfManufacture(yearOfManufacture)
                .build();
    }




    public static Student inputStudent() {

    System.out.println("Введите данные студента:");

    System.out.print("Группа: ");
    String groupNumber = scanner.nextLine().trim();

    if (groupNumber.isEmpty()) {
        System.out.println("Группа не может быть пустой. Установлено значение по умолчанию: 'Unknown'");
        groupNumber = "Unknown";
    }

    
    Float averageScore = null;
    while (averageScore == null) {
        try {
            System.out.print("Средний балл (от 2 до 5): ");
            String input = scanner.nextLine().trim().replace(',', '.');
            float value = Float.parseFloat(input);
            if (value < 2.0f || value > 5.0f) {
                System.out.println("Балл должен быть в диапазоне от 2 до 5.");
                continue;
            }
            averageScore = value;
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное число (например: 4).");
        }
    }

    Integer reportCardNumber = null;
    while (reportCardNumber == null) {
        try {
            System.out.print("Номер зачётки (целое число): ");
            String input = scanner.nextLine().trim();
            reportCardNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное целое число.");
        }
    }

    return new Student.Builder()
            .setGroupNumber(groupNumber)
            .setAverageScore(averageScore)
            .setReportCardNumber(reportCardNumber)
            .build();
}




    public static Book inputBook() {
        System.out.println("Введите данные книги:");
        System.out.print("Автор: ");
        
    String author = scanner.nextLine().trim();
    while (author.isEmpty()) {
        System.out.print("Автор не может быть пустым. Введите снова: ");
        author = scanner.nextLine().trim();
    }

    System.out.print("Название: ");
    String title = scanner.nextLine().trim();
    while (title.isEmpty()) {
        System.out.print("Название не может быть пустым. Введите снова: ");
        title = scanner.nextLine().trim();
    }

    int pages;
    while (true) {
        try {
            System.out.print("Количество страниц: ");
            String pagesStr = scanner.nextLine().trim();
            pages = Integer.parseInt(pagesStr);
            if (pages <= 0) {
                System.out.println("Количество страниц должно быть положительным числом.");
                continue;
            }
            break;
        } catch (NumberFormatException e) {
            System.out.println("Введите корректное целое число.");
        }
    }

       return new Book.Builder()
          .setAuthor(author)
          .setTitle(title)
          .setNumOfPages(pages)
          .build();
    }
}