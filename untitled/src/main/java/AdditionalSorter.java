import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.ToIntFunction;

public class AdditionalSorter<T> {

    private final ToIntFunction<T> extractor;
    private final ExecutorService executor;

    private AdditionalSorter(Builder<T> builder) {
        this.executor = builder.executor;
        this.extractor = builder.extractor;
    }

    public List<T> additionalSort(List<T> arrayForSort) {
        ArrayList<T> originalArray = new ArrayList<>(arrayForSort);
        // Достаем из исходного массива объекты с четными значениями полей
        List<T> arrayOfObjWithEvenFields = originalArray.stream()
                .filter(obj -> extractor.applyAsInt(obj) % 2 == 0)
                .toList();

        // Сортируем полученные элементы с четными полями
        List<T> sortedObjWithEvenFields = sortEvenFields(arrayOfObjWithEvenFields);

        return getArrayWithInsertedElements(arrayForSort, sortedObjWithEvenFields);
    }

    private List<T> getArrayWithInsertedElements(List<T> originalArray, List<T> arrayForInsert) {
        int indexInArrayForInsert = 0;

        for (int i = 0; i < originalArray.size(); i++) {
            if (extractor.applyAsInt(originalArray.get(i)) % 2 == 0) {
                originalArray.set(i, arrayForInsert.get(indexInArrayForInsert));
                indexInArrayForInsert++;
            }
        }

        return originalArray;
    }

    private List<T> sortEvenFields(List<T> arrayForSort) {
        Future<List<T>> sortResult = executor.submit(() -> {
            if (arrayForSort.size() == 1 || arrayForSort.isEmpty()) {
                return arrayForSort;
            } else {
                int midIndex = arrayForSort.size() / 2;
                List<T> sortedLeftArray = sortEvenFields(arrayForSort.subList(0, midIndex));
                List<T> sortedRightArray = sortEvenFields(arrayForSort.subList(midIndex, arrayForSort.size()));

                return mergeSortedPartsOfArray(sortedLeftArray, sortedRightArray);
            }
        });

        try {
            return sortResult.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.out.printf("Ошибка внутри одного из потоков. Причина: %s\n", e.getCause());
        }

        return Collections.emptyList();
    }

    private List<T> mergeSortedPartsOfArray(List<T> leftArray, List<T> rightArray) {
        if (leftArray == null || rightArray == null) {
            return null;
        }

        List<T> resultArray = new ArrayList<>(leftArray.size() + rightArray.size());
        int indexInLeftArray = 0, indexInRightArray = 0;

        while (indexInLeftArray != leftArray.size() || indexInRightArray != rightArray.size()) {
            if (indexInLeftArray == leftArray.size()) {
                resultArray.addAll(rightArray.subList(indexInRightArray, rightArray.size()));
                indexInRightArray = rightArray.size();
                continue;
            }
            if (indexInRightArray == rightArray.size()) {
                resultArray.addAll(leftArray.subList(indexInLeftArray, leftArray.size()));
                indexInLeftArray = leftArray.size();
                continue;
            }
            if (extractor.applyAsInt(leftArray.get(indexInLeftArray)) >= extractor.applyAsInt(rightArray.get(indexInRightArray))) {
                resultArray.add(rightArray.get(indexInRightArray));
                indexInRightArray++;
            } else {
                resultArray.add(leftArray.get(indexInLeftArray));
                indexInLeftArray++;
            }
        }

        return resultArray;
    }

    public static class Builder<T> {

        private ToIntFunction<T> extractor;
        private ExecutorService executor;

        public Builder() {
        }

        public Builder<T> setExtractor(ToIntFunction<T> extractor) {
            this.extractor = extractor;
            return this;
        }

        public Builder<T> setExecutor(ExecutorService executor) {
            this.executor = executor;
            return this;
        }

        public AdditionalSorter<T> build() {
            return new AdditionalSorter<>(this);
        }
    }

}
