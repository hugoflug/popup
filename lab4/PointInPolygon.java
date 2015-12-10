import java.util.ArrayList;
import java.util.List;

/** Authors: Hugo Sandelius & Fabian Schilling */

public class PointInPolygon {

    /* Returns -1 if point is not in polygon, 1 if point is on polygon edge, 0 if point not in polygon */
    public static int isInPolygon(Point p, List<Point> polygon) {

        /* check if point is on polygon edge */
        for (int i = 0; i < polygon.size(); i++) {
            Line l = new Line(polygon.get(i), polygon.get((i+1) % polygon.size()));
            if (l.distanceToPoint(p) == 0) {
                return 0;
            }
        }

        double angleSum = 0;
        for (int i = 0; i < polygon.size(); i++) {
            Point p0 = polygon.get(i).subtract(p);
            Point p1 = polygon.get((i+1) % polygon.size()).subtract(p);
            angleSum += Math.atan2(p0.cross(p1), p0.dot(p1));
        }

        final double THRESHOLD = 0.0001;
        if (Math.abs(angleSum) > Math.PI*2 - THRESHOLD && Math.abs(angleSum) < Math.PI*2 + THRESHOLD) {
            return 1;
        }

        return -1;
    }

}
