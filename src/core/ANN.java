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

    public static final double LEARNING_RATE = 1.2;
    public static final int IMG_SIZE = 20;

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
     *
     * @param noOfLoops The number of loops it will train.
     */
    public void start(int noOfLoops) {
        while (noOfLoops >= 0) {
            for (FileImage image : imgList) {
                double error;
                    double[][] imageData = image.getImgMatrix();

                    error = facitFiles.get(image.getName()) - activation(image);

                    // iterate through every weight/pixel
                    for (int j = 0; j < weights.length; j++) {
                        for (int k = 0; k < weights[0].length; k++) {
                            double delta = LEARNING_RATE * error * imageData[j][k];
                            weights[j][k] += delta;
                            // System.out.println("Delta = "+LEARNING_RATE + " * "+error+" * "+(double)imageData[j][k]);
                        }
                    }
                    //System.out.println("show error : " + error  + " FACEIT " + facitFiles.get(image.getName()));
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
        // i : the image that we want to calculate the activation function for
        // (ai)
        double weightSum = 0;

        double[][] imageData = image.getImgMatrix();

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
            System.out.println("Image not recognized, returning 0");
            return 0;
        }
    }

    /**
     * Tests the performance of the neural network.
     * @return The percentage of correct answers as a double.
     */
    public double testPerformance(ArrayList<FileImage> images, HashMap<String, Integer> testDataFacit) {
        double correctAnswers = 0;
        // iterate through all images and count the correct answers
        for (FileImage image : images) {
            if (activation(image) == testDataFacit.get(image.getName())) {
                correctAnswers++;
            }
        }
        return 100.0 * (correctAnswers / testDataFacit.size());
    }

    /**
     * Runs a clasification test on a set of images.
     * @param images An array of images to perform the test on.
     */
    public void classificationTest(ArrayList<FileImage> images) {
        System.out.println("# Output: ");
        for (FileImage image : images) {
            System.out.format("%s %d\n", image.getName(), activation(image));
        }
    }

}
