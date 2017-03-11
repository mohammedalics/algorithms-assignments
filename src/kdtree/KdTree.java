package kdtree;

import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/******************************************************************************
 * Name: Mohammed Ali NetID: N/A Precept: N/A
 *
 * Partner Name: N/A Partner NetID: N/A Partner Precept: N/A
 * 
 * Description: KdTree.
 ******************************************************************************/
public class KdTree {

    private Node root;
    private int size;

    
    /**
     * Node class.
     * 
     * @author
     *
     */
    private class Node {
        private Node right;
        private Node left;
        private Point2D point;
        private int level;
        private RectHV rect;

        public Node(Point2D point, Node right, Node left, RectHV rect, int level) {
            super();
            this.point = point;
            this.right = right;
            this.left = left;
            this.rect = rect;
            this.level = level;
        }
    }

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * is the set empty?
     * 
     * @return
     */
    public boolean isEmpty() {
        return root == null;

    }

    /**
     * number of points in the set
     * 
     * @return
     */
    public int size() {
        return size;

    }

    /**
     * add the point to the set (if it is not already in the set)
     * 
     * @param p
     */
    public void insert(Point2D p) {

        root = insert(p, root);
    }

    /**
     * does the set contain point p?
     * 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return contains(root, p);

    }

    private boolean contains(Node n, Point2D p) {
        if (n == null) {
            return false;
        }

        if (n.point.equals(p)) {
            return true;
        }

        // vertical
        if (n.level % 2 == 0) {
            if (n.point.x() > p.x()) {
                return contains(n.left, p);
            }
            else {
                return contains(n.right, p);
            }
        }
        else {
            if (n.point.y() > p.y()) {
                return contains(n.left, p);
            }
            else {
                return contains(n.right, p);
            }
        }
    }

    private Node insert(Point2D p, Node n) {
        // Check if p is the first node.
        if (n == null) {
            size++;
            return new Node(p, null, null, new RectHV(0, 0, 1, 1), 0);
        }

        if (n.point.equals(p)) {
            return n;
        }

        // vertical
        if (n.level % 2 == 0) {

            // if the point to be inserted has a smaller y-coordinate than the
            // point in the node, go left

            if (p.x() < n.point.x()) {

                if (n.left == null) {

                    RectHV rect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.point.x(), n.rect.ymax());

                    n.left = new Node(p, null, null, rect, n.level + 1);
                }

                else {
                    insert(p, n.left);
                }

            }
            // go right.
            else {
                if (n.right == null) {

                    RectHV rect = new RectHV(n.point.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());

                    n.right = new Node(p, null, null, rect, n.level + 1);
                }

                else {
                    insert(p, n.right);
                }

            }
        }
        // horizontal
        else {

            // if the point to be inserted has a smaller x-coordinate than the
            // point in the node, go down/left
            if (p.y() < n.point.y()) {

                if (n.left == null) {

                    RectHV rect = new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.point.y());

                    n.left = new Node(p, null, null, rect, n.level + 1);

                }

                else {
                    insert(p, n.left);
                }
            }
            // else, go up/right.
            else {

                if (n.right == null) {

                    RectHV rect = new RectHV(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.rect.ymax());

                    n.right = new Node(p, null, null, rect, n.level + 1);
                }

                else {
                    insert(p, n.right);
                }
            }
        }
        size++;
        return n;
    }


    /**
     * draw all points to standard draw
     */
    public void draw() {
        draw(root);
    }

    /**
     * draw
     * 
     * @param node
     */
    private void draw(final Node node) {
        if (node == null)
            return;
        StdDraw.point(node.point.x(), node.point.y());
        draw(node.left);
        draw(node.right);
    }

    /**
     * all points that are inside the rectangle
     * 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> range = new LinkedList<Point2D>();
        range(root, rect, range);
        return range;

    }

    /**
     * 
     * @param n
     * @param rect
     * @param range
     */
    private void range(Node n, RectHV rect, LinkedList<Point2D> range) {
        if (n == null) {
            return;
        }

        if (rect.contains(n.point)) {
            range.add(n.point);
        }

        if (n.left != null && rect.intersects(n.left.rect)) {
            range(n.left, rect, range);
        }

        if (n.right != null && rect.intersects(n.right.rect)) {
            range(n.right, rect, range);
        }

    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     * 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node n, Point2D p, Point2D nearest) {
        if (n == null) {
            return nearest;
        }

        if (p.distanceSquaredTo(n.point) < p.distanceSquaredTo(nearest)) {
            nearest = n.point;
        }

        // vertical
        if (n.level % 2 == 0) {

            if (n.point.x() > p.x()) {

                nearest = nearest(n.right, p, nearest);

                if (n.left != null && nearest.distanceSquaredTo(p) > n.left.rect.distanceSquaredTo(p)) {

                    nearest = nearest(n.left, p, nearest);
                }
            }
            else {
                nearest = nearest(n.left, p, nearest);

                if (n.right != null && nearest.distanceSquaredTo(p) > n.right.rect.distanceSquaredTo(p)) {

                    nearest = nearest(n.right, p, nearest);
                }
            }
        }
        else {

            if (n.point.y() > p.y()) {

                nearest = nearest(n.right, p, nearest);

                if (n.left != null && nearest.distanceSquaredTo(p) > n.left.rect.distanceSquaredTo(p)) {

                    nearest = nearest(n.left, p, nearest);
                }
            }
            else {
                nearest = nearest(n.left, p, nearest);

                if (n.right != null && nearest.distanceSquaredTo(p) > n.right.rect.distanceSquaredTo(p)) {

                    nearest = nearest(n.right, p, nearest);
                }
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
        KdTree tree = new KdTree();
        StdDraw.enableDoubleBuffering();

        In in = new In(args[0]);
        while (in.hasNextLine()) {
            String[] line = in.readLine().split(" ");
            tree.insert(new Point2D(Double.parseDouble(line[0]), Double.parseDouble(line[1])));
        }
        tree.draw();
        StdDraw.show();

    }
}