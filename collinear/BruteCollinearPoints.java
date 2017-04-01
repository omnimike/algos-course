import java.util.LinkedList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        LinkedList<LineSegment> lines = new LinkedList<LineSegment>();
        // This is used to sort the points to pick the longest segment
        Point[] collinearPoints = new Point[4];
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point a = points[i];
            for (int j = i + 1; j < n; j++) {
                Point b = points[j];
                double targetSlope = a.slopeTo(b);
                for (int k = j + 1; k < n; k++) {
                    Point c = points[k];
                    if (a.slopeTo(c) == targetSlope) {
                        for (int l = k + 1; l < n; l++) {
                            Point d = points[l];
                            if (a.slopeTo(d) == targetSlope) {
                                collinearPoints[0] = a;
                                collinearPoints[1] = b;
                                collinearPoints[2] = c;
                                collinearPoints[3] = d;
                                Arrays.sort(collinearPoints);
                                lines.add(new LineSegment(collinearPoints[0], collinearPoints[3]));
                            }
                        }
                    }
                }
            }
        }
        segments = lines.toArray(new LineSegment[lines.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
