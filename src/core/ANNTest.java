package core;

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
    public static final double PASS_PERCENTAGE = 0.5;
    public static final String RESOURCES_TEST_DATA_TXT = "resources/test-data.txt";

    private ANN neuralNetwork;
    private ArrayList<FileImage> images;
    private ArrayList<FileImage> testImages;
    private HashMap<String,Integer> testDataFacit;

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
        testImages = parser.parseImage(RESOURCES_TEST_DATA_TXT);
        testDataFacit = parser.parseFacit("resources/test-data-facit.txt");

        ArrayList<FileImage> clone = new ArrayList<>();
        Collections.shuffle(images);

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
        neuralNetwork.start(NO_OF_LOOPS);

        double result = neuralNetwork.testPerformance(testImages, testDataFacit);

        assertTrue(result >= PASS_PERCENTAGE);
    }

    /**
     * Runs the test for classification for the images.
     * @throws Exception
     */
    @Test
    public void testClassificationTest() throws Exception {
        neuralNetwork.start(NO_OF_LOOPS);
        neuralNetwork.classificationTest(images);
    }
}