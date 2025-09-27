package data;

public class Car {

    private final String model;
    private final Integer power;
    private final Integer yearOfManufacture;

    private Car(String model, Integer power, Integer yearOfManufacture) {
        this.model = model;
        this.power = power;
        this.yearOfManufacture = yearOfManufacture;
    }

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

        public Builder(String model, Integer power, Integer yearOfManufacture) {
            this.model = model;
            this.power = power;
            this.yearOfManufacture = yearOfManufacture;
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
            return new Car(this.model, this.power, this.yearOfManufacture);
        }

    }

}
