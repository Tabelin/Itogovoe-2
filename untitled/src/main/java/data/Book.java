package data;

public class Book {

    /* В качестве формата ввода можно взять перечисление авторов через точку с запятой, затем можно с помощью split()
    легко получить массив авторов и работать с ним */
    private final String authors;
    private final String title;
    private final Integer numOfPages;

    private Book(String authors, String title, Integer numOfPages) {
        this.authors = authors;
        this.title = title;
        this.numOfPages = numOfPages;
    }

    public String getAuthors() {
        return this.authors;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getNumOfPages() {
        return this.numOfPages;
    }

    public static class Builder {

        private String authors;
        private String title;
        private Integer numOfPages;

        public Builder() {
        }

        public Builder(String authors, String title, Integer numOfPages) {
            this.authors = authors;
            this.title = title;
            this.numOfPages = numOfPages;
        }

        public Builder setAuthors(String authors) {
            this.authors = authors;
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
            return new Book(this.authors, this.title, this.numOfPages);
        }

    }

}
