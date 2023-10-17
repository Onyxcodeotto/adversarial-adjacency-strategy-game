import java.util.ArrayList;
import java.util.Optional;

public class PrefixTree {
    private static final int ROOT_DUMMY_VALUE = -1;
    private static final int MAX_DEPTH = 10;
    private final Node root;
    public PrefixTree() {
        this.root = new Node(ROOT_DUMMY_VALUE);
    }

    public Node getRoot() {
        return this.root;
    }

    public void insertSequence(Chromosome chromosome, char[][] gameMap, char playerPiece, char opponentPiece) {
        Node current = this.root;
        ArrayList<Integer> sequence = chromosome.getSequence();
        for (int i = 0; i < sequence.size(); i++) {
            Integer element = sequence.get(i);
            Optional<Node> matchingChild = current.children.stream()
                    .filter(e -> e.content == element)
                    .findFirst();

            if (matchingChild.isPresent()) {
                current = matchingChild.get();
            }
            else {
                Node newNode = new Node(element);
                current.children.add(newNode);
                current = newNode;
            }
            if (i == sequence.size() - 1) {
                current.corresopndingChromosome = chromosome;
                current.stateValue = chromosome.calculateFinalStateValue(gameMap, playerPiece, opponentPiece);
            }
        }
    }

    public void printTree() {
        printTree(root, "");
    }

    private void printTree(Node node, String prefix) {
        if (node != null) {
            System.out.println(prefix + node.content + " state value: " + node.stateValue);

            for (Node child : node.children) {
                printTree(child, prefix + "  ");
            }
        }
    }

    public void calculateAllFitness() {
        this.root.minimax(true);
        ArrayList<Integer> sequenceStack = new ArrayList<>();
        for (Node child : this.root.children) {
            child.dfs(sequenceStack);
        }
    }

    public static void main(String[] args) {
//        PrefixTree pt = new PrefixTree();
//        ArrayList<Integer> seq = new ArrayList<>();
//        seq.add(1);
//        seq.add(2);
//        seq.add(3);
//        pt.insertSequence(seq);
//        ArrayList<Integer> seq1 = new ArrayList<>();
//        seq1.add(1);
//        seq1.add(4);
//        seq1.add(5);
//        pt.insertSequence(seq1);
//        ArrayList<Integer> seq2 = new ArrayList<>();
//        seq2.add(7);
//        seq2.add(4);
//        seq2.add(5);
//        pt.insertSequence(seq2);
//        pt.printTree();
    }
}
class Node {
    int content;
    Integer stateValue = null;
    Chromosome corresopndingChromosome = null; // for leaf node only
    ArrayList<Node> children;

    public Node(int content) {
        this.content = content;
        this.children = new ArrayList<>();
    }

    /**
     * Ini buat nyari state value di setiap node
     * @param isMaximizing
     */
    public void minimax(boolean isMaximizing) {
        if (stateValue != null) {
            return;
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Node child : children) {
                child.minimax(false);
                maxEval = Math.max(maxEval, child.stateValue);
            }
            stateValue = maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Node child : children) {
                child.minimax(true);
                minEval = Math.min(minEval, child.stateValue);
            }
            stateValue = minEval;
        }
    }

    /**
     * Ini gunanya buat nyari daun yang paling bisa mempertahankan state value-nya sampe atas
     * Semakin lama bisa mempertahankan state value -> fitness semakin tinggi
     * @param sequenceStack
     */
    public void dfs(ArrayList<Integer> sequenceStack) {
        sequenceStack.add(this.stateValue);
        if (this.children.isEmpty()) {
            int lastStateValue = this.stateValue;
            int fitnessValue = 1;
            for (int i = sequenceStack.size() - 1; i >= 0; i--) {
                if (sequenceStack.get(i) != lastStateValue) {
                    break;
                }
                fitnessValue++;
            }
            this.corresopndingChromosome.setFitness(fitnessValue);
        }
        else {
            this.children.forEach(child -> {
                child.dfs(sequenceStack);
            });
        }
        sequenceStack.remove(sequenceStack.size() - 1);
    }
}
