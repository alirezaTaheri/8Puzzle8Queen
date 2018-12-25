
import java.util.Random;
public class EightQueen {
    private static int n ;
    private static int stepsClimbedAfterLastRestart = 0;
    private static int stepsClimbed =0;
    private static int heuristic = 0;
    private static int randomRestarts = 0;

    public static Queen[] generateBoard() {
        Queen[] startBoard = new Queen[n];
        Random rndm = new Random();
        for(int i=0; i<n; i++){
            startBoard[i] = new Queen(rndm.nextInt(n), i);
        }
        return startBoard;
    }

    private static void printState (Queen[] state) {
        int[][] tempBoard = new int[n][n];
        for (int i=0; i<n; i++) {
            tempBoard[state[i].getRow()][state[i].getColumn()]=1;
        }
        System.out.println();
        for (int i=0; i<n; i++) {
            for (int j= 0; j < n; j++) {
                System.out.print(tempBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static int findHeuristic (Queen[] state) {
        int heuristic = 0;
        for (int i = 0; i< state.length; i++) {
            for (int j=i+1; j<state.length; j++ ) {
                if (state[i].ifConflict(state[j])) {
                    heuristic++;
                }
            }
        }
        return heuristic;
    }

    public static Queen[] nextBoard (Queen[] presentBoard) {
        Queen[] nextBoard = new Queen[n];
        Queen[] tmpBoard = new Queen[n];
        int presentHeuristic = findHeuristic(presentBoard);
        int bestHeuristic = presentHeuristic;
        int tempH;

        for (int i=0; i<n; i++) {
            nextBoard[i] = new Queen(presentBoard[i].getRow(), presentBoard[i].getColumn());
            tmpBoard[i] = nextBoard[i];
        }
        for (int i=0; i<n; i++) {
            if (i>0)
                tmpBoard[i-1] = new Queen (presentBoard[i-1].getRow(), presentBoard[i-1].getColumn());
            tmpBoard[i] = new Queen (0, tmpBoard[i].getColumn());
            for (int j=0; j<n; j++) {
                tempH = findHeuristic(tmpBoard);
                if (tempH < bestHeuristic) {
                    bestHeuristic = tempH;
                    for (int k=0; k<n; k++) {
                        nextBoard[k] = new Queen(tmpBoard[k].getRow(), tmpBoard[k].getColumn());
                    }
                }
                if (tmpBoard[i].getRow()!=n-1)
                    tmpBoard[i].move();
            }
        }
        if (bestHeuristic == presentHeuristic) {
            randomRestarts++;
            stepsClimbedAfterLastRestart = 0;
            nextBoard = generateBoard();
            heuristic = findHeuristic(nextBoard);
        } else
            heuristic = bestHeuristic;
        stepsClimbed++;
        stepsClimbedAfterLastRestart++;
        return nextBoard;
    }

    public static void main(String[] args) {
        int presentHeuristic;
        n = 8;
        System.out.println("8 queens hill climbing:");
        Queen[] presentBoard = generateBoard();
        presentHeuristic = findHeuristic(presentBoard);
        while (presentHeuristic != 0) {
            presentBoard = nextBoard(presentBoard);
            presentHeuristic  = heuristic;
        }
        printState(presentBoard);
        System.out.println("\nSteps: " + stepsClimbed);
    }
}
