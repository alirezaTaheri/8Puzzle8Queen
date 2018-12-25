
import java.util.Random;
import java.util.Stack;

public class EightPuzzle {

    public static void main(String[] args){
        EightPuzzle eight_Puzzle = new EightPuzzle();     
        eight_Puzzle.start();  
    }
    int[][] goal = {
        {1, 2, 3},
        {8, 0, 4},
        {7, 6, 5}
    };

    int[][] board = {
        {2, 6, 3},
        {1, 0, 4},
        {8, 7, 5}
    };
    int emptyTileI = 0;
    int emptyTileJ = 0;
    int stepCounter = 0;

    int min_fn;
    EightPuzzleNode min_fn_node;

    Random random = new Random();
    Stack<EightPuzzleNode> stack_state = new Stack<>();//for backtracking

    public void start() {

        getEmptyTile();
        min_fn = getF(board);

        System.out.println("Start: \n");

        try {
            hillClimbing();
        } catch (Exception e) {
            System.out.println("Goal can not Found" +e);
            echo(min_fn_node.state, "Found: Min F " + min_fn);
        }
    }

    public void hillClimbing() throws Exception {

        while (true) {
            System.out.println("/nSteps: " + (++stepCounter));

            EightPuzzleNode lowestPossible_fn_node = getLowestPossible_fn_node();
            addState(Priority.neighbors_nodeArray);

            echo(lowestPossible_fn_node.state, "-------new state");
            int fnCounter = 1;
            for (int i = 1; i < Priority.neighbors_nodeArray.length; i++) {
                if (Priority.neighbors_nodeArray[i - 1].fn == Priority.neighbors_nodeArray[i].fn) {//fns are equal
                    fnCounter++;
                }
            }
            if (Priority.neighbors_nodeArray.length != 1 && fnCounter == Priority.neighbors_nodeArray.length) {//all fns are equal, equal chances to choose
                System.out.println("---fs are equal local maxima---");

                for (int i = 0; i < Priority.neighbors_nodeArray.length; i++) {
                    if (stack_state != null) {
                        System.out.println("pop " + (i + 1));
                        stack_state.pop();
                    } else {
                        System.out.println("empty stack inside loop");
                    }
                }

                if (stack_state != null) {
                    EightPuzzleNode gameEightPuzzleNode = stack_state.pop();
                    board = gameEightPuzzleNode.state;
                    Priority.preState = gameEightPuzzleNode.parent;
                    getEmptyTile();

                    echo(board, "popped state from all equal fn");
                    System.out.println("empty tile position: " + emptyTileI + ", " + emptyTileJ);
                } else {
                    System.out.println("stack empty inside first lm check");
                }
            } else {

                System.out.println("lowest fn: " + lowestPossible_fn_node.fn);
                if (lowestPossible_fn_node.fn == 0) {
                    System.out.println("Complete");
                    System.out.println("Total steps: " + stepCounter);
                    break;
                }

                if (lowestPossible_fn_node.fn <= min_fn) {
                    min_fn = lowestPossible_fn_node.fn;
                    min_fn_node = lowestPossible_fn_node;

                    if (stack_state != null) {
                        EightPuzzleNode gameEightPuzzleNode = stack_state.pop();
                        board = gameEightPuzzleNode.state;
                        Priority.preState = gameEightPuzzleNode.parent;
                        getEmptyTile();

                        echo(board, "-------new state as going deeper");
                        System.out.println("empty tile position: " + emptyTileI + ", " + emptyTileJ);
                    } else {
                        System.out.println("stack empty");
                    }

                } else {
                    System.out.println("local maxima");
                    System.out.println("getting higher, not possible");
                
                    for (int i = 0; i < Priority.neighbors_nodeArray.length; i++) {
                        if (stack_state != null) {
                            stack_state.pop();
                        } else {
                            System.out.println("empty stack inside loop");
                        }

                    }
                    if (stack_state != null) {
                        EightPuzzleNode gameEightPuzzleNode = stack_state.pop();
                        board = gameEightPuzzleNode.state;
                        Priority.preState = gameEightPuzzleNode.parent;
                        getEmptyTile();

                        echo(board, "popped state from getting higher");
                        System.out.println("empty tile position: " + emptyTileI + ", " + emptyTileJ);
                    } else {
                        System.out.println("stack empty inside second lm check");
                    }
                }
            }
        }
    }

