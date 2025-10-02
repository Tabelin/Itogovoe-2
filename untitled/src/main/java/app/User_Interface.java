package app;

import data.Car;
import data.Student;
import data.Book;

import input.ManualInput;

import generator.GenerateBook;
import generator.GenerateCar;
import generator.GenerateStudent;

import binSearch.BinSearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class User_Interface {

    private static Scanner scanner = new Scanner(System.in);
    private static List<?> currentCollection = null; 
    private static String currentType = "";
    
     public static void main(String[] args) {
        System.out.println("=== Приложение для сортировки и поиска ===");

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
                System.out.println("чтение из файла не реализовано.");
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
        switch (currentType) {                                                              //заполнение коллекции должно быть посредством стрима!!!
            case "Car":
                currentCollection = GenerateCar.generateRandomCars(count);
                break;
            case "Student":
                currentCollection = GenerateStudent.generateRandomStudents(count);
                break;
            case "Book":
                currentCollection = GenerateBook.generateRandomBooks(count);
                break;
            default:
                System.out.println("Неизвестный тип данных.");
                return;
        }
        System.out.println("Сгенерировано " + count + " элементов типа " + currentType);
    }



                                                                                            //2.2 чтение из файла




        
                                                                                            //2.3 ручной ввод

    private static void manualInputData(int count) {
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
        
        System.out.println("данные сохранены (нет).");
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