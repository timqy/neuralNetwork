package file;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dv13thg on 10/7/15.
 */
public class ImageParser {

    private static ImageParser instance = new ImageParser();

    private ImageParser() { }

    public ArrayList<FaceFile> parseImage(String filePath) throws IOException, NumberFormatException{
        ArrayList<FaceFile> imgArr = new ArrayList<FaceFile>();
        FaceFile faceFile = new FaceFile();
        int lineNumber = 0;

        BufferedReader bufferedreader = new BufferedReader(new FileReader(filePath));
        String line;

        while((line = bufferedreader.readLine()) != null) {
            if (line.startsWith("#") || line.trim().length() == 0) {
                if(faceFile.getName() != null && faceFile.getNodeArr().size() == (20*20)){
                    imgArr.add(faceFile);
                    faceFile = new FaceFile();
                }
            } else {
                if (line.matches("^[0-9 ]+$")) {
                    String[] ArrNumbers = line.split(" ");
                    for (int i = 0; i < ArrNumbers.length; i++) {
                        faceFile.setImgMatrix(lineNumber, i, Integer.parseInt(ArrNumbers[i]));
                    }
                    lineNumber++;
                } else {
                    faceFile.setName(line);
                    lineNumber = 0;
                }

            }
        }

        return imgArr;
    }


    /**
     * Parses a facit file and writes the imagename as key and integer value as
     * integer value to the hashmap.
     * @param filepath filepath to the facit file.
     * @return An hashmap.
     */
    public HashMap<String, Integer> parseFacit(String filepath) throws IOException {
        HashMap<String, Integer> facitMap = new HashMap<String, Integer>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        String line;

        while ((line = bufferedReader.readLine()) != null) {
            if(!line.startsWith("#")) {
                String[] tokens = line.split(" ");

                if(tokens.length == 2) {
                    facitMap.put(tokens[0], Integer.parseInt(tokens[1]));
                    System.out.println("Inserted: Key: "+tokens[0] + " value: "+tokens[1]);
                }

            }
        }
        return facitMap;
    }

    public static ImageParser getInstance() {
        return instance;
    }

}
