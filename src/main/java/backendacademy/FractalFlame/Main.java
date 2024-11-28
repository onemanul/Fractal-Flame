package backendacademy.FractalFlame;

import backendacademy.FractalFlame.Render.MultiThreaded;
import backendacademy.FractalFlame.Render.SingleThreaded;
import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.FractalStructure;
import backendacademy.FractalFlame.Transformations.Transformation;
import backendacademy.FractalFlame.Transformations.VariationsGetter;
import backendacademy.FractalFlame.Utils.ImageFormat;
import backendacademy.FractalFlame.Utils.ImageUtils;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

@UtilityClass
public class Main {
    private static final int MIN_IMAGE_SIZE = 16;
    private static final int MAX_WIDTH = 3840;
    private static final int MAX_HEIGHT = 2160;
    private static final int MIN_ITERATIONS = 5;
    private static final int MAX_ITERATIONS = 100000;
    private static final int MAX_SYMMETRY_INDEX = 5;
    private static final int POINTS = 300;
    private static final int EQ_COUNT = 20;                 // количество генерируемых афинных преобразований
    private static final Path PATH_FOR_IMAGE = Paths.get("IMAGES");

    private final static PrintStream OUTPUT = System.out;
    static Scanner in = new Scanner(System.in);             // "1000\n1000\n10000\n0\n"

    public static void main(String[] args) {
        OUTPUT.println("Введите параметры изображения:\nШирина: " + segment(MIN_IMAGE_SIZE, MAX_WIDTH));
        int width = correctIntInput(MIN_IMAGE_SIZE, MAX_WIDTH);
        OUTPUT.println("Высота: " + segment(MIN_IMAGE_SIZE, MAX_HEIGHT));
        int height = correctIntInput(MIN_IMAGE_SIZE, MAX_HEIGHT);
        OUTPUT.println("Введите количество итераций: " + segment(MIN_ITERATIONS, MAX_ITERATIONS));
        int iterations = correctIntInput(MIN_ITERATIONS, MAX_ITERATIONS);
        OUTPUT.println("Введите показатель симметрии (степень двойки, где 0 - без симметрии): "
            + segment(0, MAX_SYMMETRY_INDEX));
        int symmetryIndex = correctIntInput(0, MAX_SYMMETRY_INDEX);
        Transformation[] variations = VariationsGetter.get();

        FractalStructure fractalSingle =
            FractalStructure.create(POINTS, iterations, EQ_COUNT, width, height, symmetryIndex, variations);
        FractalStructure fractalMulti =
            FractalStructure.create(POINTS, iterations, EQ_COUNT, width, height, symmetryIndex, variations);

        Pair<FractalImage, Long> imageTimeSingleThread = imageTimeSingleThread(fractalSingle);
        ImageUtils.save(imageTimeSingleThread.getLeft(), ImageFormat.JPEG, PATH_FOR_IMAGE);
        Pair<FractalImage, Long> imageTimeMultiThread = imageTimeMultiThread(fractalMulti);
        ImageUtils.save(imageTimeMultiThread.getLeft(), ImageFormat.PNG, PATH_FOR_IMAGE);

        OUTPUT.println("Время при однопоточной генерации: " + imageTimeSingleThread.getRight()
            + "\nВремя при многопоточной генерации (" + Runtime.getRuntime().availableProcessors() + " потоков): "
            + imageTimeMultiThread.getRight());
    }

    public static Pair<FractalImage, Long> imageTimeSingleThread(FractalStructure fractal) {
        long start = System.nanoTime();
        FractalImage image = SingleThreaded.getFractalImage(fractal);
        long time = System.nanoTime() - start;
        return Pair.of(image, time);
    }

    public static Pair<FractalImage, Long> imageTimeMultiThread(FractalStructure fractal) {
        long start = System.nanoTime();
        FractalImage image = MultiThreaded.getFractalImage(fractal);
        long time = System.nanoTime() - start;
        return Pair.of(image, time);
    }

    public static int correctIntInput(int min, int max) {
        Optional<Integer> optSize = checkForInt(in.nextLine());
        while (optSize.isEmpty() || optSize.get() < min || optSize.get() > max) {
            OUTPUT.println("Введите число " + segment(min, max));
            optSize = checkForInt(in.nextLine());
        }
        return optSize.get();
    }

    public static Optional<Integer> checkForInt(String str) {
        try {
            return Optional.of(Integer.parseInt(str.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static String segment(int min, int max) {
        return  "от " + min + " до " + max;
    }
}
