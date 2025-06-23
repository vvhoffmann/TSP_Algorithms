package wit.wh.utils;

import java.awt.geom.Point2D;

public class Point extends Point2D.Double {
    public Point(double x, double y) {
        super(x, y);
    }

    public double distance(Point b) {
        return Math.hypot(this.x - b.x, this.y - b.y);
    }

    @Override
    public String toString() {
        return " [" + x + ", " + y + ']';
    }
}