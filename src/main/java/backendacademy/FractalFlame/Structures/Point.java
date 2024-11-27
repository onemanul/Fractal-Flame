package backendacademy.FractalFlame.Structures;

public record Point(double x, double y) {

    public boolean inRange(double minX, double maxX, double minY, double maxY) {
        return minX <= x && x <= maxX && minY <= y && y <= maxY;
    }
}
