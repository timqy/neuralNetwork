package core;

import file.FileImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 */
public class Trainer {

    private double learningRate = 0.5;

    private int happyThreshold = 16;
    private int sadThreshold = 10;
    private int 

    private ArrayList<FileImage> imgList;
    private HashMap<String, Integer> facitFiles;

    private ArrayList<NeuronNode> nodes;

    /**
     * Constructs a new Trainer object set with a dataset of imagefiles and
     * the correct answers to them.
     * @param imgList
     * @param facitFiles
     */
    public Trainer(ArrayList<FileImage> imgList, HashMap<String, Integer> facitFiles) {
        this.imgList = imgList;
        this.facitFiles = facitFiles;

        initANN(20);
    }

    /**
     * Creates and initiates a new ANN.
     */
    private void initANN(int size) {
        nodes = new ArrayList<>();

        //Fill the node array with nodes.
        for (int i = 0; i < size*size; i++) {
            nodes.add(new NeuronNode());
        }

        //Setup connectivity between all nodes to all nodes
        for(NeuronNode nn : nodes) {
            nn.setConnections(nodes);
        }

    }

    public void start(int noOfLoops) {


        for(int i = 0; i < imgList.size();i++) {
            int[][] trainingData = imgList.get(i).getImgMatrix();
            assignValues(trainingData);

            while (noOfLoops != 0) {

                for (NeuronNode node : nodes) {
                    double weightedSum = 0;
                    for (NeuronNode neuronNode : nodes) {
                        if(neuronNode != node) {
                            weightedSum += neuronNode.getWeightBetweenNodes(node);
                        }

                    }

                    // Calculate output
                    int output = 0;
                    if (threshold <= weightedSum) {
                        output = 1;
                    }
                    // Calculate error
                    int error =

                    // Update weights

                }

                noOfLoops--;
            } //end while
        }//end for

        // Start training loop
        while (noOfLoops != 0) {

            int errorCount = 0;
            // Loop over training data
            for (int i = 0; i < imgList.size(); i++) {



                for (NeuronNode node : nodes) {
                    node.updateWeights();
                }

                int weightSum = 0;
                // Calculate weighted input
                for(NeuronNode node : nodes) {
                    NeuronNode connectedNodes += node.getTotalWeight;
                }

                // Calculate
                if(weightSum > threshold)
                    output = 1;
                else
                    output = 0;


                // Calculate error


                // Update weights

            }

            // If there are no errors, stop
            if (errorCount == 0) {
                noOfLoops = 0;
            }
            noOfLoops--;
        }
    }

    /**
     *
     * @param trainingData
     */
    private void assignValues(int[][] trainingData) {
        int counter = 0;

        for (int i = 0; i < trainingData.length; i++) {
            for (int j = 0; j < trainingData.length; j++) {
                int pixel = trainingData[i][j];
                nodes.get(counter).setValue(pixel);
                counter++;
            }
        }

    }

}
