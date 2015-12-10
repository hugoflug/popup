import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static IO io = new IO(System.in, System.out);

    public static void main(String[] args) {

        convexHull();
        //segmentDistance();

    }

    public static void convexHull() {

        while (io.hasMoreTokens()) {

            int n = io.getInt();
            if (n == 0) break;

            Point[] points = new Point[n];

            for (int i = 0; i < n; i++) {
                points[i] = new Point(io.getDouble(), io.getDouble());
            }

            Point[] hull = Hull.convexHull(points);

            // Remove collinear edges
            ArrayList<Point> finalHull = new ArrayList<>();
            for (int i = 0; i < hull.length - 1; i++) {
                if (hull[i].subtract(hull[i + 1]).equals(Point.ZERO)) {
                    continue;
                }
                finalHull.add(hull[i]);
            }
            finalHull.add(hull[hull.length - 1]);

            io.println(finalHull.size());

            for (Point p: finalHull) {
                io.println(p);
            }

        }

        io.close();
    }

    public static void segmentDistance() {

        int n = io.getInt(); // n <= 10000

        for (int i = 0; i < n; i++) {
            Point a = new Point(io.getDouble(), io.getDouble());
            Point b = new Point(io.getDouble(), io.getDouble());
            Line l1 = new Line(a, b);

            Point c = new Point(io.getDouble(), io.getDouble());
            Point d = new Point(io.getDouble(), io.getDouble());
            Line l2 = new Line(c, d);

            io.println(String.format("%.2f", l1.distanceToLineSegment(l2)));
        }

        io.close();
    }

    public static void pointInPolygon() {
        IO io = new IO(System.in, System.out);
        while (true) {
            int n = io.getInt();

            if (n == 0) {
                break;
            }

            List<Point> polygon = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                polygon.add(new Point(io.getInt(), io.getInt()));
            }

            int m = io.getInt();
            for (int i = 0; i < m; i++) {
                Point p = new Point(io.getInt(), io.getInt());
                int res = PointInPolygon.isInPolygon(p, polygon);
                if (res == -1) {
                    io.println("out");
                } else if (res == 0) {
                    io.println("on");
                } else if (res == 1) {
                    io.println("in");
                }
            }

        }

        io.close();
    }

    public static void polygonArea() {
        IO io = new IO(System.in, System.out);
        while (true) {
            int n = io.getInt();

            if (n == 0) {
                break;
            }

            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                points.add(new Point(io.getInt(), io.getInt()));
            }

            long area = PolygonArea.calculateArea(points);
            if (area < 0) {
                io.print("CW");
            } else {
                io.print("CCW");
            }

            io.println(" " + String.format("%.1f", Math.abs(area)/2.0));
        }

        io.close();
    }
}
