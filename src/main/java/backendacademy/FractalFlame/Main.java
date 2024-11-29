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

/**
 * При небольших входных значениях генерации фрактального пламени (особенно при малом количестве итераций или точек)
 * многопоточная реализация НЕ ВСЕГДА БЫСТРЕЕ однопоточной, а начиная с некоторых достаточно малых значений
 * многопоточная реализация ВСЕГДА МЕДЛЕННЕЕ однопоточной. Используемые константы (в том числе минимальные)
 * отсекают бОльшую часть таких случаев.
 */

@UtilityClass
public class Main {
    private static final int MIN_IMAGE_SIZE = 100;
    private static final int MAX_WIDTH = 3840;
    private static final int MAX_HEIGHT = 2160;
    private static final int MIN_ITERATIONS = 100;
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

        FractalImage imageSingle = SingleThreaded.getFractalImage(fractalSingle);
        FractalImage imageMulti = MultiThreaded.getFractalImage(fractalMulti);
        ImageUtils.save(imageSingle, ImageFormat.JPEG, PATH_FOR_IMAGE);
        ImageUtils.save(imageMulti, ImageFormat.PNG, PATH_FOR_IMAGE);

        FractalStructure fractal =
            FractalStructure.create(POINTS, iterations, EQ_COUNT, width, height, symmetryIndex, variations);
        Runnable single = () -> SingleThreaded.getFractalImage(fractal);
        Runnable multi = () -> MultiThreaded.getFractalImage(fractal);
        OUTPUT.println("Время при однопоточной генерации: " + getTime(single)
            + "\nВремя при многопоточной генерации (" + Runtime.getRuntime().availableProcessors() + " потоков): "
            + getTime(multi));
    }

    public static long getTime(Runnable method) {
        long start = System.nanoTime();
        method.run();
        long end = System.nanoTime();
        return end - start;
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
