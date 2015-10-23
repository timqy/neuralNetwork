package test.test;

import file.ImageParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * Tests the FileImage parser
 * @author dv13lan
 */
public class ImageParserTest {


    private ImageParser parser;

    @Before
    public void setUp() throws Exception {
        parser = ImageParser.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        parser = null;
    }

    @Test
    public void testParseFacit() throws Exception {
        HashMap<String, Integer> map = parser.parseFacit("resources/training-facit.txt");
        assertEquals(map.size(), 300);
    }
}