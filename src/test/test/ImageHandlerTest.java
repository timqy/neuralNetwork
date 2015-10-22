package test;

import file.FileImage;
import file.ImageHandler;
import file.ImageParser;
import main.CLI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageHandlerTest {

    ImageHandler ih;
    ImageParser ip;
    FileImage fileImage;
    double[][] image;


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
        printImage();

        ih.RotateImageAnalyzer(fileImage);
        image = fileImage.getImgMatrix();
        printImage();
    }

    private void printImage(){
        for(int x = 0; x < image.length;x++) {
            for (int y = 0; y < image[0].length; y++)
                System.out.printf("%3d  ", (int)image[x][y]);
            System.out.println();
        }
        System.out.printf("\n");
    }
}