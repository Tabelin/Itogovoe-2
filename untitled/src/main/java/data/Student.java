package data;

public class Student {

    private final String groupNumber;
    private final Float averageScore;
    private final String reportCardNumber;

    private Student(String groupNumber, Float averageScore, String reportCardNumber) {
        this.groupNumber = groupNumber;
        this.averageScore = averageScore;
        this.reportCardNumber = reportCardNumber;
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

        public Builder(String groupNumber, Float averageScore, String reportCardNumber) {
            this.groupNumber = groupNumber;
            this.averageScore = averageScore;
            this.reportCardNumber = reportCardNumber;
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
            return new Student(this.groupNumber, this.averageScore, this.reportCardNumber);
        }

    }

}
