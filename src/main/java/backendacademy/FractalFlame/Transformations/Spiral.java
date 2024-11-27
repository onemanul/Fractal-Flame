package backendacademy.FractalFlame.Transformations;

import backendacademy.FractalFlame.Structures.Point;

public class Spiral implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan(point.y() / point.x());
        return new Point((Math.cos(theta) + Math.sin(r)) / r, (Math.sin(theta) - Math.cos(r)) / r);
    }
}
