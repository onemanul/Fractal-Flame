package backendacademy.FractalFlame.Structures;

public class Pixel {
    public int red;
    public int green;
    public int blue;
    private int hits;

    public Pixel() {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.hits = 0;
    }

    public int getHits() {
        return hits;
    }

    public void increaseHit() {
        hits++;
    }
}
