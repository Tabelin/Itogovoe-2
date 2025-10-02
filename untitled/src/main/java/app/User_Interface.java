package untitled.src.main.java.app;

import data.Car;
import data.Student;
import data.Book;

import input.ManualInput;

import generator.GenerateBook;                  //рандом
import generator.GenerateCar;
import generator.GenerateStudent;

import writing.WriteClass;                       //чтение/запись в/из файл(а)
import java.util.function.Function;

import sort.SimpleMergeSort;                    //сортировка

import binSearch.BinSearch;                     //поиск

import java.util.ArrayList;                             
import java.util.Arrays;
import java.util.Comparator;
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

                                                                              
    private static void manualInputData(int count) {                                        //2.3 ручной ввод
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
        
        if (currentCollection == null || currentCollection.isEmpty()) {
        System.out.println("Сначала заполните коллекцию!");
        return;
        }

        System.out.println("Выберите режим сортировки:");
        System.out.println("1. По одному полю");
        System.out.println("2. По нескольким полям (приоритет)");

        int mode = getIntInput("Ваш выбор: ");

        Comparator<?> comparator;

        switch (mode) {
            case 1:
                comparator = getSingleFieldComparator();
                break;
            case 2:
                comparator = getMultiFieldComparator();
                break;
            default:
                System.out.println("Неверный ввод.");         //еше дополнительно к основным сортировкам реализовать эти же алгоритмы сортировки таким образом, что объекты классов будут сортироваться по какому-либо числовому полю: объекты с четными значениями этого поля должны быть отсортированы в натуральном порядке, а с нечетными – оставаться на исходных
                return;                                             //доп сортировка?
        }  
        
        if (comparator == null) return;

        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>) currentCollection;
           
        try {
            System.out.println("Начинаем сортировку...");
            SimpleMergeSort.mergeSort(list, (Comparator<Object>) comparator);
            System.out.println(" Коллекция успешно отсортирована!");
           
            // Показать первые 5 элементов
            System.out.println("Первые 5 элементов после сортировки:");
            for (int i = 0; i < Math.min(5, currentCollection.size()); i++) {
                printCollection(currentCollection, 10);
            }
        
        } catch (Exception e) {
            System.err.println("Ошибка при сортировке: " + e.getMessage());
        }

    }


    private static Comparator<?> getSingleFieldComparator() {
    System.out.println("Выберите поле для сортировки:");
    System.out.println("1. По модели / группе / автору");
    System.out.println("2. По мощности / среднему баллу / количеству страниц");
    System.out.println("3. По году выпуска / номеру зачетки / названию");

    int choice = getIntInput("Ваш выбор: ");

    switch (currentType) {
        case "Car":
            return switch (choice) {
                case 1 -> (Comparator<Car>) (c1, c2) -> c1.getModel().compareTo(c2.getModel());
                case 2 -> (Comparator<Car>) (c1, c2) -> Integer.compare(c1.getPower(), c2.getPower());
                case 3 -> (Comparator<Car>) (c1, c2) -> Integer.compare(c1.getYearOfManufacture(), c2.getYearOfManufacture());
                default -> {
                    System.out.println("Неверный выбор.");
                    yield null;
                }
            };

        case "Student":
            return switch (choice) {
                case 1 -> (Comparator<Student>) (s1, s2) -> s1.getGroupNumber().compareTo(s2.getGroupNumber());
                case 2 -> (Comparator<Student>) (s1, s2) -> Float.compare(s1.getAverageScore(), s2.getAverageScore());
                case 3 -> (Comparator<Student>) (s1, s2) -> s1.getReportCardNumber().compareTo(s2.getReportCardNumber());
                default -> {
                    System.out.println("Неверный выбор.");
                    yield null;
                }
            };

        case "Book":
            return switch (choice) {
                case 1 -> (Comparator<Book>) (b1, b2) -> b1.getAuthor().compareTo(b2.getAuthor());
                case 2 -> (Comparator<Book>) (b1, b2) -> Integer.compare(b1.getNumOfPages(), b2.getNumOfPages());
                case 3 -> (Comparator<Book>) (b1, b2) -> b1.getTitle().compareTo(b2.getTitle());
                default -> {
                    System.out.println("Неверный выбор.");
                    yield null;
                }
            };

        default:
            System.out.println("Неизвестный тип данных.");
            return null;
        }
    }


    private static Comparator<?> getMultiFieldComparator() {
    System.out.println("Сортировка по нескольким полям (в порядке приоритета).");

    switch (currentType) {
        case "Car":
            return (Comparator<Car>) Comparator.comparing(Car::getModel)
                .thenComparingInt(Car::getPower)
                .thenComparingInt(Car::getYearOfManufacture);

        case "Student":
            return (Comparator<Student>) Comparator.comparing(Student::getGroupNumber)
                .thenComparingDouble(Student::getAverageScore)
                .thenComparing(Student::getReportCardNumber);

        case "Book":
            return (Comparator<Book>) Comparator.comparing(Book::getAuthor)
                .thenComparing(Book::getTitle)
                .thenComparingInt(Book::getNumOfPages);

        default:
            System.out.println("Неизвестный тип данных.");
            return null;
        }
    }

    private static void searchElement() {                                                   // 4 бинарный поиск
       if (currentCollection == null || currentCollection.isEmpty()) {
        System.out.println("Сначала заполните коллекцию!");
        return;
    }

    scanner.nextLine(); // очистка
        System.out.print("Введите текст для поиска: ");
        String query = scanner.nextLine().toLowerCase();

        List<Object> results = currentCollection.stream()
            .filter(obj -> {
                if (obj instanceof Car car) {
                    return car.getModel().toLowerCase().contains(query) ||
                           String.valueOf(car.getPower()).contains(query) ||
                           String.valueOf(car.getYearOfManufacture()).contains(query);
                } else if (obj instanceof Student student) {
                    return student.getSurname().toLowerCase().contains(query) ||
                           student.getGroupNumber().toLowerCase().contains(query) ||
                           String.valueOf(student.getAverageScore()).contains(query) ||
                           student.getReportCardNumber().toLowerCase().contains(query);
                } else if (obj instanceof Book book) {
                    return book.getTitle().toLowerCase().contains(query) ||
                           book.getAuthor().toLowerCase().contains(query) ||
                           String.valueOf(book.getNumOfPages()).contains(query);
                }
                return false;
            })
            .toList();

        if (!results.isEmpty()) {
            System.out.println("Найдено " + results.size() + " элементов:");
            printCollection(results, 10);
        } else {
            System.out.println("Ничего не найдено.");
        }
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



    private static void printCollection(List<?> collection, int limit) {                             //вывод доллекции
    if (collection == null || collection.isEmpty()) {
        System.out.println("Коллекция пуста.");
        return;
    }
    int size = Math.min(collection.size(), limit);

    System.out.println("Первые " + size + " элементов");
        for (int i = 0; i < size; i++) {
        Object obj = collection.get(i);
        System.out.printf("%d. ", i + 1);

            if (obj instanceof Car car) {
                System.out.println(car.getModel() + "Мощность: " + car.getPower() + " л.с. Год: " + car.getYearOfManufacture());
            } else if (obj instanceof Student student) {
                System.out.println(student.getSurname() + "Группа: " + student.getGroupNumber() +
                        " Балл: " + String.format("%.2f", student.getAverageScore()));
            } else if (obj instanceof Book book) {
                System.out.println(book.getTitle() + "Автор: " + book.getAuthor() + " Страниц: " + book.getNumOfPages());
            }
        }
    }
}