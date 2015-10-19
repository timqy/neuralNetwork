package core;

import java.util.ArrayList;

/**
 *
 */
public class NeuronBoss {

    private ArrayList<Node> nodes;

    private double learningRate = 0.5;
    private double threshold = 0.6;
    private double[] weights = {1,2,3,4};

    /**
     * Constructs a new NeuronBoss
     */
    public NeuronBoss() {
        nodes = new ArrayList<>();
    }

    public void registerNode() {
        for(Node n : nodes) {
           n.setConnection(nodes);
        }
    }

}
