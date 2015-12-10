import java.util.ArrayList;
import java.util.List;

public class PolygonArea {
    public static long calculateArea(List<Point> polygon) {
        long area = 0;

        for (int i = 0; i < polygon.size() - 1; i++) {
            area += polygon.get(i).cross(polygon.get(i+1));
        }

        area += polygon.get(polygon.size() - 1).cross(polygon.get(0));

        return area;
    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        while (true) {
            int n = io.getInt();

            if (n == 0) {
                break;
            }

            List<Point> points = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                points.add(new Point(io.getInt(), io.getInt()));
            }

            long area = calculateArea(points);
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
