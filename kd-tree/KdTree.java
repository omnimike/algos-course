
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {

    private Node root = null;
    private int size = 0;

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        boolean inserted = false;
        if (root == null) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            inserted = true;
        } else {
            inserted = insert(root, p, true);
        }
        if (inserted) {
            size++;
        }
    }

    private boolean insert(Node parent, Point2D point, boolean vertical) {
        if (parent.point.compareTo(point) == 0) {
            return false;
        }
        if (vertical ? parent.point.x() > point.x() : parent.point.y() > point.y()) {
            // Point goes left/down
            if (parent.lb == null) {
                RectHV parentRect = parent.rect;
                RectHV rect = new RectHV(
                    parentRect.xmin(),
                    parentRect.ymin(),
                    vertical ? parent.point.x() : parentRect.xmax(),
                    !vertical ? parent.point.y() : parentRect.ymax()
                );
                parent.lb = new Node(point, rect);
                return true;
            } else {
                return insert(parent.lb, point, !vertical);
            }
        } else {
            // Point goes right/up
            if (parent.rt == null) {
                RectHV parentRect = parent.rect;
                RectHV rect = new RectHV(
                    vertical ? parent.point.x() : parentRect.xmin(),
                    !vertical ? parent.point.y() : parentRect.ymin(),
                    parentRect.xmax(),
                    parentRect.ymax()
                );
                parent.rt = new Node(point, rect);
                return true;
            } else {
                return insert(parent.rt, point, !vertical);
            }
        }
    }

    public boolean contains(Point2D p) {
        return contains(root, p, true);
    }

    private boolean contains(Node node, Point2D point, boolean vertical) {
        if (node == null) {
            return false;
        }
        if (node.point.compareTo(point) == 0) {
            return true;
        }
        if (vertical ? node.point.x() > point.x() : node.point.y() > point.y()) {
            return contains(node.lb, point, !vertical);
        } else {
            return contains(node.rt, point, !vertical);
        }
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean vertical) {
        if (node == null) {
            return;
        }
        if (vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.003);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.003);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        draw(node.lb, !vertical);
        draw(node.rt, !vertical);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());
    }

    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> results = new ArrayList<Point2D>();
        range(results, root, rect);
        return results;
    }

    private void range(ArrayList<Point2D> results, Node node, RectHV rect) {
        if (node != null && node.rect.intersects(rect)) {
            if (rect.contains(node.point)) {
                results.add(node.point);
            }
            range(results, node.lb, rect);
            range(results, node.rt, rect);
        }
    }

    public Point2D nearest(Point2D p) {
        if (root != null) {
            return nearest(root, root.point, p);
        } else {
            return null;
        }
    }

    private Point2D nearest(Node node, Point2D closestSoFar, Point2D point) {
        double distToPoint = node.point.distanceSquaredTo(point);
        double distToClosest = closestSoFar.distanceSquaredTo(point);
        if (distToPoint < distToClosest) {
            closestSoFar = node.point;
            distToClosest = distToPoint;
        }
        Node firstToSearch;
        Node secondToSearch;
        if (node.lb != null && node.lb.rect.contains(point)) {
            firstToSearch = node.lb;
            secondToSearch = node.rt;
        } else {
            firstToSearch = node.rt;
            secondToSearch = node.lb;
        }
        if (firstToSearch != null &&
            firstToSearch.rect.distanceSquaredTo(point) < distToClosest
        ) {
            Point2D localClosest = nearest(firstToSearch, closestSoFar, point);
            double distToLocalClosest = localClosest.distanceSquaredTo(point);
            if (distToLocalClosest < distToClosest) {
                closestSoFar = localClosest;
                distToClosest = distToLocalClosest;
            }
        }
        if (secondToSearch != null &&
            secondToSearch.rect.distanceSquaredTo(point) < distToClosest
        ) {
            Point2D localClosest = nearest(secondToSearch, closestSoFar, point);
            double distToLocalClosest = localClosest.distanceSquaredTo(point);
            if (distToLocalClosest < distToClosest) {
                closestSoFar = localClosest;
                distToClosest = distToLocalClosest;
            }
        }

        return closestSoFar;
    }

    private static class Node {
        private Point2D point;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV r) {
            point = p;
            rect = r;
            lb = null;
            rt = null;
        }
    }

    public static void main(String[] args) {

    }
}
