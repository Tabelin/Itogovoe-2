package untitled.src.main.java.binSearch;

import java.util.Comparator;
import java.util.List;

public class BinSearch {

    public static <T> int binSearch(List<T> list, T obj, Comparator<T> comparator) {
        int startIndex = 0;
        int endIndex = list.size() - 1;

        while (startIndex <= endIndex) {
            int mid = startIndex + (endIndex - startIndex) / 2;
            int comparison = comparator.compare(list.get(mid), obj);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                startIndex = mid + 1;
            } else {
                endIndex = mid - 1;
            }
        }

        return -1;
    }
}