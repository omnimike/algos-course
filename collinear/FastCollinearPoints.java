import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points.length < 4) {
            segments = new LineSegment[0];
            return;
        }
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
        for (int i = 0; i < points.length; i++) {
            Point originPoint = points[i];
            Arrays.sort(points, originPoint.slopeOrder());
            double runningGrad = originPoint.slopeTo(points[1]);
            int runLength = 1;
            for (int k = 2; k < points.length; k++) {
                double slope = originPoint.slopeTo(points[k]);
                if (slope == Double.NEGATIVE_INFINITY) {
                    throw new IllegalArgumentException("duplicate point detected");
                }
                if (slope == runningGrad) {
                    runLength++;
                } else {
                    if (runLength >= 3) {
                        // Add one for the origin point
                        Point[] linearPoints = new Point[runLength + 1];
                        linearPoints[0] = originPoint;
                        for (int j = 1; j <= runLength; j++) {
                            linearPoints[j] = points[k - j];
                        }
                        Arrays.sort(linearPoints);
                        lines.add(new LineSegment(linearPoints[0], linearPoints[linearPoints.length - 1]));
                    }
                    runLength = 1;
                    runningGrad = originPoint.slopeTo(points[k]);
                }
            }
            // This catches the final run
            if (runLength >= 3) {
                // Add one for the origin point
                Point[] linearPoints = new Point[runLength + 1];
                linearPoints[0] = originPoint;
                for (int j = 1; j <= runLength; j++) {
                    linearPoints[j] = points[points.length - j];
                }
                Arrays.sort(linearPoints);
                lines.add(new LineSegment(linearPoints[0], linearPoints[linearPoints.length - 1]));
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
