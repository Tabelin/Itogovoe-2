package data;

import java.util.Comparator;

public class Student {

    private final String groupNumber;
    private final Float averageScore;
    private final Integer reportCardNumber;

    private Student(Builder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.reportCardNumber = builder.reportCardNumber;
    }

    public static final Comparator<Student> BY_GROUP_NUMBER_CASE_INSENSITIVE =
            Comparator.comparing(Student::getGroupNumber, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Student> BY_AVERAGE_SCORE =
            Comparator.comparingDouble(Student::getAverageScore);

    public static final Comparator<Student> BY_REPORT_CARD_NUM =
            Comparator.comparingInt(Student::getReportCardNumber);

    public static final Comparator<Student> BY_GROUP_NUM_THEN_AVG_SCORE_THEN_CARD_NUM =
            BY_GROUP_NUMBER_CASE_INSENSITIVE.thenComparing(BY_AVERAGE_SCORE).thenComparing(BY_REPORT_CARD_NUM);

    public static final Comparator<Student> BY_GROUP_NUM_THEN_CARD_NUM_THEN_AVG_SCORE =
            BY_GROUP_NUMBER_CASE_INSENSITIVE.thenComparing(BY_REPORT_CARD_NUM).thenComparing(BY_AVERAGE_SCORE);

    public static final Comparator<Student> BY_AVG_SCORE_THEN_GROUP_NUM_THEN_CARD_NUM =
            BY_AVERAGE_SCORE.thenComparing(BY_GROUP_NUMBER_CASE_INSENSITIVE).thenComparing(BY_REPORT_CARD_NUM);

    public static final Comparator<Student> BY_AVG_SCORE_THEN_CARD_NUM_THEN_GROUP_NUM =
            BY_AVERAGE_SCORE.thenComparing(BY_REPORT_CARD_NUM).thenComparing(BY_GROUP_NUMBER_CASE_INSENSITIVE);

    public static final Comparator<Student> BY_CARD_NUM_THEN_GROUP_NUM_THEN_AVG_SCORE =
            BY_REPORT_CARD_NUM.thenComparing(BY_GROUP_NUMBER_CASE_INSENSITIVE).thenComparing(BY_AVERAGE_SCORE);

    public static final Comparator<Student> BY_CARD_NUM_THEN_AVG_SCORE_THEN_GROUP_NUM =
            BY_REPORT_CARD_NUM.thenComparing(BY_AVERAGE_SCORE).thenComparing(BY_GROUP_NUMBER_CASE_INSENSITIVE);

    public String getGroupNumber() {
        return this.groupNumber;
    }

    public Float getAverageScore() {
        return this.averageScore;
    }

    public Integer getReportCardNumber() {
        return this.reportCardNumber;
    }

    public static class Builder {

        private String groupNumber;
        private Float averageScore;
        private Integer reportCardNumber;

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

        public Builder setReportCardNumber(Integer reportCardNumber) {
            this.reportCardNumber = reportCardNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

    }

}
