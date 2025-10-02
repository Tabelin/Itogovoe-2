package generator;

import data.Car;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenerateCar {
    private static final Random random = new Random();

    private static final String[] MODELS = {
        "Лада", "ГАЗ", "КамАЗ", "Москвич", "УАЗ", "ЗАЗ", "ТагАЗ", "ПАЗ"
    };

    public static String generateRandomModel() {
        return MODELS[random.nextInt(MODELS.length)];
    }

    public static int generateRandomPower() {
        return 50 + random.nextInt(401);
    }

    public static int generateRandomYear() {
        return 1980 + random.nextInt(46);
    }

    public static List<Car> generateRandomCars(int count) {
    return IntStream.range(0, count)
        .mapToObj(_ -> new Car.Builder()
            .setModel(generateRandomModel())
            .setPower(generateRandomPower())
            .setYearOfManufacture(generateRandomYear())
            .build())
        .collect(Collectors.toList());
    }


    // public static void main(String[] args) {
    //     List<Car> cars = generateRandomCars(20);
    //     for (Car car : cars) {
    //         System.out.println(car.getModel() + ", " + car.getPower() + " л.с., " + car.getYearOfManufacture());
    //     }
    // }
}
