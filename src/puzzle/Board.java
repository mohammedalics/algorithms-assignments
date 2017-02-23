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
 *  Description:  Board. 
 ******************************************************************************/

import java.util.LinkedList;

public final class Board {

    private final int[][] blocks;
    // private Board prev;

    /**
     * Construct a board from an n-by-n array of blocks (where blocks[i][j] =
     * block in row i, column j)
     * 
     * @param blocks
     */
    public Board(int[][] blocks) {

        // Check if blocks is null
        if (blocks == null) {
            throw new NullPointerException();
        }

        this.blocks = cloneArray(blocks);
        // prev = null;

    }

    public int dimension() // board dimension n
    {
        return blocks.length;
    }

    /**
     * Number of blocks out of place.
     * 
     * @return
     */
    public int hamming() {
        int hamming = 0;
        int counter = 1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != counter++ && blocks[i][j] != 0) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Sum of Manhattan distances between blocks and goal
     * 
     * @return
     */
    public int manhattan() {
        int manhattan = 0;
        int counter = 1;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != counter++ && blocks[i][j] != 0) {
                    int jDash = (blocks[i][j] - 1) % blocks.length;
                    int iDash = (blocks[i][j] - 1 - jDash) / blocks.length;
                    // System.out.println("For + " + blocks[i][j] + " , manh = "
                    // + (Math.abs(i - iDash) + Math.abs(j - jDash) ));
                    manhattan += Math.abs(i - iDash) + Math.abs(j - jDash);
                }
            }
        }
        return manhattan;
    }

    /**
     * Is this board the goal board?
     * 
     * @return true if the board matches the goal board.
     */
    public boolean isGoal() {
        // int counter = 1;
        // for (int i = 0; i < blocks.length; i++) {
        // for (int j = 0; j < blocks.length; j++) {
        // if (counter < blocks.length * blocks.length && blocks[i][j] !=
        // counter++) {
        // return false;
        // }
        // }
        // }
        // // Check if the last cell is empty.
        // if (blocks[blocks.length - 1][blocks.length - 1] != 0) {
        // return false;
        // }
        // return true
        return manhattan() == 0 || hamming() == 0;
    }

    public Board twin() // a board that is obtained by exchanging any pair of
    {
//        int[][] bloksCopy = cloneArray(blocks);

//        int[] index1 = getRandomIndex();
//        int[] index2 = getRandomIndex();

//        int temp = bloksCopy[index1[0]][index1[1]];
//        bloksCopy[index1[0]][index1[1]] = bloksCopy[index2[0]][index2[1]];
//        bloksCopy[index2[0]][index2[1]] = temp;

//        return new Board(bloksCopy);
        
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length - 1; col++)
                if (blocks[row][col] != 0 && blocks[row][col+1] != 0)
                    return new Board(swap(row, col, row, col + 1));
        throw new RuntimeException();
    }
    
    private int[][] swap(int row1, int col1, int row2, int col2) {
        int[][] copy = cloneArray(blocks);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

//    private int[] getRandomIndex() {
//        int i = StdRandom.uniform(blocks.length);
//        int j = StdRandom.uniform(blocks.length);
//        while (blocks[i][j] == 0) {
//            int[] indexs = getRandomIndex();
//            i = indexs[0];
//            j = indexs[1];
//        }
//        return new int[] { i, j };
//    }

    

    @Override
    public boolean equals(Object y) // does this board equal y?
    {
        if (y == null)
            return false;
        if (getClass() != y.getClass())
            return false;
        Board that = (Board) y;
        
        if (blocks.length != that.blocks.length) {
            return false;
        }
        
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                if (blocks[i][j] != that.blocks[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Clones the provided array
     * 
     * @param src
     * @return a new clone of the provided array
     */
    private static int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public Iterable<Board> neighbors() // all neighboring boards
    {
        /*
         * for possible places consider that we have 0 in i,j so : - i-1, j -
         * i+1 , j - i, j-1 - i, j+1
         */
//        Board[] neighbors = new Board[4];
//        // get the index of 0;
//        int i = 0, j = 0;
//        boolean found = false;
//        for (i = 0; i < blocks.length; i++) {
//            for (j = 0; j < blocks.length; j++) {
//                if (blocks[i][j] == 0) {
//                    found = true;
//                    break;
//                }
//            }
//            if (found)
//                break;
//        }
//        int current = 0;
//        if (i - 1 >= 0) {
//            int[][] blocksCopy = cloneArray(blocks);
//            blocksCopy[i][j] = blocksCopy[i - 1][j];
//            blocksCopy[i - 1][j] = 0;
//            Board newBoard = new Board(blocksCopy);
//            // newBoard.prev = this;
//            neighbors[current++] = newBoard;
//        }
//        if (i + 1 < blocks.length) {
//            int[][] blocksCopy = cloneArray(blocks);
//            blocksCopy[i][j] = blocksCopy[i + 1][j];
//            blocksCopy[i + 1][j] = 0;
//            Board newBoard = new Board(blocksCopy);
//            // newBoard.prev = this;
//            neighbors[current++] = newBoard;
//        }
//        if (j - 1 >= 0) {
//            int[][] blocksCopy = cloneArray(blocks);
//            blocksCopy[i][j] = blocksCopy[i][j - 1];
//            blocksCopy[i][j - 1] = 0;
//            Board newBoard = new Board(blocksCopy);
//            // newBoard.prev = this;
//            neighbors[current++] = newBoard;
//        }
//        if (j + 1 < blocks.length) {
//            int[][] blocksCopy = cloneArray(blocks);
//            blocksCopy[i][j] = blocksCopy[i][j + 1];
//            blocksCopy[i][j + 1] = 0;
//            Board newBoard = new Board(blocksCopy);
//            // newBoard.prev = this;
//            neighbors[current++] = newBoard;
//        }
//        return new Iterable<Board>() {
//            @Override
//            public Iterator<Board> iterator() {
//                return new Iterator<Board>() {
//                    private int count = 0;
//                    @Override
//                    public boolean hasNext() {
//                        return count < 4 && neighbors[count] != null;
//                    }
//                    @Override
//                    public Board next() {
//                        if (!hasNext())
//                            throw new NoSuchElementException();
//                        return neighbors[count++];
//                    }
//                };
//            }
//        };
        
        LinkedList<Board> neighbors = new LinkedList<Board>();
        // get the index of 0;
        int i = 0, j = 0;

        boolean found = false;
        for (i = 0; i < blocks.length; i++) {

            for (j = 0; j < blocks.length; j++) {
                if (blocks[i][j] == 0) {
                    found = true;
                    break;
                }
            }
            if (found)
                break;
        }
        if (i - 1 >= 0) {
            int[][] blocksCopy = cloneArray(blocks);
            blocksCopy[i][j] = blocksCopy[i - 1][j];
            blocksCopy[i - 1][j] = 0;
            Board newBoard = new Board(blocksCopy);
            // newBoard.prev = this;
            neighbors.add(newBoard);
        }

        if (i + 1 < blocks.length) {
            int[][] blocksCopy = cloneArray(blocks);
            blocksCopy[i][j] = blocksCopy[i + 1][j];
            blocksCopy[i + 1][j] = 0;
            Board newBoard = new Board(blocksCopy);
            // newBoard.prev = this;
            neighbors.add(newBoard);
        }

        if (j - 1 >= 0) {
            int[][] blocksCopy = cloneArray(blocks);
            blocksCopy[i][j] = blocksCopy[i][j - 1];
            blocksCopy[i][j - 1] = 0;
            Board newBoard = new Board(blocksCopy);
            // newBoard.prev = this;
            neighbors.add(newBoard);
        }

        if (j + 1 < blocks.length) {
            int[][] blocksCopy = cloneArray(blocks);
            blocksCopy[i][j] = blocksCopy[i][j + 1];
            blocksCopy[i][j + 1] = 0;
            Board newBoard = new Board(blocksCopy);
            // newBoard.prev = this;
            neighbors.add(newBoard);
        }

        return neighbors;

    }

    
    public String toString() // string representation of this board (in the
                             // output format specified below)
    {
        
        StringBuilder result = new StringBuilder();
        result.append(blocks.length + "\n");
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                result.append(" " + blocks[i][j] + " ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
    }

    
}