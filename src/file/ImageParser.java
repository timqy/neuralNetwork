package file;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author dv13lan, dv13thg
 * @version 2015-10-13
 *
 * This class handles the parsing of the FileImage and facit files.
 * It is implemented using the singleton design pattern since we only need
 * one instance of this class.
 */
public class ImageParser {

    // Access outside this object by this field.
    private static ImageParser instance = new ImageParser();

    /**
     * private empty constructor as standard for singleton classes in java.
     */
    private ImageParser() { }

    /**
     * Parses the imagefiles, will ignore # signs as they are seens as comments.
     * @param filePath Path to the file containing the faceit images.
     * @return An arraylist containing faceit files.
     * @throws IOException
     * @throws NumberFormatException
     */
    public ArrayList<FileImage> parseImage(String filePath) throws IOException, NumberFormatException {
        ArrayList<FileImage> imgArr = new ArrayList<>();
        FileImage FileImage = new FileImage();
        int lineNumber = 0;

        BufferedReader bufferedreader = new BufferedReader(new FileReader(filePath));
        String line;

        while((line = bufferedreader.readLine()) != null) {
            if (line.startsWith("#") || line.trim().length() == 0) {
                if(FileImage.getName() != null && FileImage.getImgMatrix().length == 20){
                    imgArr.add(FileImage);
                    FileImage = new FileImage();
                }
            } else {
                if (line.matches("^[0-9 ]+$")) {
                    String[] ArrNumbers = line.split(" ");
                    for (int i = 0; i < ArrNumbers.length; i++) {
                        FileImage.setImgMatrix(lineNumber, i, Integer.parseInt(ArrNumbers[i]));
                    }
                    lineNumber++;
                } else {
                    FileImage.setName(line);
                    lineNumber = 0;
                }

            }
        }

        ImageHandler ih = new ImageHandler();
        for(FileImage image : imgArr)
            ih.RotateImageAnalyzer(image.getImgMatrix());

        return imgArr;
    }


    /**
     * Parses a facit file and writes the imagename as key and integer value as
     * integer value to the hashmap.
     * @param filepath file path to the facit file.
     * @return An hashmap.
     */
    public HashMap<String, Integer> parseFacit(String filepath) throws IOException {
        HashMap<String, Integer> facitMap = new HashMap<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if(!line.startsWith("#")) {
                String[] tokens = line.split(" ");

                if(tokens.length == 2) {
                    facitMap.put(tokens[0], Integer.parseInt(tokens[1]));
                    // System.out.println("Inserted: Key: "+tokens[0] + " value: "+tokens[1]);
                }

            }
        }
        return facitMap;
    }

    /**
     * Used by other classes and objects to get an instance of this parser.
     * @return An imageparser.
     */
    public static ImageParser getInstance() {
        return instance;
    }
}