    private EightPuzzleNode getLowestPossible_fn_node() {

        if (emptyTileI == 0 && emptyTileJ == 0) {
            EightPuzzleNode fn_array[] = {getDownF(), getRightF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 0 && emptyTileJ == 1) {
            EightPuzzleNode fn_array[] = {getLeftF(), getDownF(), getRightF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 0 && emptyTileJ == 2) {
            EightPuzzleNode fn_array[] = {getLeftF(), getDownF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 1 && emptyTileJ == 0) {
            EightPuzzleNode fn_array[] = {getDownF(), getRightF(), getUpF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 1 && emptyTileJ == 1) {
            EightPuzzleNode fn_array[] = {getLeftF(), getDownF(), getRightF(), getUpF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 1 && emptyTileJ == 2) {
            EightPuzzleNode fn_array[] = {getLeftF(), getDownF(), getUpF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 2 && emptyTileJ == 0) {
            EightPuzzleNode fn_array[] = {getRightF(), getUpF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 2 && emptyTileJ == 1) {
            EightPuzzleNode fn_array[] = {getLeftF(), getRightF(), getUpF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;

        } else if (emptyTileI == 2 && emptyTileJ == 2) {
            EightPuzzleNode fn_array[] = {getLeftF(), getUpF()};
            EightPuzzleNode lowest_fn_node = Priority.sort(fn_array);
            return lowest_fn_node;
        }
        return null;
    }

    private EightPuzzleNode getLeftF() {

        int left_state[][] = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (i == emptyTileI && j == emptyTileJ) {
                    left_state[i][j] = board[i][j - 1];
                    left_state[i][j - 1] = board[i][j];
                } else {
                    left_state[i][j] = board[i][j];
                }
            }
        }
        echo(left_state, "left state");
        EightPuzzleNode node = new EightPuzzleNode(getF(left_state), left_state, board);
        return node;
    }

    private EightPuzzleNode getRightF() {

        int right_state[][] = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (i == emptyTileI && j == emptyTileJ) {
                    right_state[i][j] = board[i][j + 1];
                    right_state[i][j + 1] = board[i][j];
                    j++;
                } else {
                    right_state[i][j] = board[i][j];
                }
            }
        }

        echo(right_state, "right state");
        EightPuzzleNode node = new EightPuzzleNode(getF(right_state), right_state, board);
        return node;
    }

    private EightPuzzleNode getUpF() {

        int up_state[][] = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (i == emptyTileI && j == emptyTileJ) {
                    up_state[i][j] = board[i - 1][j];
                    up_state[i - 1][j] = board[i][j];
                } else {
                    up_state[i][j] = board[i][j];
                }
            }
        }
        echo(up_state, "up state");
        EightPuzzleNode node = new EightPuzzleNode(getF(up_state), up_state, board);
        return node;
    }

    private EightPuzzleNode getDownF() {

        int down_state[][] = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if ((i - 1) == emptyTileI && j == emptyTileJ) {
                    down_state[i][j] = board[i - 1][j];
                    down_state[i - 1][j] = board[i][j];
                } else {
                    down_state[i][j] = board[i][j];
                }
            }
        }
        echo(down_state, "down state");
        EightPuzzleNode node = new EightPuzzleNode(getF(down_state), down_state, board);
        return node;
    }

    private int getF(int[][] game_state) {

        int fn_count = 0;
        for (int i = 0; i < game_state.length; i++) {
            for (int j = 0; j < game_state[0].length; j++) {
                if (game_state[i][j] != goal[i][j] && game_state[i][j] != 0) {//found misplaced tiles
                    fn_count++;
                }
            }
        }
        return fn_count;
    }

    private void addState(EightPuzzleNode nodeArray[]) {
        for (int i = nodeArray.length - 1; i >= 0; i--) {
            stack_state.add(nodeArray[i]);
        }
    }

    private void getEmptyTile() {

        nestedloop:
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    emptyTileI = i;
                    emptyTileJ = j;
                    break nestedloop;
                }
            }
        }
    }

    private void echo(int[][] state, String message) {
        System.out.println(message);
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                System.out.print(state[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}
