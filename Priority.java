
import java.util.Arrays;

public class Priority {
   static int[][] preState;
    static EightPuzzleNode neighbors_nodeArray[];

    public static EightPuzzleNode sort(EightPuzzleNode[] nodeArray) {
        
        if(preState!=null){
            nodeArray = getParentRemovedEightPuzzleNodeArray(nodeArray, preState);//remove parent
        }
        
        for (int i = 0; i < nodeArray.length; i++) {
            for (int j = nodeArray.length - 1; j > i; j--) {
                if (nodeArray[j].fn < nodeArray[j - 1].fn) {
                    EightPuzzleNode temp = nodeArray[j];
                    nodeArray[j] = nodeArray[j - 1];
                    nodeArray[j - 1] = temp;
                }
            }
        }
        Priority.neighbors_nodeArray = nodeArray;
        return nodeArray[0];
    }

    public static EightPuzzleNode[] getParentRemovedEightPuzzleNodeArray(EightPuzzleNode []nodeArray, int[][] preState) {
        EightPuzzleNode[] parentRemovedEightPuzzleNodeArray = new EightPuzzleNode[nodeArray.length - 1];
        int j = 0;
        for (int i = 0; i < nodeArray.length; i++) {
            if (Arrays.deepEquals(nodeArray[i].state, preState)) {
            } else {
                parentRemovedEightPuzzleNodeArray[j] = nodeArray[i];
                j++;
            }
        }
        return parentRemovedEightPuzzleNodeArray;
    }
}

