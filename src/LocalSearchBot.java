public class LocalSearchBot extends Bot {
    private char mark;
    private char opponentMark;
    // Constructor
    public LocalSearchBot(GameState state, char mark, char opponentMark) {
        super(state);
        this.mark = mark;
        this.opponentMark = opponentMark;
    }

    public int evaluateMove(PseudoMap map, int i, int j){
        int score = 0;
        int startRow;
        int endRow;
        int startColumn;
        int endColumn;

        if (i - 1 < 0) {   // If clicked button in first row, no preceding row exists.
            startRow = i;
        }
        else {               // Otherwise, the preceding row exists for adjacency.
            startRow = i - 1;
        }
        if (i + 1 >= this.state.ROW){  // If clicked button in last row, no subsequent/further row exists.
            endRow = i;
        }
        else {               // Otherwise, the subsequent row exists for adjacency.
            endRow = i + 1;
        }
        if (j - 1 < 0) {     // If clicked on first column, lower bound of the column has been reached.
            startColumn = j;
        }
        else {
            startColumn = j - 1;
        }
        if (j + 1 >= this.state.COL) {  // If clicked on last column, upper bound of the column has been reached.
            endColumn = j;
        }
        else{
            endColumn = j + 1;
        }

        for (int x = startRow; x <= endRow; x++) {
            if (map.get(x,j) == opponentMark){
                score++;
            }
        }

        for (int y = startColumn; y <= endColumn; y++) {
            if (map.get(i,y) == opponentMark){
                score++;
            }
        }



        return score;
    }
    // The main function to find the optimum move using local search
    public int[] move() {
        int[] optimumMove = new int[2];
        int neighborValue = -999; // Initialization
        PseudoMap map = this.state.getMap();

        // Generate All possible move => Will be used for generate successors
        Coordinate[] possibleMoves = this.state.getEmptyTile();

        // Evaluate All Successors while search for the neighbor state (optimum move)
        for (Coordinate coor: possibleMoves){
            int successorValue = evaluateMove(map, coor.getX(), coor.getY());
            successorValue += (1 + successorValue); // Plus one for fill the empty and plus successor Value for decrement on opponent score
            if (successorValue > neighborValue){
                neighborValue = successorValue;
                optimumMove[0] = coor.getX();
                optimumMove[1] = coor.getY();
            }
        }
        // Update Current game state if neighbor.value > current.value
        if ((this.state.getPlayerScore()-this.state.getBotScore()) < neighborValue){
            // With the assumption that method fill could update the changed tile
            this.state.fill(new Coordinate(optimumMove[0], optimumMove[1]), this.mark);
        }
        return optimumMove;
    }

//    public static void main(String[] args) {
//        GameState coba = new GameState();
//        LocalSearchBot LSBot = new LocalSearchBot(coba);
//
//        LSBot.LocalSearchSolve();
//    }
}