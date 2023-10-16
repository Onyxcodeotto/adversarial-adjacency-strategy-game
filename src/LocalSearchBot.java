public class LocalSearchBot extends BaseBot {
    public LocalSearchBot(GameState state) {
        super(state);
    }

    public void LocalSearchSolve() {
        System.out.println("Hello World!");
    }

    // public int[] move() {
    //     
    // }

    public static void main(String[] args) {
        GameState coba = new GameState();
        LocalSearchBot LSBot = new LocalSearchBot(coba);

        LSBot.LocalSearchSolve();
    }
}