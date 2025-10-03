package app;

import binSearch.BinSearch;
import data.Book;
import data.Car;
import data.Student;
import generator.GenerateBook;
import generator.GenerateCar;                 
import generator.GenerateStudent;
import input.ManualInput;
import input.ResultSaver;
import java.util.Arrays;                      
import java.util.Comparator;
import java.util.List;
import java.util.Objects;                   
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import number_entries_in_list.NumEntriesInList;
import sort.SimpleMergeSort;                    
import writing.WriteClass;                             

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

                case 6:  // добавить ввод пользователем  элемента 
                    if (currentCollection == null || currentCollection.isEmpty()) {
                        System.out.println("Сначала заполните коллекцию!");
                        break;
                    }
                    countElementOccurrences(); // нужно добавить сюда, что мы можем передавать;
    
                    break;


                case 7:
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
        System.out.println("6. Вывод количеств вхождений элемента в список");
        System.out.println("7. Выход");
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
        int choice = getIntInput("Ваш выбор заполнения: ");
        

        switch (choice) {
            case 1:
                generateRandomData();
                break;
            case 2:
                fillFromFile();
                break;
            case 3:
                manualInputData();                                                         
                break;
            default:
                System.out.println("Неверный выбор.");
        }

        if (currentCollection != null) {
            System.out.println("Коллекция заполнена. Размер: " + currentCollection.size());
        }
    }

     private static void generateRandomData() {                                    // 2.1 рандом заполнение
        int count = getIntInput("Введите количество элементов: ");
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

   
        String fileName = scanner.nextLine().trim();
        try {
            String[] lines = WriteClass.readLinesFromFile(fileName);

            switch (currentType) {
                case "Car":
                    List<Car> cars = Arrays.stream(lines)
                        .filter(line -> !line.trim().isEmpty())
                        .map(line -> {
                            String[] parts = line.split("/");
                            if (parts.length != 3) return null;
                            try {
                                String model = parts[0].trim();
                                int power = Integer.parseInt(parts[1].trim());
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
                        String[] parts = line.split("/");
                        if (parts.length != 3) return null;
                        try {
                            String groupNumber = parts[0].trim();
                            float averageScore = Float.parseFloat(parts[1].trim());
                            Integer reportCardNumber = Integer.parseInt(parts[2].trim());
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
                        String[] parts = line.split("/");
                        if (parts.length != 2) return null;
                        try {
                            String authorTitle = parts[0].trim();
                            int numOfPages = Integer.parseInt(parts[1].replace("стр.", "").trim());

                            String[] authTitleParts = authorTitle.split(" - ", 2);
                            if (authTitleParts.length != 2) return null;

                            String author = authTitleParts[0];
                            String title = authTitleParts[1];

                            return new Book.Builder()
                                .setAuthor(author)
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

                                                                              
    private static void manualInputData() {                                        //2.3 ручной ввод

        int count = getIntInput("Введите количество элементов: ");

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
        System.out.println("1. По нескольким полям");

        int mode = getIntInput("Ваш выбор: ");

        Comparator<?> comparator;

        switch (mode) {
            
            case 1:
                comparator = getMultiFieldComparator();
                break;
            default:
                System.out.println("Неверный ввод.");        
                return;                                             
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
                ResultSaver.saveWithIndex(currentCollection, "Отсортированная коллекция (" + currentType + ") ");
            }
        
        } catch (Exception e) {
            System.err.println("Ошибка при сортировке: " + e.getMessage());
        }
    }

    private static Comparator<?> getMultiFieldComparator() {
    //System.out.println("Сортировка по нескольким полям (в порядке приоритета).");

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
        switch (currentType) {
            case "Car":
                searchCar();
                break;
            case "Student":
                searchStudent();
                break;
            case "Book":
                searchBook();
                break;
            default:
                System.out.println("Неизвестный тип данных.");
        }

    }
        
    private static void saveToFile() {                                                      // 5 сохранить в файл
    if (currentCollection == null || currentCollection.isEmpty()) {                          
        System.out.println("Сначала заполните коллекцию!");
        return;
    }


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
      
         int intResult = scanner.nextInt();
         scanner.nextLine();
         return intResult;

    }
   
    private static void countElementOccurrences() {                                 // подсчет н
        if (currentCollection == null || currentCollection.isEmpty()) {
            System.out.println("Сначала заполните коллекцию!");
            return;
        }

             scanner.nextLine();
        System.out.print("Введите модель: ");
        String model = scanner.nextLine().trim();

         int power;

    while (true) {
        try {
            System.out.print("Введите мощность (л.с.): ");
            power = Integer.parseInt(scanner.nextLine().trim());
            break;
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    int year;
        while (true) {
            try {
                System.out.print("Введите год выпуска: ");
                year = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }

        Car searchCar = new Car.Builder()
            .setModel(model)
            .setPower(power)
            .setYearOfManufacture(year)
            .build();

        @SuppressWarnings("unchecked")
        Comparator<Car> comparator = (Comparator<Car>) getMultiFieldComparator();
        if (comparator == null) return;

        @SuppressWarnings("unchecked")
        List<Car> cars = (List<Car>) currentCollection;
            System.out.println(model+power+year+"dd");


            int countEntries=0;
            try{
                
                countEntries = NumEntriesInList.findNumEntries(searchCar,cars,comparator);
                } catch (InterruptedException e) {
                System.out.println("Введите число.");
            
            }
            System.out.println("количество вхождений " +countEntries);

    
        }
    private static void printCollection(List<?> collection, int limit) {                             //вывод коллекции
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
                System.out.println(car.getModel() + " Мощность: " + car.getPower() + " л.с. Год: " + car.getYearOfManufacture());
            } else if (obj instanceof Student student) {
                System.out.println("Группа: " + student.getGroupNumber() +
                        " Балл: " + String.format("%.2f", student.getAverageScore()));
            } else if (obj instanceof Book book) {
                System.out.println(book.getTitle() + "Автор: " + book.getAuthor() + " Страниц: " + book.getNumOfPages());
            }
        }
    }

    private static void searchCar() {
        scanner.nextLine();
        System.out.print("Введите модель: ");
        String model = scanner.nextLine().trim();

         int power;

    while (true) {
        try {
            System.out.print("Введите мощность (л.с.): ");
            power = Integer.parseInt(scanner.nextLine().trim());
            break;
        } catch (NumberFormatException e) {
            System.out.println("Введите число.");
        }
    }

    int year;
        while (true) {
            try {
                System.out.print("Введите год выпуска: ");
                year = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Введите число.");
            }
        }

        Car searchCar = new Car.Builder()
            .setModel(model)
            .setPower(power)
            .setYearOfManufacture(year)
            .build();

        @SuppressWarnings("unchecked")
        Comparator<Car> comparator = (Comparator<Car>) getMultiFieldComparator();
        if (comparator == null) return;

        @SuppressWarnings("unchecked")
        List<Car> cars = (List<Car>) currentCollection;
            System.out.println(model+power+year);



        int index = BinSearch.binSearch(cars, searchCar, comparator);

        if (index != -1) {
            System.out.println("Элемент найден на позиции: " + index);
            System.out.println("Найденный автомобиль: " + cars.get(index));
        } else {
            System.out.println("Элемент не найден.");
        }
    }

    private static void searchStudent(){

    }
    private static void searchBook(){
        
    }

}