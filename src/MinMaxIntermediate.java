public class MinMaxIntermediate{
    private int val;
    private Coordinate move;
    public MinMaxIntermediate(Coordinate move, int val){
        this.val = val;
        this.move = move;
    }
//    public MinMaxIntermediate(int val){
//        //dummy constructor
//        //masih nggak tahu dipakai atau nggak
//        //biar nggak harus mindahin move
//    }
    public int getVal(){
        return this.val;
    }
    public Coordinate getMove(){
        return this.move;
    }
}
