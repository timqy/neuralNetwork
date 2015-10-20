package core;

import file.FileImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 */
public class ANN {

    private double[][] weights;

    private double learningRate = 0.5;
    private double threshold = 0.08;

    private ArrayList<FileImage> imgList;
    private HashMap<String, Integer> facitFiles;

    private ArrayList<NeuronNode> nodes;

    /**
     * Constructs a new Trainer object set with a dataset of imagefiles and
     * the correct answers to them.
     * @param imgList
     * @param facitFiles
     */
    public ANN(ArrayList<FileImage> imgList, HashMap<String, Integer> facitFiles) {
        this.imgList = imgList;
        this.facitFiles = facitFiles;
        initANN(20);
    }

    /**
     * Creates and initiates a new ANN.
     */
    private void initANN(int size) {
        weights = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                weights[i][j] = 0;
            }
        }
    }

    public void start(int noOfLoops) {
        for (int i = 0; i < imgList.size(); i++) {
            FileImage image = imgList.get(i);
            int[][] imageData = image.getImgMatrix();
            double error = facitFiles.get(i) - act(imgList, i);

            // iterate through every weight/pixel
            for (int j = 0; j < weights.length; j++) {
                for (int k = 0; k < weights[0].length; k++) {
                    double delta = learningRate * error * imageData[j][k];
                    weights[j][k] += delta;
                }
            }
        }
    }

    private int act(ArrayList<FileImage> imgList, int i) {
        return 0;
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
