package core;

import file.FileImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * The ANN (A Neural Network) represents our neuralnetwork, contains methods to train it,
 * verify its performance and test it on new images. An associated test for this class
 * can be found and its called ANNTest.
 *
 * @author dv13lan, dv13thg
 * @version 20 okt - 2015
 */
public class ANN {

    private double[][] weights;
    private double learningRate = 1;
    private double threshold = 0;
    private ArrayList<FileImage> imgList;
    private HashMap<String, Integer> facitFiles;

    /**
     * Constructs a new Trainer object set with a dataset of imagefiles and
     * the correct answers to them.
     *
     * @param imgList A list containing Facefile images.
     * @param facitFiles A list containing the correct answers.
     */
    public ANN(ArrayList<FileImage> imgList, HashMap<String, Integer> facitFiles) {
        this.imgList = imgList;
        this.facitFiles = facitFiles;
        initANN(20);
    }

    /**
     * Creates and initiates a new ANN. Will allocate memory for the weights and
     * initiate them with random values. Will also shuffle the list of Faceimages.
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
     * Given an image as parameter to this function, it will retrieve the 2d
     * image array from the FaceImage and sum the weights together times the
     * image data.
     *
     * If the imagedata at a pixel is 0 the wieghtsum will not increase.
     *
     * After the sums has been added together we will normalize the sum and
     * then run the Sigmoid function on it.
     *
     * Finally then we will return the correct integer representation of
     * SAD, HAPPY etc depending on the output from Sigmoid.
     * @param image An image to analyse.
     * @return the value
     */
    private int activation(FileImage image) {
        // i : the image that we want to calculate the activation function for
        // (ai)
        double weightSum = 0;

        int[][] imageData = image.getImgMatrix();

        // Calculate the activation function
        // Sum of each pixel times the weight in an image affects the result
        // iterate through all rows and columns
        for (int j = 0; j < imageData.length; j++) {
            for (int k = 0; k < imageData[0].length; k++) {
                weightSum += imageData[j][k] * weights[j][k];
            }
        }

        // Normalize x
        weightSum = weightSum / (imageData.length * imageData[0].length);
        weightSum = (weightSum * 6) - 3;

        // Sigmoid function
        weightSum = 1 / (1 + Math.exp(-weightSum));

        if (weightSum < .25) {
            return 1;
        } else if (weightSum < .5) {
            return 2;
        } else if (weightSum < .75) {
            return 3;
        } else if (weightSum <= 1.0) {
            return 4;
        } else {
            return 0;
        }
    }

    /**
     * Tests the performance of the neural network.
     * @return The percentage of correct answers as a double.
     */
    public double testPerformance(ArrayList<FileImage> images) {
        double correctAnswers = 0;
        // iterate through all images and count the correct answers
        for (FileImage image : images) {
            if (activation(image) == facitFiles.get(image.getName())) {
                correctAnswers++;
            }
        }
        return 100.0 * (correctAnswers / facitFiles.size());
    }

    /**
     * Runs a clasification test on a set of images.
     * @param images An array of images to perform the test on.
     */
    public void classificationTest(ArrayList<FileImage> images) {
        System.out.println("# - Happy, Sad, Mischievous or Mad - #");
        System.out.println("# Output: ");
        for (FileImage image : images) {
            System.out.format("%s %d\n", image.getName(), activation(image));
        }
    }

}
