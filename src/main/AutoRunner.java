package main;

import core.ANN;
import file.FileImage;
import file.ImageParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**Autoruns the program and will print out the results on standard output in
 * the format specified in the assignment.
 * @author dv13lan
 * @version 20 okt - 2015
 */
public class AutoRunner {


    public static final int TRAINING_LOOP = 500;

    private ArrayList<FileImage> testData;
    private HashMap<String, Integer> facitData;
    private ArrayList<FileImage> trainingData;
    private HashMap<String, Integer> testDataFacit;

    /**
     * Constructs a new Autorunner object.
     *
     * @param trainingPath A string representing the file path to the training
     *                     file.
     * @param facitPath A string representing the file path to the facit file.
     * @param testFilePath A string representing the file path to the test
     *                     images that are not included in the training file.
     */
    public AutoRunner(String trainingPath, String facitPath, String testFilePath) {
        try {
            trainingData = ImageParser.getInstance().parseImage(trainingPath);
            facitData = ImageParser.getInstance().parseFacit(facitPath);
            testData = ImageParser.getInstance().parseImage(testFilePath);
            testDataFacit = ImageParser.getInstance().parseFacit("resources/test-data-facit.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts running the automatic run of the NeuralNetwork.
     */
    public void run() {
        prepareData();
        ANN neuralNetwork = new ANN(trainingData,facitData);

        //Train train train
        neuralNetwork.start(TRAINING_LOOP);

        //Pray to god it works!
        //neuralNetwork.classificationTest(testData);

        double correct = neuralNetwork.testPerformance(testData,testDataFacit);

        System.out.println("Correct: " + correct + "%");


    }

    /**
     * Will pre-process all images before use in the neural network.
     */
    private void prepareData() {
        //Pre process the training data
        for(FileImage img : trainingData)
            img.preProcessImage();

        //Pre process the test data.
        for(FileImage img : testData)
            img.preProcessImage();
    }
}
