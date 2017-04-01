import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] originalPoints) {
        if (originalPoints.length < 4) {
            segments = new LineSegment[0];
            return;
        }
        Point[] originPoints = Arrays.copyOf(originalPoints, originalPoints.length);
        Point[] points = Arrays.copyOf(originalPoints, originalPoints.length);
        Arrays.sort(originPoints);
        ArrayList<LineSegment> lines = new ArrayList<LineSegment>();
        for (int i = 0; i < originPoints.length; i++) {
            Point originPoint = originPoints[i];
            ArrayList<Point> endpoints = new ArrayList<Point>();
            Arrays.sort(points, originPoint.slopeOrder());
            double runningGrad = originPoint.slopeTo(points[1]);
            if (runningGrad == Double.NEGATIVE_INFINITY) {
                throw new IllegalArgumentException("duplicate point detected");
            }
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
                        if (linearPoints[0] == originPoint) {
                            endpoints.add(linearPoints[linearPoints.length - 1]);
                        }
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
                if (linearPoints[0] == originPoint) {
                    endpoints.add(linearPoints[linearPoints.length - 1]);
                }
            }

            Collections.sort(endpoints);
            for (int l = 0; l < endpoints.size(); l++) {
                Point endpoint = endpoints.get(l);
                if (l == 0 || endpoint.compareTo(endpoints.get(l - 1)) != 0) {
                    lines.add(new LineSegment(originPoint, endpoint));
                }
            }
        }
        segments = lines.toArray(new LineSegment[lines.size()]);
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
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
