package backendacademy.FractalFlame.Transformations;

import backendacademy.FractalFlame.Structures.Point;

public class Exponential  implements Transformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.exp(point.x() - 1) * Math.cos(Math.PI * point.y());
        double newY = Math.exp(point.x() - 1) * Math.sin(Math.PI * point.y());
        return new Point(newX, newY);
    }
}
