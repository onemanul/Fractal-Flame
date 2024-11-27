package backendacademy.FractalFlame;

import backendacademy.FractalFlame.Structures.FractalImage;
import backendacademy.FractalFlame.Structures.Pixel;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;


public class ImageUtils {
    private ImageUtils() {}

    public static void save(FractalImage image, ImageFormat format, Path path) {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Путь не является директорией");
        }
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < image.height(); ++y) {
            for (int x = 0; x < image.width(); ++x) {
                Pixel pixel = image.pixels()[y][x];
                Color color = new Color(pixel.red, pixel.green, pixel.blue);
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }
        File file = Path.of(path.toString(),
            "Fractal_Flame_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"))
                + "." + format.name()).toFile();
        try {
            ImageIO.write(bufferedImage, format.name(), file);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи в файл");
        }
    }
}
