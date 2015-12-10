import java.util.ArrayList;
import java.util.List;

public class PointInPolygon {
    public static int testPoint(Point p, List<Point> polygon) {
        for (int i = 0; i < polygon.size(); i++) {
            Line l = new Line(polygon.get(i), polygon.get((i+1) % polygon.size()));
            if (l.distanceToPoint(p) == 0) { /* dangerous! */
                //System.err.println("on");
                return 0;
            }
        }

        double angleSum = 0;
        for (int i = 0; i < polygon.size(); i++) {
            Point p0 = polygon.get(i).subtract(p);
            Point p1 = polygon.get((i+1) % polygon.size()).subtract(p);
            angleSum += Math.atan2(p0.cross(p1), p0.dot(p1));
        }


        //System.err.println(angleSum);

        final double THRESHOLD = 0.0001;
        if (Math.abs(angleSum) > Math.PI*2 - THRESHOLD && Math.abs(angleSum) < Math.PI*2 + THRESHOLD) { /* dangerous! */
            return 1;
        }

        return -1;
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
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
                int res = testPoint(p, polygon);
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
}
