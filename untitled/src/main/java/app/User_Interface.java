package app;

import data.Car;
import data.Student;
import data.Book;




import java.util.Scanner;

public class User_Interface. {
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("    Выберите действие:");
            System.out.println("1. Заполнить коллекцию (из файла, рандом, вручную)");
            System.out.println("2. Сортировать");
            System.out.println("3. Найти элемент (бинарный поиск)");
            System.out.println("4. Сохранить результат");
            System.out.println("5. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

              switch (choice) {
                case 1:
                    // вызов метода заполнения
                    break;
                case 2:
                    // вызов сортировки
                    break;
                case 3:
                    // вызов поиска
                    break;
                case 4:
                    // вызов сохранения
                    break;
                case 5:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }

        scanner.close();
    }
}