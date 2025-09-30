import java.util.Comparator;

public class AdditionalSorter<T> {

    private T[] innerArray;
    private Comparator<T> comparator;

    private AdditionalSorter(Builder builder) {
        this.innerArray = builder.innerArray;
        this.comparator = builder.comparator;
    }


    public class Builder {

        private T[] innerArray;
        private Comparator<T> comparator;

        public Builder() {
        }

        public Builder setInnerArray(T[] innerArray) {
            this.innerArray = innerArray;
            return this;
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
