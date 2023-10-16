public class GameState{

    private int roundRemaining;
    private char aiPiece;
    private char playerPiece;
    // Map
    private PseudoMap map;
    private int playerScore;
    private int botScore;
    private int currentRound;
    /*We may store stack of move to store last move so we dont need parameter
     * for getLastMove() and reverse()
    */
    public boolean isGameEnded(){
        return roundRemaining==0;
    }
    public Coordinate[] getEmptyTile(){
        
    }
    public char getAIPiece(){
        return this.aiPiece;
    }
    public char getPlayerPiece(){
        return this.playerPiece;
    }
    public void fill(Coordinate move, char piece){

    }
    public void reverse(Coordinate move){
        
    }
    public Coordinate getLastMove(){

    }
}