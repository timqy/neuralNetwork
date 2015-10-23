package core;

import file.FileImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * The ANN (A Neural Network) represents our neural network, contains methods to train it,
 * verify its performance and test it on new images. An associated test for this class
 * can be found and its called ANNTest.
 *
 * @author dv13lan, dv13thg
 * @version 20 okt - 2015
 */
public class ANN {

    private static final int IMG_SIZE = 20;

    private double[][] weights;
    private ArrayList<FileImage> imgList;
    private HashMap<String, Integer> facitFiles;

    /**
     * Constructs a new Trainer object set with a data set of image files and
     * the correct answers to them.
     *
     * @param imgList A list containing Facefile images.
     * @param facitFiles A list containing the correct answers.
     */
    public ANN(ArrayList<FileImage> imgList, HashMap<String, Integer> facitFiles) {
        this.imgList = imgList;
        this.facitFiles = facitFiles;
        initANN();
    }

    /**
     * Creates and initiates a new ANN. Will allocate memory for the weights and
     * initiate them with random values. Will also shuffle the list of Faceimages.
     */
    private void initANN() {
        Collections.shuffle(imgList, new Random(System.nanoTime()));

        weights = new double[IMG_SIZE][IMG_SIZE];

        for (int x = 0; x < IMG_SIZE; x++) {
            for (int y = 0; y < IMG_SIZE; y++) {
                weights[x][y] = new Random().nextDouble();
            }
        }
    }

    /**
     * Trains the neural network with a set learning rate for a specific number of
     * times.
     * @param noOfLoops The number of loops it will train.
     * @param learningRate The specified learning rate for the network.
     */
    public void train(double learningRate, int noOfLoops) {
        while (noOfLoops >= 0) {
            for (FileImage image : imgList) {
                double error;
                double[][] imageData = image.getImgMatrix();

                error = facitFiles.get(image.getName()) - activation(image);

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

    /**
     * Given an image as parameter to this function, it will retrieve the 2d
     * image array from the FaceImage and sum the weights together times the
     * image data.
     *
     * If the image data at a pixel is 0 the sum of the weights will not increase.
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

        double weightSum = calculateWeights(image.getImgMatrix());

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
     * Calculates the weights
     * @param imageData 2D image array to calculate weights from.
     * @return An double representing the total sum of all the weights.
     */
    private double calculateWeights(double[][] imageData) {
        double weightSum = 0;

        for (int j = 0; j < imageData.length; j++) {
            for (int k = 0; k < imageData[0].length; k++) {
                weightSum += imageData[j][k] * weights[j][k];
            }
        }

        weightSum = weightSum / (imageData.length * imageData[0].length);
        weightSum = (weightSum * 6) - 3;

        // Sigmoid function
        weightSum = 1 / (1 + Math.exp(-weightSum));
        return weightSum;
    }

    /**
     * Tests the performance of the neural network.
     * @return The percentage of correct answers as a double.
     */
    public double testPerformance(int numberOfTests) {
        double correctAnswers = 0;
        Collections.shuffle(imgList,new Random(System.nanoTime()));
        for(int i = 0; i < numberOfTests;i++){
            int imgIndex = new Random().nextInt(imgList.size());

            if (activation(imgList.get(imgIndex)) == facitFiles.get(imgList.get(imgIndex).getName())) {
                correctAnswers++;
            }
        }
        return 100.0 * (correctAnswers / numberOfTests);
    }

    /**
     * Runs a classification test on a set of images.
     * @param images An array of images to perform the test on.
     */
    public void runTest(ArrayList<FileImage> images) {
        System.out.println("# Output: ");
        for (FileImage image : images) {
            System.out.format("%s %d\n", image.getName(), activation(image));
        }
    }

}
