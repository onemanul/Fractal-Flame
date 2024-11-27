package backendacademy.FractalFlame.Transformations;

import backendacademy.FractalFlame.Structures.Point;

public class Spherical implements Transformation {
    @Override
    public Point apply(Point point) {
        double rSqr = point.x() * point.x() + point.y() * point.y();
        return new Point(point.x() / rSqr, point.y() / rSqr);
    }
}
