public class IntermediateMove {
     char up;
     char left;
     char right;
     char bottom;
     Coordinate coor;
    public IntermediateMove(){
    };

    public void setUp(char up){
        this.up = up;
    }
    public void setRight(char right){
        this.right = right;
    }
    public void setLeft(char left){
        this.left = left;
    }
    public void setBottom(char bottom){
        this.bottom = bottom;
    }
    public void setCoor(int i, int j){
        this.coor = new Coordinate(i,j);
    }
    public Coordinate getCoor(){return this.coor;}

    public char getUp() {
        return up;
    }

    public char getBottom() {
        return bottom;
    }

    public char getLeft() {
        return left;
    }

    public char getRight() {
        return right;
    }
}
