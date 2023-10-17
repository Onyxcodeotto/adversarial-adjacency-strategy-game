public class GameState{

    private int roundRemaining;
    private char aiPiece;
    private char playerPiece;
    // Map
    private PseudoMap map;
    private int playerScore;
    private int botScore;
    private int currentRound;

    public static final int ROW = 8;
    public static final int COL = 8;
    /*We may store stack of move to store last move so we dont need parameter
     * for getLastMove() and reverse()
    */
    
    public GameState() {

    }

    public boolean isGameEnded(){
        return roundRemaining==0;
    }
    public Coordinate[] getEmptyTile(){
        return (new Coordinate[64]); //MASIH PROTOTYPE!
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
        Coordinate coor = new Coordinate(0,0);
        return (coor);  //MASIH PROTOTYPE!
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getBotScore() {
        return botScore;
    }

    public PseudoMap getMap(){
        return this.map;
    }
}