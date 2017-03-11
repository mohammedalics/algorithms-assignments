package kdtree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/******************************************************************************
 * Name: Mohammed Ali NetID: N/A Precept: N/A
 *
 * Partner Name: N/A Partner NetID: N/A Partner Precept: N/A
 * 
 * Description: PointSET.
 ******************************************************************************/
public class PointSET {

    private TreeSet<Point2D> treeSet;

    /**
     * construct an empty set of points
     */
    public PointSET() {

        treeSet = new TreeSet<Point2D>();

    }

    /**
     * is the set empty?
     * 
     * @return
     */
    public boolean isEmpty() {
        return treeSet.size() == 0;

    }

    /**
     * number of points in the set
     * 
     * @return
     */
    public int size() {
        return treeSet.size();

    }

    /**
     * add the point to the set (if it is not already in the set)
     * 
     * @param p
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        treeSet.add(p);
    }

    /**
     * does the set contain point p?
     * 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }
        return treeSet.contains(p);

    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        for (Iterator<Point2D> iterator = treeSet.iterator(); iterator.hasNext();) {
            Point2D point2d = iterator.next();
            point2d.draw();
        }
    }

    /**
     * all points that are inside the rectangle
     * 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException();
        }

        LinkedList<Point2D> list = new LinkedList<Point2D>();
        for (Iterator<Point2D> iterator = treeSet.iterator(); iterator.hasNext();) {
            Point2D point2d = iterator.next();
            if (rect.contains(point2d)) {
                list.add(point2d);
            }
        }
        return list;

    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException();
        }

        Point2D nearest = null;
        for (Iterator<Point2D> iterator = treeSet.iterator(); iterator.hasNext();) {
            Point2D point2d = iterator.next();
            if (nearest == null || p.distanceTo(point2d) < p.distanceTo(nearest)) {
                nearest = point2d;
            }
        }
        return nearest;

    }

    /**
     * unit testing of the methods (optional)
     * 
     * @param args
     */
    public static void main(String[] args) {

    }
}