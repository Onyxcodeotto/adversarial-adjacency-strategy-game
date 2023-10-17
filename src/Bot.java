public class Bot {
    protected GameState state;

    public Bot(GameState state) {
        this.state = state;
    }

    public int[] move() {
        // create random move
        return new int[]{(int) (Math.random()*8), (int) (Math.random()*8)};
    }

    public void updateGameState(GameState state) {
        this.state = state;
    }
}