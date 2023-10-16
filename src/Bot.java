
import java.util.ArrayList;
public class BaseBot {
    protected GameState state;
    public int[] move() {
        // create random move
        return new int[]{(int) (Math.random()*8), (int) (Math.random()*8)};
    }

}



public class GameState{

    private int roundRemaining;
    public boolean isGameEnded(){
        return roundRemaining==0;
    }
    public Coordinate[] getEmptyTile(){
        
    }
}

public class PseudoMap{

}

