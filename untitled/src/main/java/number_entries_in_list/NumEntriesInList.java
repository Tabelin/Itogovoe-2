package untitled.src.main.java.number_entries_in_list;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class NumEntriesInList {

    public static <T> int findNumEntries(T element, List<T> list, Comparator<T> comparator) throws InterruptedException {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int sizeL = list.size();

        AtomicInteger totalCount = new AtomicInteger(0);
        CountDownLatch completionLatch = new CountDownLatch(availableProcessors);

        for (int i = 0; i < availableProcessors; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    int localCount = 0; // для уменьшения работы с атомарными полями
                    int index = threadId;

                    while (index < sizeL) {
                        if (element.equals(list.get(index))) {
                            localCount++;
                        }
                        index += availableProcessors;
                    }

                    totalCount.addAndGet(localCount);
                    System.out.println("Thread " + threadId + " found " + localCount + " matches");

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    completionLatch.countDown();
                }
            }).start();
        }

        completionLatch.await();

        return totalCount.get();
    }
}