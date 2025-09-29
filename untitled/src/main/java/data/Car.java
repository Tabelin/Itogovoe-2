package data;

import java.util.Comparator;

public class Car {

    private final String model;
    private final Integer power;
    private final Integer yearOfManufacture;

    private Car(Builder builder) {
        this.model = builder.model;
        this.power = builder.power;
        this.yearOfManufacture = builder.yearOfManufacture;
    }

    public static final Comparator<Car> BY_MODEL_CASE_INSENSITIVE =
            Comparator.comparing(Car::getModel, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Car> BY_POWER =
            Comparator.comparingInt(Car::getPower);

    public static final Comparator<Car> BY_YEAR =
            Comparator.comparingInt(Car::getYearOfManufacture);

    public static final Comparator<Car> BY_MODEL_THEN_POWER_THEN_YEAR =
            BY_MODEL_CASE_INSENSITIVE.thenComparing(BY_POWER).thenComparing(BY_YEAR);

    public static final Comparator<Car> BY_MODEL_THEN_YEAR_THEN_POWER =
            BY_MODEL_CASE_INSENSITIVE.thenComparing(BY_YEAR).thenComparing(BY_POWER);

    public static final Comparator<Car> BY_POWER_THEN_MODEL_THEN_YEAR =
            BY_POWER.thenComparing(BY_MODEL_CASE_INSENSITIVE).thenComparing(BY_YEAR);

    public static final Comparator<Car> BY_POWER_THEN_YEAR_THEN_MODEL =
            BY_POWER.thenComparing(BY_YEAR).thenComparing(BY_MODEL_CASE_INSENSITIVE);

    public static final Comparator<Car> BY_YEAR_THEN_MODEL_THEN_POWER =
            BY_YEAR.thenComparing(BY_MODEL_CASE_INSENSITIVE).thenComparing(BY_POWER);

    public static final Comparator<Car> BY_YEAR_THEN_POWER_THEN_MODEL =
            BY_YEAR.thenComparing(BY_POWER).thenComparing(BY_MODEL_CASE_INSENSITIVE);

    public String getModel() {
        return this.model;
    }

    public Integer getPower() {
        return this.power;
    }

    public Integer getYearOfManufacture() {
        return this.yearOfManufacture;
    }

    public static class Builder {

        private String model;
        private Integer power;
        private Integer yearOfManufacture;

        public Builder() {
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setPower(Integer power) {
            this.power = power;
            return this;
        }

        public Builder setYearOfManufacture(Integer yearOfManufacture) {
            this.yearOfManufacture = yearOfManufacture;
            return this;
        }

        public Car build() {
            return new Car(this);
        }

    }

}
