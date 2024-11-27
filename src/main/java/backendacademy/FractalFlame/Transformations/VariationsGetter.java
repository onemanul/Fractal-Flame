package backendacademy.FractalFlame.Transformations;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;

public class VariationsGetter {
    private VariationsGetter() {}

    private static final List<Transformation> TRANSFORMATIONS =  List.of(
        new Disk(),
        new Exponential(),
        new Fisheye(),
        new Handkerchief(),
        new Heart(),
        new Linear(),
        new Polar(),
        new Sinusoidal(),
        new Spherical(),
        new Spiral(),
        new Swirl()
        );

    private final static PrintStream OUTPUT = System.out;
    static Scanner in = new Scanner("0"); // (System.in);

    public static Transformation[] get() {
        OUTPUT.println("Доступные вариации:");
        for (int i = 0; i < TRANSFORMATIONS.size(); ++i) {
            OUTPUT.println(i + 1 + " : " + TRANSFORMATIONS.get(i).getClass().getSimpleName());
        }
        OUTPUT.println("""
            Введите номера выбираемых вариаций. Номера вводите по одному. Для окончания введите 0.
            При вводе 0 до выбора вариаций они будут выбраны случайно.
            Одну вариацию можно выбрать несколько раз, увеличив этим её вес при генерации изображения""");
        List<Transformation> variations = new ArrayList<>();
        Optional<Integer> input = checkForInt(in.nextLine(), TRANSFORMATIONS.size());
        while (input.isEmpty() || input.get() != 0) {
            if (input.isEmpty()) {
                OUTPUT.println("Введите число от 1 до " + TRANSFORMATIONS.size() + " или 0 для завершения выбора");
            } else {
                variations.add(TRANSFORMATIONS.get(input.get() - 1));
            }
            input = checkForInt(in.nextLine(), TRANSFORMATIONS.size());
        }
        if (!variations.isEmpty()) {
            return variations.toArray(new Transformation[0]);
        }
        return getRandomVariations();
    }

    public static Transformation[] getRandomVariations() {
        List<Transformation> variations = new ArrayList<>();
        Random random = new Random();
        int numberOfVariations = random.nextInt(TRANSFORMATIONS.size()) + 1;
        for (int i = 0; i < numberOfVariations; ++i) {
            int index = random.nextInt(TRANSFORMATIONS.size());
            variations.add(TRANSFORMATIONS.get(index));

            OUTPUT.println(TRANSFORMATIONS.get(index).getClass().getSimpleName());
        }
        return variations.toArray(new Transformation[0]);
    }

    public static Optional<Integer> checkForInt(String str, int max) {
        try {
            int input = Integer.parseInt(str.trim());
            return (input < 0 || input > max) ? Optional.empty() : Optional.of(input);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
