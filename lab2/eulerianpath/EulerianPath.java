import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hugosa on 15/10/15.
 */
import java.util.*;

/* Authors: Hugo Sandelius & Fabian Schilling */

public class EulerianPath {

    /* Finds a eulerian path in the graph described by the adjacency lists in 'neighors'
    *  'inEdges' is an array, where inEdges[i] is an array of indexes of inEdges to node with index i
    *  'edges' is the total amount of edges
    * */
    public static List<Integer> findEulerianPath(List<LinkedList<Integer>> neighbors, int[] inEdges, int edges) {
        LinkedList<Integer> path = new LinkedList<>();
        int pathPos = 0;

        int index = findStartingPoint(neighbors, inEdges);
        int endPoint = findEndPoint(neighbors, inEdges);

        if (index == -1 || endPoint == -1) {
            return null;
        }

        boolean endAlreadyReached = false;

        int startPoint = index;
        while (true) {
            path.add(pathPos, index);

            if (!neighbors.get(index).isEmpty()) {
                // if we have neighbors, visit them
                index = neighbors.get(index).pollFirst();
            } else {
                /* if we have no unvisited neighbors, we need to have either
                    ended up at our starting point or at the only possible
                    end point for eulerian path. Otherwise, no eulerian
                    path possible
                 */
                if (index != startPoint && index != endPoint) {
                    /* although, we're allowed to end up in this situation one time */
                    if (!endAlreadyReached) {
                        endAlreadyReached = true;
                    } else {
                        return null;
                    }
                }

                // try to find a node in the path with unvisited neighbors
                boolean foundNew = false;
                int i = 0;
                for (int vertex : path) {
                    if (!neighbors.get(vertex).isEmpty()) {
                        startPoint = vertex;
                        pathPos = i;
                        index = neighbors.get(vertex).pollFirst();
                        foundNew = true;
                        break;
                    }
                    i++;
                }

                // if we didn't find any, we're finished
                if (!foundNew) {
                    if (edges > path.size() - 1) {
                        // not all edges are in our path
                        return null;
                    }
                    Collections.reverse(path);
                    return path;
                }
            }
        }
    }

    // returns a starting point, or -1 if no eulerian path possible
    private static int findStartingPoint(List<LinkedList<Integer>> neighbors, int[] inEdges) {
        int i = 0;
        int startingPoint = -2;
        for (LinkedList<Integer> neighborList : neighbors) {
            // if outEdges > inEdges, this should be our starting point
            if (neighborList.size() > inEdges[i]) {
                // but if outEdges > inEdges + 1, no eulerian path possible
                if (neighborList.size() > inEdges[i] + 1) {
                    return -1;
                }
                // but if there is more than one vertex like this, no eulerian path possible
                if (startingPoint != -2) {
                    return -1;
                } else {
                    startingPoint = i;
                }
            }
            i++;
        }

        // if we didn't find a starting point, just start at an arbitrary point
        if (startingPoint == -2) {
            startingPoint = 0;
        }

        return startingPoint;
    }

    // returns an end point, or -1 if no eulerian path possible, or -2 if no specific end point
    private static int findEndPoint(List<LinkedList<Integer>> neighbors, int[] inEdges) {
        int i = 0;
        int endPoint = -2;
        for (LinkedList<Integer> neighborList : neighbors) {
            // if outEdges > inEdges, this should be our starting point
            if (neighborList.size() < inEdges[i]) {
                // but if outEdges > inEdges + 1, no eulerian path possible
                if (neighborList.size() < inEdges[i] - 1) {
                    return -1;
                }
                // but if there is more than one vertex like this, no eulerian path possible
                if (endPoint != -2) {
                    return -1;
                } else {
                    endPoint = i;
                }
            }
            i++;
        }

        return endPoint;
    }
}
