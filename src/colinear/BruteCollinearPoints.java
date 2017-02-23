package colinear;

/******************************************************************************
 * Compilation: javac Point.java Execution: java Point Dependencies: none
 * 
 * An immutable data type for points in the plane. For use on Coursera,
 * Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    private LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        
        // Check points if duplicate. 
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new java.lang.IllegalArgumentException();
                }
            }
        }
        
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        
        List<LineSegment> list = new ArrayList<LineSegment>();
        for (int p = 0; p < pointsCopy.length - 3; p++) {
            for (int q = p + 1; q < pointsCopy.length - 2; q++) {
                double pqSlope = pointsCopy[p].slopeTo(pointsCopy[q]);
                for (int r = q + 1; r < pointsCopy.length - 1; r++) {
                    double prSlope = pointsCopy[p].slopeTo(pointsCopy[r]);
                    if (pqSlope != prSlope) {
                        continue;
                    }
                    for (int s = r + 1; s < pointsCopy.length; s++) {
                        double psSlope = pointsCopy[p].slopeTo(pointsCopy[s]);
                        if (pqSlope == psSlope) {
                            list.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                        
                        }
                    }
                }
            }
        }
        
        segments = list.toArray(new LineSegment[list.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        
        return Arrays.copyOf(segments, numberOfSegments());

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
        for (LineSegment seg : collinear.segments()) {
            StdOut.println(seg);
            seg.draw();
        }
        StdDraw.show();
    }

}