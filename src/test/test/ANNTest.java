package test;

import core.ANN;
import file.FileImage;
import file.ImageParser;
import main.CLI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Basic test class for the neural network.
 * @author dv13lan
 * @version 20 okt - 2015
 */
public class ANNTest {

    //Training loops
    public static final int NO_OF_LOOPS = 1;
    private final double LEARNING_RATE = 0.5;
    public static final double PASS_PERCENTAGE = 0.5;

    private ANN neuralNetwork;
    private ArrayList<FileImage> images;

    /**
     * Setups the tests. It will read the default training file and
     * the default facit file from the CLI constants.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        ImageParser parser = ImageParser.getInstance();
        HashMap<String, Integer> facit = parser.parseFacit(CLI.RESOURCES_TRAINING_FACIT_TXT);

        images = parser.parseImage(CLI.RESOURCES_TRAINING_TXT);
        ArrayList<FileImage> clone = new ArrayList<>();
        Collections.shuffle(images);

        //Pre process the training data
        for(FileImage img : images)
            img.preProcessImage();

        for(int i = 0; i < 100;i++)
            clone.add(images.get(i));

        neuralNetwork = new ANN(clone, facit);
    }

    /**
     * Runs the performance test, the test will accept all and inclusive
     * the PASS_PERCENTAGE constant.
     *
     * @throws Exception
     */
    @Test
    public void testTestPerformance() throws Exception {
        neuralNetwork.start(LEARNING_RATE,NO_OF_LOOPS);

        double result = neuralNetwork.testPerformance(100);

        assertTrue(result >= PASS_PERCENTAGE);
    }

    /**
     * Runs the test for classification for the images.
     * @throws Exception
     */
    @Test
    public void testClassificationTest() throws Exception {
        neuralNetwork.start(LEARNING_RATE,NO_OF_LOOPS);
        neuralNetwork.classificationTest(images);
    }


    /**
     * Automatic training value evaluation.
     *
     */
    @Test
    public void testTrainingValue(){
        for(double learningRate = 0.1; learningRate < 2; learningRate += 0.2){
            System.out.println("learnin RAte : " +learningRate);
            for(int loops = 1; loops < 101; loops+=1){
                neuralNetwork.start(learningRate,loops);

                //System.out.printf(" learningRate : %.1f | loops : %2d | result : %.0f\n",learningRate,loops,neuralNetwork.testPerformance(10000));
                System.out.printf("%d %.1f\n",loops,neuralNetwork.testPerformance(100));
                try {
                    setUp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }




    }

}