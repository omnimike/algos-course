
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class PointSET {

    private SET<Point2D> points = new SET<Point2D>();

    public PointSET() {
        
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        for (Point2D p : points) {
            if (rect.contains(p)) {
                list.add(p);
            }
        }
        return list;
    }

    public Point2D nearest(Point2D p) {
        Point2D nearest = null;
        for (Point2D pt : points) {
            if (nearest == null || p.distanceSquaredTo(pt) < p.distanceSquaredTo(nearest)) {
                nearest = pt;
            }
        }
        return nearest;

    }

    public static void main(String[] args) {

    }
}
