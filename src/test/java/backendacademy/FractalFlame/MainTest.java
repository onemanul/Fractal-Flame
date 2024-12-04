package backendacademy.FractalFlame;

import backendacademy.FractalFlame.Render.MultiThreaded;
import backendacademy.FractalFlame.Render.SingleThreaded;
import backendacademy.FractalFlame.Structures.FractalStructure;
import backendacademy.FractalFlame.Transformations.VariationsGetter;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    public void testMultiFasterSingle() {
        FractalStructure fractal =
            FractalStructure.create(300, 1000, 10, 500, 500, 0, VariationsGetter.getRandomVariations());
        Runnable single = () -> SingleThreaded.getFractalImage(fractal);
        Runnable multi = () -> MultiThreaded.getFractalImage(fractal);
        long singleTime = Main.getTime(single);
        long multiTime = Main.getTime(multi);
        assertTrue(singleTime > multiTime);
    }

    @Test
    public void testGetTime() {
        Runnable test = () -> {return;};
        assertTrue(Main.getTime(test) > 0);
    }

    @Test
    public void testCorrectIntInput() {
        Main.in = new Scanner("-1\nabc\n*\nf\n98+7\n15\n0\nп\n5");
        assertEquals(5, Main.correctIntInput(1, 10));
    }

    @Test
    void testCheckForInt_CorrectInput() {
        Optional<Integer> result = Main.checkForInt("20");
        assertTrue(result.isPresent());
        assertEquals(20, result.get());

        result = Main.checkForInt("0");
        assertTrue(result.isPresent());
        assertEquals(0, result.get());

        result = Main.checkForInt("-20");
        assertTrue(result.isPresent());
        assertEquals(-20, result.get());
    }

    @Test
    void testCheckForInt_IncorrectInput() {
        Optional<Integer> result = Main.checkForInt("abc");
        assertFalse(result.isPresent());
        result = Main.checkForInt("7оо");
        assertFalse(result.isPresent());
        result = Main.checkForInt("5.5");
        assertFalse(result.isPresent());
    }

    @Test
    public void testSegment() {
        String result = Main.segment(1, 10);
        assertEquals("от 1 до 10", result);
        result = Main.segment(-5, 5);
        assertEquals("от -5 до 5", result);
    }

    @Test
    void testCorrectPathInput() {
        Main.in = new Scanner("qwerty\nqwe?rty\n**\nDeFaUlT\nsrc/main\ndefault\n");
        Path test1 = Main.correctPathInput();
        Path test2 = Main.correctPathInput();
        assertEquals("src" + File.separatorChar + "main", test1.toString());
        assertEquals(Main.DEFAULT_PATH_FOR_IMAGE, test2);
    }
}
