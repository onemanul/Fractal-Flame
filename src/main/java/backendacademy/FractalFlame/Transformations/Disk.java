package backendacademy.FractalFlame.Transformations;

import backendacademy.FractalFlame.Structures.Point;

public class Disk implements Transformation {
    @Override
    public Point apply(Point point) {
        double piR = Math.PI * Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan(point.y() / point.x());
        return new Point(theta * Math.sin(piR) / Math.PI, theta * Math.cos(piR) / Math.PI);
    }
}
