import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithmBot extends Bot {
    private Population population;
    private char[][] gameMap;
    private final char playerPiece;
    private final char opponentPiece;
    private ArrayList<Integer> emptyTiles;
    private static final int ROWS = 8;
    private static final int COLS = 8;


    public GeneticAlgorithmBot(GameState state) {
        super(state);
        this.playerPiece = state.getAIPiece();
        this.opponentPiece = state.getPlayerPiece();
    }

    public void initializeStates() {
        this.emptyTiles = new ArrayList<>();
        ArrayList<Coordinate> emptyTilesCoordinate = this.state.getEmptyTile();
        for (Coordinate coordinate : emptyTilesCoordinate) {
            this.emptyTiles.add(coordinate.getX() * COLS + coordinate.getY());
        }
        this.gameMap = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                this.gameMap[i][j] = this.state.getMap().get(i, j);
            }
        }
        this.population = new Population(this.state.getRoundRemaining(), this.playerPiece, this.opponentPiece);
//        System.out.println("Round remaining: " + this.state.getRoundRemaining());
        this.population.setGameMap(gameMap);
        this.population.setEmptyTiles(this.emptyTiles);
    }

    @Override
    public int[] move() {
        this.initializeStates();
        int result = this.population.naturalSelection();
        int rowToFill = result / ROWS;
        int colToFill = result % COLS;
        System.out.println("Calculated Move");
        return new int[] {rowToFill, colToFill};
//        try {
//            int result = this.population.naturalSelection();
//            int rowToFill = result / ROWS;
//            int colToFill = result % COLS;
//            System.out.println("Calculated Move");
//            return new int[] {rowToFill, colToFill};
//        } catch (Exception e) {
//            System.out.println("Random Move");
//            System.out.println(e.getMessage());
//            ArrayList<Coordinate> emptyTile = this.state.getEmptyTile();
//            Random random = new Random();
//            int randomIndex = random.nextInt(emptyTile.size());
//            int rowToFill = emptyTile.get(randomIndex).getX();
//            int colToFill = emptyTile.get(randomIndex).getY();
//            return new int[] {rowToFill, colToFill};
//        }
    }

}
