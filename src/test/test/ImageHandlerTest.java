package test.test;

import file.FileImage;
import file.ImageHandler;
import file.ImageParser;
import main.CLI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageHandlerTest {

    private ImageHandler ih;
    private ImageParser ip;
    private FileImage fileImage;
    private double[][] image;


    @Before
    public void setUp() throws Exception {
        ip = ImageParser.getInstance();
        ih = new ImageHandler();
        fileImage = ip.parseImage(CLI.RESOURCES_TRAINING_TXT).get(8);
        image = fileImage.getImgMatrix();
    }

    @After
    public void tearDown() throws Exception {
        image = null;
    }

    @Test
    public void testRotateImageAnalyzer() throws Exception {
        ih.RotateImageAnalyzer(fileImage);
        image = fileImage.getImgMatrix();
    }
}