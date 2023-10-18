import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Population {
    private ArrayList<Chromosome> individuals;
    private ArrayList<Integer> emptyTiles;
    private PrefixTree reservationTree;
    private final int maxTurns;
    private char[][] gameMap;
    private final char playerPiece;
    private final char opponentPiece;
    private static final int INITIAL_POPULATION_SIZE = 100;
    private static final int MAX_ITERATIONS = 5;

    public Population(int maxTurns, char playerPiece, char opponentPiece) {
        this.maxTurns = maxTurns;
        this.playerPiece = playerPiece;
        this.opponentPiece = opponentPiece;
        this.individuals = new ArrayList<>();
    }

    public void setGameMap(char[][] gameMap) {
        this.gameMap = gameMap;
    }

    public void setEmptyTiles(ArrayList<Integer> emptyTiles) {
        this.emptyTiles = emptyTiles;
    }

    public int naturalSelection() {
        this.generateInitialPopulations();
        this.reservationTree = new PrefixTree();
        // Selection and reproduction process is done multiple times
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            this.individuals.forEach(individual -> {
                reservationTree.insertSequence(individual, this.gameMap, this.playerPiece, this.opponentPiece);
            });
            this.reservationTree.calculateAllFitness();
            this.reproduce();
            if (i != MAX_ITERATIONS - 1) {
                this.reservationTree = new PrefixTree();
            }
        }
//        System.out.println("remaining tree: ");
//        reservationTree.printTree();
        int maxStateValue = reservationTree.getRoot().stateValue;

//        this.reservationTree.getRoot().children.forEach(child -> System.out.println("content: " + child.content + " value: " + child.stateValue));
//        System.out.println("Maximum state value: " + maxStateValue);
        Optional<Node> firstChildWithMaxStateValue = this.reservationTree.getRoot().children.stream()
                .filter(node -> node.stateValue != null && node.stateValue == maxStateValue)
                .findFirst();
        System.out.println("result of natural selection: " + firstChildWithMaxStateValue.get().content);
        return firstChildWithMaxStateValue.get().content;
//        if (firstChildWithMaxStateValue.isPresent()) {
//            System.out.println("Action to take: ");
//            System.out.println(firstChildWithMaxStateValue.get().content);
//            return firstChildWithMaxStateValue.get().content;
//        }
    }

    public void generateInitialPopulations() {
        for (int i = 0; i < INITIAL_POPULATION_SIZE; i++) {
            this.individuals.add(new Chromosome(this.emptyTiles, this.maxTurns));
        }
    }

    public void reproduce() {
        // Select randomly 66% of the population to reproduce,
        // with the probability proportional to the fitness value
        ArrayList<Double> roulette = this.createRoulette();
        ArrayList<Chromosome> reproducingIndividuals = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i <  INITIAL_POPULATION_SIZE * 2 / 3; i++) {
            double randomNumber = random.nextDouble();

            for (int j = 0; j < roulette.size(); j++) {
                if (randomNumber < roulette.get(j)) {
                    reproducingIndividuals.add(this.individuals.get(j));
                    break;
                }
            }
        }
//        System.out.println("Reproducing individuals");
//        reproducingIndividuals.forEach(e -> System.out.println(e.getSequence()));

        // Perform crossover among the selected individuals, save the offspring
        ArrayList<Chromosome> offspring = new ArrayList<>();
        for (int i = 0; i < reproducingIndividuals.size() / 2; i++) {
            Chromosome firstParent = reproducingIndividuals.get(i);
            Chromosome secondParent = reproducingIndividuals.get(reproducingIndividuals.size() - i - 1);
            ArrayList<Chromosome> resultingIndividuals = firstParent.crossover(secondParent);
            offspring.addAll(resultingIndividuals);
            resultingIndividuals.clear();
        }

        // Only add the reproducing individuals and the offspring to the population
        this.individuals = reproducingIndividuals;
        this.individuals.addAll(offspring);
    }

    private ArrayList<Double> createRoulette() {
        double fitnessValueSum = individuals.stream()
                .mapToDouble(Chromosome::getFitness)
                .sum();

        ArrayList<Double> roulette = new ArrayList<>(
                individuals.stream().map(chromosome -> chromosome.getFitness() / fitnessValueSum).toList()
        );

        for (int i = 1; i < roulette.size() - 1; i++) {
            roulette.set(i, roulette.get(i) + roulette.get(i - 1));
        }

        roulette.set(roulette.size() - 1, 1.0); // Roundup for last element
        return roulette;
    }

    private void print() {
        for (Chromosome individual : this.individuals) {
            System.out.println(individual.getSequence());
        }
    }

    public static void main(String[] args) {
        char[][] gameMap = new char[4][4];
        Population population = new Population(16, 'X', 'O');
        population.setGameMap(gameMap);
        ArrayList<Integer> emptyTiles = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            emptyTiles.add(i);
        }
        population.setEmptyTiles(emptyTiles);
        try {
            population.naturalSelection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
