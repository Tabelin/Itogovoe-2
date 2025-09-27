package data;

public class Student {

    private final String groupNumber;
    private final Float averageScore;
    private final String reportCardNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.reportCardNumber = builder.reportCardNumber;
    }

    public String getGroupNumber() {
        return this.groupNumber;
    }

    public Float getAverageScore() {
        return this.averageScore;
    }

    public String getReportCardNumber() {
        return this.reportCardNumber;
    }

    public static class Builder {

        private String groupNumber;
        private Float averageScore;
        private String reportCardNumber;

        public Builder() {
        }

        public Builder setGroupNumber(String groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public Builder setAverageScore(Float averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public Builder setReportCardNumber(String reportCardNumber) {
            this.reportCardNumber = reportCardNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

    }

}
