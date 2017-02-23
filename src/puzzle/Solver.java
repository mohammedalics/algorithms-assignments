package puzzle;


/******************************************************************************
 *  Name:    Mohammed Ali
 *  NetID:   N/A
 *  Precept: N/A
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Solver. 
 ******************************************************************************/

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private MinPQ<CustomBoard> initialPq;
    private MinPQ<CustomBoard> twinPq;
//    private List<Board> solution;
//    private List<Board> solutionTwin;
    private LinkedList<Board> solutionList;
//    private int moves;
//    private Map<String, Integer> allMoves;

//    private List<Move> initialMoves; 
//    private List<Move> twinMoves; 

    private class CustomBoard {
        private CustomBoard parent; 
        private Board board;
      
        
        public CustomBoard(Board board, CustomBoard parent) {
            this.parent = parent; 
            this.board = board; 
        }
        
        public CustomBoard getParent() {
            return parent;
        }
        public Board getBoard() {
            return board;
        }
        
    }
    
 // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Check if initial is null
        if (initial == null) {
            throw new NullPointerException();
        }
//        moves = 0;
//        solution = new ArrayList<Board>();
//        solutionTwin = new ArrayList<Board>();

//        allMoves = new HashMap<>();
//        initialMoves = new ArrayList<Move>();
//        twinMoves = new ArrayList<Move>();

        initialPq = new MinPQ<CustomBoard>(new CustomBoardComparator());
        twinPq = new MinPQ<CustomBoard>(new CustomBoardComparator());

        CustomBoard customInitialBoard = new CustomBoard(initial, null);
        initialPq.insert(customInitialBoard);
        CustomBoard customInitialTwinBoard = new CustomBoard(initial.twin(), null);
        twinPq.insert(customInitialTwinBoard);

        boolean resolvable = false;
        boolean unResolvable = false;

        while (!(resolvable || unResolvable)) {
            CustomBoard customBoard = initialPq.delMin();
            if (customBoard.getBoard().isGoal()) {
                resolvable = true;
                LinkedList<Board> reversedSolutionList = new LinkedList<Board>();
                while (customBoard.getParent() != null) {
                    reversedSolutionList.add(customBoard.board);
                    customBoard = customBoard.getParent();
                }
                reversedSolutionList.add(customBoard.board);
                
                solutionList = new LinkedList<Board>();
                Iterator<Board> iterator = reversedSolutionList.descendingIterator();
                while (iterator.hasNext()) {
                    solutionList.add(iterator.next());
                }
                break;
            }

            CustomBoard customTwinBoard = twinPq.delMin();
            if (customTwinBoard.getBoard().isGoal()) {
                unResolvable = true;
                break;
            }

//            allMoves.put(board.toString(), moves);
//            allMoves.put(boardTwin.toString(), moves);
           
//            ++moves;
            
//            initialMoves = new ArrayList<Move>();
//            twinMoves = new ArrayList<Move>();

//            initialPq = new MinPQ<Board>(new BoardComparator(initialMoves));
//            twinPq = new MinPQ<Board>(new BoardComparator(twinMoves));
            
            for (Board b : customBoard.getBoard().neighbors()) {
                if (isEligibleToBeAddedToPQ(customBoard, b)) {
//                    allMoves.put(b.toString(), moves);
//                    addMove(new Move(b.toString(), moves));
//                    initialMoves.add(new Move(b.toString(), moves));
                    CustomBoard newCustomBoard = new CustomBoard(b, customBoard); 
                    initialPq.insert(newCustomBoard);
                }
            }

            for (Board b : customTwinBoard.getBoard().neighbors()) {
                if (isEligibleToBeAddedToPQ(customTwinBoard, b)) {
//                    allMoves.put(b.toString(), moves);
//                    addMove(new Move(b.toString(), moves));
//                    twinMoves.add(new Move(b.toString(), moves));
                    CustomBoard newCustomBoard = new CustomBoard(b, customTwinBoard); 
                    twinPq.insert(newCustomBoard);
                }
            }
        }

//        if (unResolvable) {
//            solution = new ArrayList<>();
//        }

    }
    
    private boolean isEligibleToBeAddedToPQ(CustomBoard customBoard, Board board) {
        CustomBoard customParentBoard = customBoard.getParent();
        while (customParentBoard != null) {
            if (customParentBoard.getBoard().equals(board)) {
                return false; 
            }
            customParentBoard = customParentBoard.getParent();
        }
        return true; 
    }
    
//    private class Move {
//        private String board; 
//        private int move;
//        public Move(String board, int move) {
//            super();
//            this.board = board;
//            this.move = move;
//        }
//        public String getBoard() {
//            return board;
//        }
//        public int getMove() {
//            return move;
//        }
//        @Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            result = prime * result + getOuterType().hashCode();
//            if (board == null) {
//                result = prime * result;
//            }
//            else {
//                result = prime * result + board.hashCode();
//            }
//            result = prime * result + move;
//            return result;
//        }
//        @Override
//        public boolean equals(Object y) {
//            if (y == null)
//                return false;
//            if (getClass() != y.getClass())
//                return false;
//            Move that = (Move) y;
//            return that.getBoard().equals(this.getBoard());
//        }        
//        private Solver getOuterType() {
//            return Solver.this;
//        }
//    }
    
//    private void addInitialMove(Move m) {
//        if (!initialMoves.contains(m)) {
//            allMoves.add(m);
//        }
//    }
    
//    private void addTwinMove(Move m) {
//        if (!allMoves.contains(m)) {
//            allMoves.add(m);
//        }
//    }

    private class CustomBoardComparator implements Comparator<CustomBoard> {
      
        private int getMove(CustomBoard board) {
            int move = 0;
            if (board != null) {
                move++;
                while (board.getParent() != null) {
                    move++;
                    board = board.getParent();
                }
            }
            return move;
            
        }
        
        @Override
        public int compare(CustomBoard o1, CustomBoard o2) {
            // int o1Moves = allMoves.get(o1.toString());
            // int o2Moves = allMoves.get(o2.toString());
            int o1Moves = getMove(o1);
            int o2Moves = getMove(o2);
            int o1P = o1Moves + o1.getBoard().manhattan();
            int o2P = o2Moves + o2.getBoard().manhattan();
            if (o1P < o2P) {
                return -1;
            }
            else if (o1P > o2P) {
                return 1;
            }
            return 0;
        }

    }

    /**
     * is the initial board solvable?
     * 
     * @return
     */
    public boolean isSolvable() {
//        return solution.size() > 0;
        return solutionList != null;
    }

    /**
     * Min number of moves to solve initial board; -1 if unsolvable
     * @return
     */
    public int moves() 
    {
        if (isSolvable()) {
            return solutionList.size() - 1;
        }
        return -1;
    }

    /**
     * Sequence of boards in a shortest solution; null if unsolvable
     * @return
     */
    public Iterable<Board> solution() 
    {
        if (isSolvable()) {
            return solutionList;    
        }
        return null;
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}