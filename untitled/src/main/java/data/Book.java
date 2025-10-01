package data;

import java.util.Comparator;

public class Book {

    private final String author;
    private final String title;
    private final Integer numOfPages;

    private Book(Builder builder) {
        this.author = builder.author;
        this.title = builder.title;
        this.numOfPages = builder.numOfPages;
    }

    public static final Comparator<Book> BY_AUTHOR_CASE_INSENSITIVE =
            Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Book> BY_TITLE_CASE_INSENSITIVE =
            Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Book> BY_PAGES =
            Comparator.comparingInt(Book::getNumOfPages);

    public static final Comparator<Book> BY_AUTHOR_THEN_TITLE_THEN_PAGES =
            BY_AUTHOR_CASE_INSENSITIVE.thenComparing(BY_TITLE_CASE_INSENSITIVE).thenComparing(BY_PAGES);

    public static final Comparator<Book> BY_AUTHOR_THEN_PAGES_THEN_TITLE =
            BY_AUTHOR_CASE_INSENSITIVE.thenComparing(BY_PAGES).thenComparing(BY_TITLE_CASE_INSENSITIVE);

    public static final Comparator<Book> BY_TITLE_THEN_AUTHOR_THEN_PAGES =
            BY_TITLE_CASE_INSENSITIVE.thenComparing(BY_AUTHOR_CASE_INSENSITIVE).thenComparing(BY_PAGES);

    public static final Comparator<Book> BY_TITLE_THEN_PAGES_THEN_AUTHOR =
            BY_TITLE_CASE_INSENSITIVE.thenComparing(BY_PAGES).thenComparing(BY_AUTHOR_CASE_INSENSITIVE);

    public static final Comparator<Book> BY_PAGES_THEN_AUTHOR_THEN_TITLE =
            BY_PAGES.thenComparing(BY_AUTHOR_CASE_INSENSITIVE).thenComparing(BY_TITLE_CASE_INSENSITIVE);

    public static final Comparator<Book> BY_PAGES_THEN_TITLE_THEN_AUTHOR =
            BY_PAGES.thenComparing(BY_TITLE_CASE_INSENSITIVE).thenComparing(BY_AUTHOR_CASE_INSENSITIVE);

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
