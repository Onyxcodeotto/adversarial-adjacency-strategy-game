public class GameState {

    private int roundRemaining;
    // Map
    private PseudoMap map;
    private int playerScore;
    private int botScore;

    public static final int ROW = 8;
    public static final int COL = 8;
    public static final char aiPiece = 'O';
    public static final char playerPiece = 'X';
    /*We may store stack of move to store last move so we dont need parameter
     * for getLastMove() and reverse()
    */
    
    public GameState() {
        this.map = new PseudoMap();
    }

    public boolean isGameEnded(){
        return roundRemaining==0;
    }

    public Coordinate[] getEmptyTile(){
        Coordinate[] coorList = new Coordinate[64];
        int i = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (map[row][col].equals(' ')) {
                    Coordinate available = new Coordinate(row, col);
                    coorList[i] = available;
                    i = i + 1;
                }
            }
        }
        return (coorList);
    }

    public char getAIPiece(){
        return this.aiPiece;
    }

    public char getPlayerPiece(){
        return this.playerPiece;
    }

    public void fill(Coordinate move, char piece){
        char oldPiece, newPiece;
        this.map.set(move.getX(), move.get(Y), piece);

        if (move.getX()-1 >= 0) {
            oldPiece = this.map.get(move.getX()-1, move.getY());
            this.map.set(move.getX()-1, move.getY(), piece);
            newPiece = this.map.get(move.getX()-1, move.getY());
            if (oldPiece != newPiece) {
                if (piece == 'X') {
                    this.playerScore = this.playerScore + 1;
                    if (oldPiece == 'O') {
                        this.botScore = this.botScore - 1;
                    }
                } else { //piece == 'O'
                    this.botScore = this.botScore + 1;
                    if (oldPiece == 'X') {
                        this.playerScore = this.playerScore - 1;
                    }
                }
            }
        }
        
        if (move.getX()+1 <= 7) {
            oldPiece = this.map.get(move.getX()+1, move.getY());
            this.map.set(move.getX()+1, move.getY(), piece);
            newPiece = this.map.get(move.getX()+1, move.getY());
            if (oldPiece != newPiece) {
                if (piece == 'X') {
                    this.playerScore = this.playerScore + 1;
                    if (oldPiece == 'O') {
                        this.botScore = this.botScore - 1;
                    }
                } else { //piece == 'O'
                    this.botScore = this.botScore + 1;
                    if (oldPiece == 'X') {
                        this.playerScore = this.playerScore - 1;
                    }
                }
            }
        }

        if (move.getY()+1 <= 7) {
            oldPiece = this.map.get(move.getX(), move.getY()+1);
            this.map.set(move.getX(), move.getY()+1, piece);
            newPiece = this.map.get(move.getX(), move.getY()+1);
            if (oldPiece != newPiece) {
                if (piece == 'X') {
                    this.playerScore = this.playerScore + 1;
                    if (oldPiece == 'O') {
                        this.botScore = this.botScore - 1;
                    }
                } else { //piece == 'O'
                    this.botScore = this.botScore + 1;
                    if (oldPiece == 'X') {
                        this.playerScore = this.playerScore - 1;
                    }
                }
            }
        }

        if (move.getY()-1 >= 0) {
            oldPiece = this.map.get(move.getX(), move.getY()-1);
            this.map.set(move.getX(), move.getY()-1, piece);
            newPiece = this.map.get(move.getX(), move.getY()-1);
            if (oldPiece != newPiece) {
                if (piece == 'X') {
                    this.playerScore = this.playerScore + 1;
                    if (oldPiece == 'O') {
                        this.botScore = this.botScore - 1;
                    }
                } else { //piece == 'O'
                    this.botScore = this.botScore + 1;
                    if (oldPiece == 'X') {
                        this.playerScore = this.playerScore - 1;
                    }
                }
            }
        }


    }

    public void reverse(Coordinate move){
        // Ini prosedur buat apa ya?
    }

    public Coordinate getLastMove(){
        // Ini prosedur buat apa ya?
        Coordinate coor = new Coordinate(0,0);
        return (coor); //MASIH PROTOTYPE!!
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

    //SETTER
    public void setRoundRemaining(int roundRemaining) {
        this.roundRemaining = roundRemaining;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public void setBotScore(int botScore) {
        this.botScore = botScore;
    }
}