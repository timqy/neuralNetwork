package core;

import file.FileImage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by loyd on 10/19/15.
 */
public class Trainer {

    private double learningRate = 0.5;
    private int threshold = 16;

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
        for (FileImage img : imgList) {
            runTraining(img, facitFiles.get(img.getName()), noOfLoops);
        }
    }

    private void runTraining(FileImage img, int result, int correctResult) {

    }
}
