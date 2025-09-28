package data;

public class Book {

    private final String author;
    private final String title;
    private final Integer numOfPages;

    private Book(Builder builder) {
        this.author = builder.author;
        this.title = builder.title;
        this.numOfPages = builder.numOfPages;
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

        public Builder setAuthors(String author) {
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
