import java.util.*;

public class Chromosome {
    private ArrayList<Integer> sequence;
    private ArrayList<Integer> emptyTiles;
    private int maxLength;
    private double fitness;


    public Chromosome(ArrayList<Integer> emptyTiles, int maxLength) {
        this.emptyTiles = emptyTiles;
        this.maxLength = maxLength;
        this.generateRandomSequence();
    }

    public Chromosome(Chromosome chromosome) {
        this.sequence = new ArrayList<>();
        this.sequence.addAll(chromosome.getSequence());
        this.emptyTiles = new ArrayList<>();
        this.emptyTiles.addAll(chromosome.getEmptyTiles());
    }

    public ArrayList<Integer> getSequence() {
        return sequence;
    }

    public void setSequence(ArrayList<Integer> sequence) {
        this.sequence = sequence;
    }

    public int getChromosomeSequenceAt(int index) {
        return this.sequence.get(index);
    }

    public void setChromosomeSequenceAt(int index, int element) {
        this.sequence.set(index, element);
    }

    public ArrayList<Integer> getEmptyTiles() {
        return emptyTiles;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setEmptyTiles(ArrayList<Integer> emptyTiles) {
        this.emptyTiles = emptyTiles;
    }
    private void generateRandomSequence() {
        Collections.shuffle(emptyTiles);
        this.sequence = new ArrayList<>(
                this.emptyTiles.subList(
                        0, Math.min(this.emptyTiles.size(), this.maxLength)
                )
        );
    }

    public ArrayList<Chromosome> crossover(Chromosome chromosome) {
        Random random = new Random();
        int crossoverPoint = random.nextInt(this.sequence.size());

        Chromosome firstChild = new Chromosome(this);
        Chromosome secondChild = new Chromosome(chromosome);

        for (int i = crossoverPoint; i < this.sequence.size(); i++) {
            firstChild.setChromosomeSequenceAt(i, chromosome.getChromosomeSequenceAt(i));
        }

        for (int i = crossoverPoint; i < this.sequence.size(); i++) {
            secondChild.setChromosomeSequenceAt(i, this.getChromosomeSequenceAt(i));
        }

        firstChild.mutate();
        secondChild.mutate();

        ArrayList<Chromosome> children = new ArrayList<>();
        if (firstChild.isSequenceValid()) children.add(firstChild);
        if (secondChild.isSequenceValid()) children.add(secondChild);
        return children;
    }

    public void mutate() {
        // Types of mutations: change an element at a random index
        // or swap two elements randomly with 50% probability
        Random random = new Random();
        int mutationPoint = random.nextInt(this.sequence.size());
        int randomElementIndex = random.nextInt(this.emptyTiles.size());
        int randomElement = this.emptyTiles.get(randomElementIndex);
        if (!this.sequence.contains(randomElement)) {
            this.sequence.set(mutationPoint, randomElement);
        }
        else if (random.nextDouble() < 0.5) {
            int firstIndex = random.nextInt(this.sequence.size());
            int secondIndex = random.nextInt(this.sequence.size());
            int temp = this.sequence.get(firstIndex);
            this.sequence.set(firstIndex, secondIndex);
            this.sequence.set(secondIndex, temp);
        }
    }

    public boolean isSequenceValid() {
        Set<Integer> seen = new HashSet<>();
        for (Integer element : this.sequence) {
            if (!seen.add(element)) {
                return false;
            }
        }
        return true;
    }

    public int calculateFinalStateValue(char[][] gameMap, char playerPiece, char opponentPiece) {
        // Assuming gameMap is always 8x8
        final int ROWS = 4;
        final int COLS = 4;

        char[][] gameMapCopy = new char[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameMapCopy[i][j] = gameMap[i][j];
            }
        }

        for (int i = 0; i < this.sequence.size(); i++) {
            int seqElement = this.sequence.get(i);
            int rowToFill = seqElement / ROWS;
            int colToFill = seqElement % COLS;
            char pieceToFill = i % 2 == 0 ? playerPiece : opponentPiece;

            // fill the position
            gameMapCopy[rowToFill][colToFill] = pieceToFill;

            // TODO: extract into a separate method
            int startRow;
            int endRow;
            int startColumn;
            int endColumn;

            if (rowToFill - 1 < 0) {   // If clicked button in first row, no preceding row exists.
                startRow = rowToFill;
            }
            else {               // Otherwise, the preceding row exists for adjacency.
                startRow = rowToFill - 1;
            }
            if (rowToFill + 1 >= ROWS) {  // If clicked button in last row, no subsequent/further row exists.
                endRow = rowToFill;
            }
            else {               // Otherwise, the subsequent row exists for adjacency.
                endRow = rowToFill + 1;
            }
            if (colToFill - 1 < 0) {     // If clicked on first column, lower bound of the column has been reached.
                startColumn = colToFill;
            }
            else {
                startColumn = colToFill - 1;
            }
            if (colToFill + 1 >= COLS) {  // If clicked on last column, upper bound of the column has been reached.
                endColumn = colToFill;
            }
            else{
                endColumn = colToFill + 1;
            }

            // fill the adjacent positions
            // if they're already filled with opponent piece
            // TODO: verify if the default character for unfilled tile is '\0'
            for (int x = startRow; x <= endRow; x++) {
                if (gameMapCopy[x][colToFill] == opponentPiece) {
                    gameMapCopy[x][colToFill] = pieceToFill;
                }
            }

            for (int y = startColumn; y <= endColumn; y++) {
                if (gameMapCopy[rowToFill][y] == opponentPiece) {
                    gameMapCopy[rowToFill][y] = pieceToFill;
                }
            }

//            System.out.println("i = " + i);
//            for (int k = 0; k < ROWS; k++) {
//                for (int j = 0; j < COLS; j++) {
//                    System.out.print(gameMapCopy[k][j] + " ");
//                }
//                System.out.println();
//            }
        }

        // count player and opponent pieces
        int playerCount = 0;
        int opponentCount = 0;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (gameMapCopy[i][j] == playerPiece) playerCount++;
                else if (gameMapCopy[i][j] == opponentPiece) opponentCount++;
            }
        }

        return playerCount - opponentCount;
    }

    public static void main(String[] args) {
        ArrayList<Integer> emptyTiles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            emptyTiles.add(i);
        }

        Chromosome test = new Chromosome(emptyTiles, 10);
        char[][] map = new char[4][4];
//        map[0][0] = 'X';
        System.out.println(test.getSequence());
        int finalStateValue = test.calculateFinalStateValue(map, 'O', 'X');
        System.out.println(finalStateValue);


//        Chromosome test1 = new Chromosome(emptyTiles, 6);

//        ArrayList<Integer> seq1 = new ArrayList<>();
//        seq1.add(3);
//        seq1.add(8);
//        seq1.add(2);
//        seq1.add(4);
//        seq1.add(6);
//        seq1.add(1);
//        ArrayList<Integer> seq2 = new ArrayList<>();
//        seq2.add(2);
//        seq2.add(3);
//        seq2.add(4);
//        seq2.add(5);
//        seq2.add(6);
//        seq2.add(7);
//
//        test.setSequence(seq1);
//        test1.setSequence(seq2);

//        System.out.println("Test and test1: ");
//        System.out.println(test.getSequence());
//        System.out.println(test1.getSequence());
//
//        ArrayList<Chromosome> children = test.crossover(test1);
//        System.out.println("Children: ");
//        children.forEach(chromosome -> System.out.println(chromosome.getSequence()));
    }
}
