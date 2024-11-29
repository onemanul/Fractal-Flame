package backendacademy.FractalFlame.Transformations;

import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class VariationsGetterTest {
    @Test
    public void testGet_ValidInput() {
        VariationsGetter.in = new Scanner("1\n1\n1\n0\n");
        Transformation[] result = VariationsGetter.get();
        assertEquals(3, result.length);
        assertEquals(result[0].getClass(), result[1].getClass());
        assertEquals(result[1].getClass(), result[2].getClass());
    }

    @Test
    public void testGet_InvalidInput() {
        VariationsGetter.in = new Scanner("-1\nabc\n*\nf\n98+7\n1\n0\n");
        Transformation[] result = VariationsGetter.get();
        assertEquals(1, result.length);
    }

    @Test
    public void testGet_ChooseRandom() {
        VariationsGetter.in = new Scanner("0\n");
        Transformation[] result = VariationsGetter.get();
        assertTrue(result.length > 0);
    }

    @Test
    public void testGetRandomVariations() {
        Transformation[] result = VariationsGetter.getRandomVariations();
        assertTrue(result.length > 0);
    }

    @Test
    void testCheckForInt_CorrectInput() {
        Optional<Integer> result = VariationsGetter.checkForInt("5", 10);
        assertTrue(result.isPresent());
        assertEquals(5, result.get());

        result = VariationsGetter.checkForInt("10", 10);
        assertTrue(result.isPresent());
        assertEquals(10, result.get());

        result = VariationsGetter.checkForInt("0", 10);
        assertTrue(result.isPresent());
        assertEquals(0, result.get());
    }

    @Test
    void testCheckForInt_IncorrectInput() {
        Optional<Integer> result = VariationsGetter.checkForInt("abc", 10);
        assertFalse(result.isPresent());

        result = VariationsGetter.checkForInt("-1", 10);
        assertFalse(result.isPresent());

        result = VariationsGetter.checkForInt("a", 10);
        assertFalse(result.isPresent());

        result = VariationsGetter.checkForInt("11", 10);
        assertFalse(result.isPresent());
    }
}
