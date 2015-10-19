package core;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Ludwig Andersson, dv13lan@cs.umu.se
 * @version 19/10 - 2015
 *
 * This class represents a Node in the Neuron network. It contains references to
 * other nodes through an Arraylist and reference to the weighted edges between
 * nodes in an hashmap called edges.
 */
public class NeuronNode {

    private int value;
    private ArrayList<NeuronNode> connections;
    private HashMap<NeuronNode, Double> edges;

    /**
     * Sets the value.
     * @param value
     */
    public void setValue(int value) {
        if(value > 20)
            this.value = 1;
        else
            this.value = 0;
    }

    public int getValue() {
        return value;
    }

    /**
     * Constructs a new NeuronNode objects.
     * This constructor instantiates the lists and maps.
     */
    public NeuronNode() {
        value = 0;
        connections = new ArrayList<>();
        edges = new HashMap<>();
    }

    /**
     * Sets the connections.
     * @param connections An arraylist containing the neuron this neuron will
     *                    have connections to. It will remove it self from the
     *                    list so we dont get a circular behavior.
     */
    public void setConnections(ArrayList<NeuronNode> connections) {
        connections.remove(this);
        this.connections = connections;
        createWeightedEdges();
    }

    /**
     * Sets the edge value between two nodes.
     * @param neuronNode The neuron node which the edge leads to.
     * @param value The value between the edges.
     */
    public void updateWeights(){
        for(NeuronNode neuronNode : connections) {
            if(this.value == 1 && neuronNode.getValue() == 1) {
                edges.put(neuronNode,edges.get(neuronNode)+1);
            } else if (this.value != neuronNode.getValue()){
                edges.put(neuronNode,edges.get(neuronNode)-1);
            }
        }
    }

    /**
     * Creates the weighted edges with the value of 0.
     */
    private void createWeightedEdges() {
        for(NeuronNode neuronNode : connections) {
            edges.put(neuronNode, 0.0);
        }
    }

    public double getWeightBetweenNodes(NeuronNode node) {
        return edges.get(node);
    }
}
