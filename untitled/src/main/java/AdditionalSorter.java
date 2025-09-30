import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AdditionalSorter<T> {

    private Comparator<T> comparator;

    private AdditionalSorter(Builder builder) {
        this.comparator = builder.comparator;
    }

    public List<T> sort(List<T> arrayForSort) {
        if (arrayForSort.size() == 1) {
            return arrayForSort;
        } else {
            int midIndex = arrayForSort.size() / 2;
            List<T> sortedLeftArray = sort(arrayForSort.subList(0, midIndex));
            List<T> sortedRightArray = sort(arrayForSort.subList(midIndex, arrayForSort.size()));

            return mergeSortedPartsOfArray(sortedLeftArray, sortedRightArray);
        }
    }

    private List<T> mergeSortedPartsOfArray(List<T> leftArray, List<T> rightArray) {
        List<T> resultArray = new ArrayList<>(leftArray.size() + rightArray.size());
        int indexInLeftArray = 0, indexInRightArray = 0;

        while (indexInLeftArray != leftArray.size() || indexInRightArray != rightArray.size()) {

        }

        return resultArray;
    }

    public class Builder {

        private Comparator<T> comparator;

        public Builder() {
        }

        public Builder setComparator(Comparator<T> comparator) {
            this.comparator = comparator;
            return this;
        }

        public AdditionalSorter<T> build() {
            return new AdditionalSorter<>(this);
        }
    }

}
