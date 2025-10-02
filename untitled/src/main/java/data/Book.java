package data;

import java.util.Comparator;
import java.util.Objects;

public class Book {

    private final String author;
    private final String title;
    private final Integer numOfPages;

    private Book(Builder builder) {
        this.author = builder.author;
        this.title = builder.title;
        this.numOfPages = builder.numOfPages;
    }

    private static final Comparator<Book> BY_AUTHOR_CASE_INSENSITIVE =
            Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);

    private static final Comparator<Book> BY_TITLE_CASE_INSENSITIVE =
            Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);

    private static final Comparator<Book> BY_PAGES =
            Comparator.comparingInt(Book::getNumOfPages);

    public static final Comparator<Book> BY_AUTHOR_THEN_TITLE_THEN_PAGES =
            BY_AUTHOR_CASE_INSENSITIVE.thenComparing(BY_TITLE_CASE_INSENSITIVE).thenComparing(BY_PAGES);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.getAuthor()) &&
                Objects.equals(title, book.getTitle()) &&
                (int) numOfPages == book.getNumOfPages();
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, numOfPages);
    }

    public String getAuthor() {
        return this.author;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getNumOfPages() {
        return this.numOfPages;
    }

    public static class Builder {

        private String author;
        private String title;
        private Integer numOfPages;

        public Builder() {
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setNumOfPages(Integer numOfPages) {
            this.numOfPages = numOfPages;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }

}
