package writing;

import data.Book;
import data.Car;
import data.Student;
import generator.GenerateBook;
import generator.GenerateCar;
import generator.GenerateStudent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class FileOperationException extends Exception {
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class WriteClass {

    public static void writeToFile(String fileName, String data) throws FileOperationException {
        try {
            java.nio.file.Files.writeString(java.nio.file.Paths.get(fileName), data);
        } catch (java.io.IOException e) {
            throw new FileOperationException("Ошибка при записи в файл", e);
        }
    }

    public static String[] readLinesFromFile(String fileName) throws FileOperationException {
        try {
            List<String> lines = java.nio.file.Files.readAllLines(java.nio.file.Paths.get(fileName));
            return lines.toArray(String[]::new);
        } catch (java.io.IOException e) {
            throw new FileOperationException("Ошибка при чтении из файла", e);
        }
    }

    private static <T> void writeData(String fileName, List<T> items, Function<T, String> toStringFunc) {
        List<String> descriptions = new ArrayList<>();
        for (T item : items) {
            String desc = toStringFunc.apply(item);
            System.out.println(desc);
            descriptions.add(desc);
        }
        String contentToWrite = String.join(System.lineSeparator(), descriptions);

        try {
            writeToFile(fileName, contentToWrite);
            System.out.println("Данные успешно записаны в файл " + fileName);

            String[] fileContent = readLinesFromFile(fileName);
            System.out.println("Содержимое файла:");
            for (String line : fileContent) {
                System.out.println(line);
            }
        } catch (FileOperationException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void writeBookData(List<Book> books) {
        writeData("Book.txt", books, book -> book.getAuthor()+ "/ " + book.getTitle() + "/ " + book.getNumOfPages());
    }

    public static void writeCarData(List<Car> cars) {
        writeData("Car.txt", cars, car -> car.getModel() + "/ " + car.getPower() + "/ " + car.getYearOfManufacture());
    }

    public static void writeStudentData(List<Student> students) {
        writeData("Student.txt", students, student -> student.getGroupNumber()
                + "/ " + String.format("%.2f", student.getAverageScore())
                + "/ " + student.getReportCardNumber());
    }

//    public static void main(String[] args) {
//        List<Book> books = GenerateBook.generateRandomBooks(5);
//        writeBookData(books);
//
//        List<Car> cars = GenerateCar.generateRandomCars(5);
//        writeCarData(cars);
//
//        List<Student> students = GenerateStudent.generateRandomStudents(5);
//        writeStudentData(students);
//    }
}
