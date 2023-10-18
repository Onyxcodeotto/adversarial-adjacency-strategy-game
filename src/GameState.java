import java.util.Stack;
import java.util.ArrayList;
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
    private Stack<IntermediateMove> moveStack;

    public GameState() {
        this.map = new PseudoMap();
        this.moveStack = new Stack<>();
    }

    public boolean isGameEnded(){
        return roundRemaining==0;
    }

    public ArrayList<Coordinate> getEmptyTile(){
        ArrayList<Coordinate> coorList = new ArrayList<>();
        int i = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (this.map.get(row,col) == ' ') {
                    Coordinate available = new Coordinate(row, col);
                    coorList.add(available);
                    i = i + 1;
                }
            }
        }
        return coorList;
    }

    public char getAIPiece(){
        return this.aiPiece;
    }

    public char getPlayerPiece(){
        return this.playerPiece;
    }
    public void change(int i, int j, char piece){
        if(piece == ' ' || this.map.get(i,j) == ' '){return;}
        if(piece != this.map.get(i,j)){
            if (piece == this.getAIPiece()){
                this.playerScore--;
                this.botScore++;
            }else{
                this.playerScore++;
                this.botScore--;
            }
            this.map.set(i,j,piece);
        }
    }

    public void fill(Coordinate move, char piece){
        char oldPiece, newPiece;
        this.map.set(move.getX(), move.getY(), piece);
        IntermediateMove oldMove = new IntermediateMove();
        oldMove.setCoor(move.getX(), move.getY());
        if (move.getX()-1 >= 0) {
            oldMove.setLeft(this.getMap().get(move.getX()-1, move.getY()));
            this.change(move.getX()-1, move.getY(), piece);
        }

        if (move.getX()+1 <= 7) {
            oldMove.setRight(this.getMap().get(move.getX()+1, move.getY()));
            this.change(move.getX()+1, move.getY(), piece);
        }

        if (move.getY()+1 <= 7) {
            oldMove.setUp(this.getMap().get(move.getX(), move.getY()+1) );
            this.change(move.getX(), move.getY()+1,piece);
        }

        if (move.getY()-1 >= 0) {
            oldMove.setBottom(this.getMap().get(move.getX(), move.getY()-1));
            this.change(move.getX(), move.getY()-1, piece);

        }
        this.moveStack.push(oldMove);
        this.roundRemaining--;
        if(piece == this.getPlayerPiece()){
            this.playerScore++;
        }else{
            this.botScore++;
        }


    }

    public void reverse(){
        IntermediateMove last = moveStack.pop();
        Coordinate coor = last.getCoor();


        if(coor.getX()-1>=0){
            //left
            this.change(coor.getX() -1, coor.getY(), last.getLeft());
        }
        if(coor.getX()+1<=7){
            //right
            this.change(coor.getX() +1, coor.getY(), last.getRight());
        }
        if(coor.getY()-1>=0){
            this.change(coor.getX(), coor.getY()-1, last.getBottom());
        }

        if(coor.getY()+1<=7){
            this.change(coor.getX(), coor.getY()+1, last.getUp());
        }
        if(this.map.get(coor.getX(),coor.getY()) == this.getPlayerPiece()){
            this.playerScore--;
        }else{
            this.botScore--;
        }

        this.map.set(coor,' ');

        this.roundRemaining++;
    }

    public Coordinate getLastMove(){
        return moveStack.peek().getCoor();
    }
    public int getRoundRemaining(){
        return this.roundRemaining;
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
//    public int getRoundRemaining() {
//        return this.roundRemaining;
//    }

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

    public int evaluate(){
        return this.getBotScore()- this.getPlayerScore();
    }
    public void printReport(){
        System.out.print("Round Remaining   : "); System.out.print(this.roundRemaining); System.out.println();
        System.out.print("Player Score      : "); System.out.println(this.playerScore);
        System.out.print("Bot Score         : "); System.out.println(this.botScore);
        System.out.println("Map:");
        for(int i =0; i<ROW;i++){

            for(int j= 0; j<COL;j++){
                System.out.print(this.map.get(i,j));
                System.out.print('|');
            }
            System.out.println("");

        }

    }
}