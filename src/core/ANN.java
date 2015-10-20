package core;

import file.FileImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 *
 */
public class ANN {

    private double[][] weights;

    private double learningRate = 1;
    private double threshold = 0;

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
        Collections.shuffle(imgList, new Random(System.nanoTime()));

        weights = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                weights[i][j] = new Random().nextDouble();
            }
        }
    }

    /**
     *
     * @param noOfLoops The number of loops it will train.
     */
    public void start(int noOfLoops) {
        while (noOfLoops >= 0) {
            for (FileImage image : imgList) {
                System.out.println("testing image : " + image.getName());
                double error;

                for(int i = 0; i < 20; i++ ) {
                    for (int k = 0; k < 20; k++) {
                        if (image.getImgMatrix()[i][k] > 9){
                            image.getImgMatrix()[i][k] = 1;
                        } else
                        {
                            image.getImgMatrix()[i][k] = 0;
                        }
                    }
                }

                do {
                    int[][] imageData = image.getImgMatrix();
                    error = facitFiles.get(image.getName()) - activation(image);

                    // iterate through every weight/pixel
                    for (int j = 0; j < weights.length; j++) {
                        for (int k = 0; k < weights[0].length; k++) {
                            double delta = learningRate * error * imageData[j][k];
                            weights[j][k] += delta;
                        }
                    }
                    System.out.println("show error : " + error  + " FACEIT " + facitFiles.get(image.getName()));
                } while(Math.abs(error) == threshold);
            }
            noOfLoops--;
        }
    }

    /**
     *
     * @param image
     * @return the value
     */
    private int activation(FileImage image) {
        // i : the image that we want to calculate the activation function for
        // (ai)
        double x = 0;

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

    /**
     *
     * @return
     */
    public double testPerformance() {
        double correctAnswers = 0;
        // iterate through all images and count the correct answers
        for (FileImage image : imgList) {
            if (activation(image) == facitFiles.get(image.getName())) {
                correctAnswers++;
            }
        }

        System.out.println("Correct %: "+ (100.0 * (correctAnswers / facitFiles.size())));
        return correctAnswers / facitFiles.size();
    }

    /**
     *
     * @param images
     */
    public void classificationTest(ArrayList<FileImage> images) {
        System.out.println("# - Happy, Sad, Mischievous or Mad - #");
        System.out.println("# Output: ");
        for (FileImage image : imgList) {
            System.out.format("%s %d\n", image.getName(), activation(image));
        }
    }

}
