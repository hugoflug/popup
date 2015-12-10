import java.util.ArrayList;
import java.util.List;

/** Authors: Hugo Sandelius & Fabian Schilling */

public class PolygonArea {

    /* Returns the 'signed area' a of the polygon
    *  where a = -area if the points are given in clockwise order
    *  and a = area if the points are given in counter-clockwise order
    * */
    public static long calculateArea(List<Point> polygon) {
        long area = 0;

        for (int i = 0; i < polygon.size() - 1; i++) {
            area += polygon.get(i).cross(polygon.get(i+1));
        }

        area += polygon.get(polygon.size() - 1).cross(polygon.get(0));

        return area;
    }
}
