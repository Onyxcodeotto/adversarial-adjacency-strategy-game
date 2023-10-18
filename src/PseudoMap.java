public class PseudoMap {
    private char[][] map;
    /*
    * */
    public PseudoMap(){
        this.map = new char[8][8];
    }
    public void set(int i, int j, char Piece){
        this.map[i][j] = Piece;
    }
    public void set(Coordinate coor, char Piece){this.map[coor.getX()][coor.getY()] = Piece; }
    public char get(int i, int j){
        return this.map[i][j];
    }
}
