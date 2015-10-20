package core;

import file.FileImage;
import file.ImageParser;
import main.CLI;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Basic test class for the neural network.
 * @author dv13lan
 * @version 20 okt - 2015
 */
public class ANNTest {

    //Training loops
    public static final int NO_OF_LOOPS = 100;

    private ANN neuralNetwork;
    private ArrayList<FileImage> images;
    private HashMap<String, Integer> facit;

    @Before
    public void setUp() throws Exception {
        ImageParser parser = ImageParser.getInstance();
        facit = parser.parseFacit(CLI.RESOURCES_TRAINING_FACIT_TXT);
        images = parser.parseImage(CLI.RESOURCES_TRAINING_TXT);

        neuralNetwork = new ANN(images,facit);
    }

    @Test
    public void testStart() throws Exception {
        neuralNetwork.start(NO_OF_LOOPS);
    }

    @Test
    public void testTestPerformance() throws Exception {
        neuralNetwork.start(NO_OF_LOOPS);

        double result = neuralNetwork.testPerformance();

        assertTrue(result > 0.5);
    }

    @Test
    public void testClassificationTest() throws Exception {
        neuralNetwork.start(NO_OF_LOOPS);
        neuralNetwork.classificationTest(images);
    }
}