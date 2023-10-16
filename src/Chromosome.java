import java.util.*;

public class Chromosome {
    private ArrayList<Integer> chromosomeSequence;
    private ArrayList<Integer> emptyTiles;

    public Chromosome(ArrayList<Integer> emptyTiles, int maxLength) {
        this.emptyTiles = emptyTiles;
        this.generateRandomSequence();
        this.chromosomeSequence = new ArrayList<>(
                this.emptyTiles.subList(
                        0, Math.min(this.emptyTiles.size(), maxLength)
                )
        );
    }

    public Chromosome(Chromosome chromosome) {
        this.chromosomeSequence = new ArrayList<>();
        for (Integer element : chromosome.getChromosomeSequence()) {
            this.chromosomeSequence.add(element);
        }
        this.emptyTiles = new ArrayList<>();
        for (Integer element : chromosome.getEmptyTiles()) {
            this.emptyTiles.add(element);
        }
    }

    public ArrayList<Integer> getChromosomeSequence() {
        return chromosomeSequence;
    }

    public void setChromosomeSequence(ArrayList<Integer> chromosomeSequence) {
        this.chromosomeSequence = chromosomeSequence;
    }

    public int getChromosomeSequenceAt(int index) {
        return this.chromosomeSequence.get(index);
    }

    public void setChromosomeSequenceAt(int index, int element) {
        this.chromosomeSequence.set(index, element);
    }

    public ArrayList<Integer> getEmptyTiles() {
        return emptyTiles;
    }

    public void setEmptyTiles(ArrayList<Integer> emptyTiles) {
        this.emptyTiles = emptyTiles;
    }
    private void generateRandomSequence() {
        Collections.shuffle(emptyTiles);
    }

    public ArrayList<Chromosome> crossover(Chromosome chromosome) {
        Random random = new Random();
        int crossoverPoint = random.nextInt(this.chromosomeSequence.size());

        Chromosome firstChild = new Chromosome(this);
        Chromosome secondChild = new Chromosome(chromosome);

        for (int i = crossoverPoint; i < this.chromosomeSequence.size(); i++) {
            firstChild.setChromosomeSequenceAt(i, chromosome.getChromosomeSequenceAt(i));
        }

        for (int i = crossoverPoint; i < this.chromosomeSequence.size(); i++) {
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
        Random random = new Random();
        int mutationPoint = random.nextInt(chromosomeSequence.size());
        int randomElementIndex = random.nextInt(emptyTiles.size());
        int randomElement = this.emptyTiles.get(randomElementIndex);
        if (!this.chromosomeSequence.contains(randomElement)) {
            this.chromosomeSequence.set(mutationPoint, randomElement);
        }
    }

    public boolean isSequenceValid() {
        Set<Integer> seen = new HashSet<>();
        for (Integer element : this.chromosomeSequence) {
            if (!seen.add(element)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<Integer> emptyTiles = new ArrayList<>();
        for (int i = 1; i <= 64; i++) {
            emptyTiles.add(i);
        }

        Chromosome test = new Chromosome(emptyTiles, 10);
        Chromosome test1 = new Chromosome(emptyTiles, 10);
        System.out.println("Test and test1: ");
        System.out.println(test.getChromosomeSequence());
        System.out.println(test1.getChromosomeSequence());

        ArrayList<Chromosome> children = test.crossover(test1);
        System.out.println("Children: ");
//        System.out.println(children);
//        System.out.println(children.get(0).getChromosomeSequence());
//        System.out.println(children.get(1).getChromosomeSequence());
    }
}
