

public class BaseBot {
    protected GameState state;
    public int[] move() {
        // create random move
        return new int[]{(int) (Math.random()*8), (int) (Math.random()*8)};
    }
}




