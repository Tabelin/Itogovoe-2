package app;

import data.Car;
import data.Student;
import data.Book;

import input.ManualInput;

import generator.GenerateBook;                  //рандом
import generator.GenerateCar;
import generator.GenerateStudent;

import writing.WriteClass;                       //чтение/запись в/из файл(а)
import java.util.function.Function;

import binSearch.BinSearch;                     //поиск

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class User_Interface {

    private static Scanner scanner = new Scanner(System.in);
    private static List<?> currentCollection = null; 
    private static String currentType = "";
    
     public static void main(String[] args) {
        System.out.println("xxx   Приложение для сортировки и поиска   xxx");

        while (true) {
            showMainMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1:
                    selectDataType();
                    break;
                case 2:
                    fillCollection();
                    break;
                case 3:
                    if (currentCollection == null || currentCollection.isEmpty()) {
                        System.out.println("Сначала заполните коллекцию!");
                        break;
                    }
                    sortCollection();
                    break;
                case 4:
                    if (currentCollection == null || currentCollection.isEmpty()) {
                        System.out.println("Сначала заполните коллекцию!");
                        break;
                    }
                      searchElement();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    System.out.println("Выход");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }
    private static void showMainMenu() {
        System.out.println("     Меню");
        System.out.println("1. Выбрать тип данных (Car / Student / Book)");

         if (!currentType.isEmpty()) {
             System.out.println("Текущий тип: " + currentType);
         } else {
             System.out.println("Тип данных: не выбран");
         }

        System.out.println("2. Заполнить коллекцию");
        System.out.println("3. Сортировать");
        System.out.println("4. Найти элемент (бинарный поиск)");
        System.out.println("5. Сохранить в файл");
        System.out.println("6. Выход");
    }

     private static void selectDataType() {                                             // 1 выбор типа данных
        System.out.println("Выберите тип данных:");
        System.out.println("1. Car");
        System.out.println("2. Student");
        System.out.println("3. Book");
        int choice = getIntInput("Ваш выбор: ");

        switch (choice) {
            case 1:
                currentType = "Car";
                System.out.println("Тип данных: Car");
                break;
            case 2:
                currentType = "Student";
                System.out.println("Тип данных: Student");
                break;
            case 3:
                currentType = "Book";
                System.out.println("Тип данных: Book");
                break;
            default:
                System.out.println("Неверный выбор.");
                currentType = "";
        }
    }

    private static void fillCollection() {                                              // 2 заполнение коллекции
        if (currentType.isEmpty()) {
            System.out.println("Сначала выберите тип данных!");
            return;
        }

        System.out.println("Как заполнить коллекцию?");
        System.out.println("1. Случайно");
        System.out.println("2. Из файла");
        System.out.println("3. Вручную");

        int choice = getIntInput("Ваш выбор: ");
        int count = getIntInput("Введите количество элементов: ");

        switch (choice) {
            case 1:
                generateRandomData(count);
                break;
            case 2:
                fillFromFile();
                break;
            case 3:
                manualInputData(count);                                                         
                break;
            default:
                System.out.println("Неверный выбор.");
        }

        if (currentCollection != null) {
            System.out.println("Коллекция заполнена. Размер: " + currentCollection.size());
        }
    }

     private static void generateRandomData(int count) {                                    // 2.1 рандом заполнение
        switch (currentType) {
            case "Car":
                currentCollection = IntStream.range(0, count)
                    .mapToObj(i -> {
                        // Генерируем ОДИН Car за раз → чистое использование стрима
                        return GenerateCar.generateRandomCars(1).get(0);
                    })
                    .collect(Collectors.toList());
                 break;
                 
             case "Student":
                currentCollection = IntStream.range(0, count)
                    .mapToObj(i -> GenerateStudent.generateRandomStudents(1).get(0))
                    .collect(Collectors.toList());
                break;
                 
             case "Book":
                currentCollection = IntStream.range(0, count)
                    .mapToObj(i -> GenerateBook.generateRandomBooks(1).get(0))
                    .collect(Collectors.toList());
                break;
                 
             default:
                System.out.println("Неизвестный тип данных.");
                return;
        }
        System.out.println("Сгенерировано " + count + " элементов типа " + currentType);
    }

                                                                                      
    private static void fillFromFile() {                                                      //2.2 чтение из файла
        if (currentType.isEmpty()) {
            System.out.println("Сначала выберите тип данных!");
            return;
        }

        System.out.print("Введите имя файла: ");
        System.out.print("Например:  java/test ");
        String fileName = scanner.nextLine();

        try {
            String[] lines = WriteClass.readLinesFromFile(fileName);

            switch (currentType) {
                case "Car":
                    List<Car> cars = Arrays.stream(lines)
                        .filter(line -> !line.trim().isEmpty())
                        .map(line -> {
                            String[] parts = line.split(",\\s*");
                            if (parts.length != 3) return null;
                            try {
                                String model = parts[0].trim();
                                int power = Integer.parseInt(parts[1].replace("л.с.", "").trim());
                                int yearOfManufacture = Integer.parseInt(parts[2].trim());
                                return new Car.Builder()
                                    .setModel(model)
                                    .setPower(power)
                                    .setYearOfManufacture(yearOfManufacture)
                                    .build();
                            } catch (Exception e) {
                                System.err.println("Ошибка парсинга строки: " + line);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                    currentCollection = cars;
                    break;

            case "Student":
                List<Student> students = Arrays.stream(lines)
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> {
                        String[] parts = line.split(",\\s*");
                        if (parts.length != 3) return null;
                        try {
                            String groupNumber = parts[0].trim();
                            float averageScore = Float.parseFloat(parts[1].trim());
                            String reportCardNumber = parts[2].trim();
                            return new Student.Builder()
                                .setGroupNumber(groupNumber)
                                .setAverageScore(averageScore)
                                .setReportCardNumber(reportCardNumber)
                                .build();
                        } catch (Exception e) {
                            System.err.println("Ошибка парсинга строки: " + line);
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

                currentCollection = students;
                break;

            case "Book":
                List<Book> books = Arrays.stream(lines)
                    .filter(line -> !line.trim().isEmpty())
                    .map(line -> {
                        String[] parts = line.split(",\\s*");
                        if (parts.length != 2) return null;
                        try {
                            String authorTitle = parts[0].trim();
                            int numOfPages = Integer.parseInt(parts[1].replace("стр.", "").trim());

                            String[] authTitleParts = authorTitle.split(" - ", 2);
                            if (authTitleParts.length != 2) return null;

                            String author = authTitleParts[0];
                            String title = authTitleParts[1];

                            return new Book.Builder()
                                .setAuthors(author)
                                .setTitle(title)
                                .setNumOfPages(numOfPages)
                                .build();
                            } catch (Exception e) {
                                System.err.println("Ошибка парсинга строки: " + line);
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());

                    currentCollection = books;
                    break;

                default:
                    System.out.println("Неизвестный тип данных.");
                    return;
            }

            System.out.println("Коллекция загружена из файла: " + fileName + " (" + currentCollection.size() + " элементов)");

        } catch (Exception e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }



        
                                                                                            //2.3 ручной ввод

    private static void manualInputData(int count) {
        Scanner inputScanner = new Scanner(System.in);

    switch (currentType) {
        case "Car":
            List<Car> cars = IntStream.range(0, count)
            .mapToObj(i -> {
                System.out.printf("Ввод автомобиля %d/%d:\n", i + 1, count);
                return ManualInput.inputCar();
            })
            .collect(Collectors.toList());
         currentCollection = cars;
          break;

        case "Student":
            List<Student> students = IntStream.range(0, count)
            .mapToObj(i -> {
                System.out.printf("Ввод студента %d/%d:\n", i + 1, count);
                return ManualInput.inputStudent();
            })
            .collect(Collectors.toList());
        currentCollection = students;
         break;


        case "Book":
            List<Book> books = IntStream.range(0, count)
            .mapToObj(i -> {
                System.out.printf("Ввод книги %d/%d:\n", i + 1, count);
                return ManualInput.inputBook();
            })
            .collect(Collectors.toList());
    currentCollection = books;
    break;

        default:
            System.out.println("Неизвестный тип данных.");
    }
    System.out.println("Ручной ввод завершён. Заполнено: " + count + " элементов.");
}


    private static void sortCollection() {                                                  // 3 сортировка 
        
        System.out.println("коллекция отсортирована (нет).");

    }

    private static void searchElement() {                                                   // 4 бинарный поиск
        
        System.out.println("бинарный поиск выполнен (нет).");
    }
        
    
    private static void saveToFile() {                                                      // 5 сохранить в файл
    if (currentCollection == null || currentCollection.isEmpty()) {                          
        System.out.println("Сначала заполните коллекцию!");
        return;
    }

    //System.out.print("Введите имя файла для сохранения (например: sortedCars.txt): ");
    //String fileName = scanner.nextLine();

    try {
        switch (currentType) {
            case "Car":
                @SuppressWarnings("unchecked")
                List<Car> cars = (List<Car>) currentCollection;
                WriteClass.writeCarData(cars);
                System.out.println("Данные успешно сохранены в файл: Car.txt");
                break;

            case "Student":
                @SuppressWarnings("unchecked")
                List<Student> students = (List<Student>) currentCollection;
                WriteClass.writeStudentData(students); 
                System.out.println("Данные успешно сохранены в файл: Student.txt");
                break;

            case "Book":
                @SuppressWarnings("unchecked")
                List<Book> books = (List<Book>) currentCollection;
                WriteClass.writeBookData(books);
                System.out.println("Данные успешно сохранены в файл: Book.txt");
                break;

            default:
                System.out.println("Неизвестный тип данных.");
                return;
        }

        System.out.println("Данные успешно сохранены в файл: " + fileName);

    } catch (Exception e) {
        System.err.println("Ошибка при записи в файл: " + e.getMessage());
    }
}    

    private static int getIntInput(String prompt) {                                    // ввод пользователя во время выбора
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Пожалуйста, введите число: ");
            scanner.next(); // очистка
        }
        return scanner.nextInt();
    }
}