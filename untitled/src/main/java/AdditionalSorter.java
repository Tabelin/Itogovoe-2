import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class AdditionalSorter<T> {

    private Comparator<T> comparator;

    private final ExecutorService executor;

    private AdditionalSorter(Builder<T> builder) {
        this.comparator = builder.comparator;
        this.executor = builder.executor;
    }

    public void changeComparatorTo(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public List<T> sort(List<T> arrayForSort) {
        Future<List<T>> sortResult = executor.submit(() -> {
            if (arrayForSort.size() == 1 || arrayForSort.isEmpty()) {
                return arrayForSort;
            } else {
                int midIndex = arrayForSort.size() / 2;
                List<T> sortedLeftArray = sort(arrayForSort.subList(0, midIndex));
                List<T> sortedRightArray = sort(arrayForSort.subList(midIndex, arrayForSort.size()));

                return mergeSortedPartsOfArray(sortedLeftArray, sortedRightArray);
            }
        });

        try {
            return sortResult.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            System.out.printf("Execution exception: %s\n", e.getMessage());
        }

        return null;
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
            if (comparator.compare(leftArray.get(indexInLeftArray), rightArray.get(indexInRightArray)) >= 0) {
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

        private Comparator<T> comparator;
        private ExecutorService executor;

        public Builder() {
        }

        public Builder<T> setComparator(Comparator<T> comparator) {
            this.comparator = comparator;
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
