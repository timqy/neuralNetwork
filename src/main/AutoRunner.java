package main;

import core.ANN;
import file.FileImage;
import file.ImageParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Autoruns the program and will print out the results on standard output in
 * the format specified in the assignment.
 *
 * @author dv13lan
 * @version 20 okt - 2015
 */
public class AutoRunner {

    private final double LEARNING_RATE = 0.5;
    private final int TRAINING_LOOP = 14;

    private ArrayList<FileImage> testData;
    private HashMap<String, Integer> facitData;
    private ArrayList<FileImage> trainingData;

    /**
     * Constructs a new Autorunner object.
     *
     * @param trainingPath A string representing the file path to the training
     *                     file.
     * @param facitPath    A string representing the file path to the facit file.
     * @param testFilePath A string representing the file path to the test
     *                     images that are not included in the training file.
     */
    public AutoRunner(String trainingPath, String facitPath, String testFilePath) {
        try {
            trainingData = ImageParser.getInstance().parseImage(trainingPath);
            facitData = ImageParser.getInstance().parseFacit(facitPath);
            testData = ImageParser.getInstance().parseImage(testFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts running the automatic run of the NeuralNetwork.
     */
    public void run() {
        prepareData();
        ANN neuralNetwork = new ANN(trainingData, facitData);

        //Train train train
        neuralNetwork.train(LEARNING_RATE, TRAINING_LOOP);

        //Pray to god it works!
        neuralNetwork.runTest(testData);


    }

    /**
     * Will pre-process all images before use in the neural network.
     */
    private void prepareData() {
        //Pre process the training data
        for (FileImage img : trainingData)
            img.preProcessImage();

        //Pre process the test data.
        for (FileImage img : testData)
            img.preProcessImage();
    }
}
