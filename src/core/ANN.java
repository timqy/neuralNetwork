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
    private double threshold = 0.8;

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
        while (noOfLoops >= 0) {
            for (int i = 0; i < imgList.size(); i++) {
                FileImage image = imgList.get(i);
                int[][] imageData = image.getImgMatrix();
                double error = facitFiles.get(imgList.get(i).getName()) - act(imgList, i);

                // iterate through every weight/pixel
                for (int j = 0; j < weights.length; j++) {
                    for (int k = 0; k < weights[0].length; k++) {
                        double delta = learningRate * error * imageData[j][k];
                        weights[j][k] += delta;
                    }
                }
            }
            noOfLoops--;
        }
    }

    private int act(ArrayList<FileImage> imgList, int i) {
        // i : the image that we want to calculate the activation function for
        // (ai)
        double x = 0;

        FileImage image = imgList.get(i);
        int[][] imageData = image.getImgMatrix();

        // Calculate the activation function
        // Sum of each pixel times the weight in an image affects the result
        // iterate through all rows and columns
        for (int j = 0; j < imageData.length; j++) {
            for (int k = 0; k < imageData[0].length; k++) {
                x += imageData[j][k] * weights[j][k];
            }
        }

        // Normalize x
        x = x / (imageData.length * imageData[0].length);
        x = (x * 6) - 3;

        // Sigmoid function
        x = 1 / (1 + Math.exp(-x));

        if (x < .25) {
            return 1;
        } else if (x < .5) {
            return 2;
        } else if (x < .75) {
            return 3;
        } else if (x <= 1.0) {
            return 4;
        } else {
            return 0;
        }
    }

    public double testPerformance() {
        double correctAnswers = 0;
        // iterate through all images and count the correct answers
        for (int i = 0; i < imgList.size(); i++) {
            if (act(imgList, i) == facitFiles.get(imgList.get(i).getName())) {
                correctAnswers++;
            }
        }

        System.out.println("Correct %: "+ (correctAnswers / facitFiles.size()));
        return correctAnswers / facitFiles.size();
    }

    public void classificationTest(ArrayList<FileImage> images) {
        System.out.println("# - Happy, Sad, Mischievous or Mad - #");
        System.out.println("# Output: ");
        for (int i = 0; i < images.size(); i++) {
            System.out.format("%s %d\n", images.get(i).getName(),
                    act(images, i));
        }
    }

}
