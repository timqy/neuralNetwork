package core;

import file.FaceFile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dv13lan on 10/13/15.
 */
public class NeuronBoss {

    private Neuron[] neurons;

    private double learningRate = 0.5;
    private double threshold = 0.6;
    private double[] weights = {1,2,3,4};

    /**
     * Constructs a new NeuronBoss
     */
    public NeuronBoss() {
        this.neurons = new Neuron[4];
    }


    /**
     * Trains the neurons.
     */
    public void trainNeurouns(ArrayList<FaceFile> faceFiles, HashMap<String, Integer> trainingFacit) {
        double weightSum = 0;

    }


}
