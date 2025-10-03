package sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;

public class MergeSort {
    private static final int MIN_PARALLEL_SIZE = 10; // Минимальный размер для параллельной сортировки слиянием
    private static ExecutorService executor;

    public static <T> void mergeSort(List<T> list, Comparator<T> comparator) {
        if (list == null || list.size() <= 1) {
            return;
        }

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int threadCount = 2;
        executor = Executors.newFixedThreadPool(threadCount);

        try {
            List<T> temp = new ArrayList<>(list);

            MergeSortTask<T> mainTask = new MergeSortTask<>(list, temp, 0, list.size() - 1, comparator, true);
            Future<Void> future = executor.submit(mainTask);
            future.get();

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Sorting interrupted", e);
        } finally {
            executor.shutdown();
        }
    }

    private static class MergeSortTask<T> implements Callable<Void> {
        private final List<T> list;
        private final List<T> temp;
        private final int left;
        private final int right;
        private final Comparator<T> comparator;
        private final boolean parallel;

        public MergeSortTask(List<T> list, List<T> temp, int left, int right,
                             Comparator<T> comparator, boolean parallel) {
            this.list = list;
            this.temp = temp;
            this.left = left;
            this.right = right;
            this.comparator = comparator;
            this.parallel = parallel;
        }

        @Override
        public Void call() throws Exception {
            if (left < right) {
                int mid = (left + right) / 2;

                boolean useParallel = parallel && (right - left + 1) >= MIN_PARALLEL_SIZE;

                if (useParallel) {
                    MergeSortTask<T> leftTask = new MergeSortTask<>(list, temp, left, mid, comparator, false);
                    MergeSortTask<T> rightTask = new MergeSortTask<>(list, temp, mid + 1, right, comparator, false);

                    Future<Void> leftFuture = executor.submit(leftTask);
                    Future<Void> rightFuture = executor.submit(rightTask);

                    leftFuture.get();
                    rightFuture.get();
                } else {
                    mergeSortSequential(list, temp, left, mid, comparator);
                    mergeSortSequential(list, temp, mid + 1, right, comparator);
                }

                merge(list, temp, left, mid, right, comparator);
            }
            return null;
        }
    }

    /**
     * Последовательная рекурсивная сортировка (для небольших частей)
     */
    private static <T> void mergeSortSequential(List<T> list, List<T> temp, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSortSequential(list, temp, left, mid, comparator);
            mergeSortSequential(list, temp, mid + 1, right, comparator);
            merge(list, temp, left, mid, right, comparator);
        }
    }

    private static <T> void merge(List<T> list, List<T> temp, int left, int mid, int right, Comparator<T> comparator) {
        for (int i = left; i <= right; i++) {
            temp.set(i, list.get(i));
        }

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (comparator.compare(temp.get(i), temp.get(j)) <= 0) {
                list.set(k, temp.get(i));
                i++;
            } else {
                list.set(k, temp.get(j));
                j++;
            }
            k++;
        }

        while (i <= mid) {
            list.set(k, temp.get(i));
            i++;
            k++;
        }
    }
}