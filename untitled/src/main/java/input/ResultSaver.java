package input;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ResultSaver {

    private static final String RESULT_FILE = "results.txt";

    public static void saveWithIndex(List<?> collection, String header) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE, true))) { 

            if (header != null && !header.isBlank()) {
                writer.write(header);
                writer.newLine();
            }

            for (int i = 0; i < collection.size(); i++) {
                writer.write(i + ": " + collection.get(i).toString());
                writer.newLine();
            }

            writer.write("--- Конец записи ---");
            writer.newLine();
            writer.newLine(); 

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
     public static void saveFoundElement(int index, Object element, String searchQuery) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE, true))) {

            writer.write("Поиск: \"" + searchQuery + "\"");
            writer.newLine();
            if (index != -1) {
                writer.write("Найдено на позиции " + index + ": " + element.toString());
            } else {
                writer.write("Элемент не найден.");
            }
            writer.newLine();
            writer.write("Конец поиска");
            writer.newLine();


        } catch (IOException e) {
            System.err.println("Ошибка при записи результата поиска: " + e.getMessage());
        }
    }

    public static void saveSearchResults(List<String> indexedResults, String header) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RESULT_FILE, true))) {

            writer.write(header);
            writer.newLine();

            if (indexedResults.isEmpty()) {
                writer.write("Результат: не найдено.");
            } else {
                for (String line : indexedResults) {
                    writer.write(line);
                    writer.newLine();
                }
            }

            writer.write("Конец поиска");
            writer.newLine();

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }
}