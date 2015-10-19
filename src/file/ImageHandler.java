package file;

import core.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by dv13thg on 10/13/15.
 */
public class ImageHandler {

    /** MatrixValue
     * @param imgMatrix the matrix to be analyzed
     * @return the value of the matrix
     */
    private int matrixValue(ArrayList<Integer> imgMatrix){
        int sum = 0;
        for(int value : imgMatrix) {

            if (value> (32 / 2))
                sum += value;
        }
        return sum;
    }

    public void RotateImageAnalyzer(int[][] imgMatrix) {
        int divided = 0;
        int value[] = new int[4];
        int matrixValue = imgMatrix.length;
        while (divided < 4) {
            ArrayList<Integer> tempArr = new ArrayList<>();

            int xHigh = matrixValue * ((divided%2));
            int xLow = ((divided%2) * matrixValue);
            int yHigh = (int) (matrixValue * (Math.floor(divided/2)));
            int yLow = (int) (Math.floor(divided/2) * matrixValue);

            for(int x = xLow; x < xHigh; x++)
                for(int y = yLow; y < yHigh; y++)
                    tempArr.add(imgMatrix[x][y]);
            value[divided] = matrixValue(tempArr);
            divided++;
        }
        RotateImage(imgMatrix,value);
    }

    private void RotateImage(int[][] imgMatrix, int value[]) {
        int matrixValue = imgMatrix.length;
        int[][] rotateImg = new int[matrixValue][matrixValue];
        int[][] temp;
        switch (getIndexGreatestValue(value)) {
            case 0:
                /** Should not rotate */
                break;
            case 1:
                /**  Should be rotated 240 degrees */
                for (int i = 0; i < 3; i++)
                    for (int x = 0; x < imgMatrix.length; x++)
                        for (int y = 0; y < imgMatrix.length; y++)
                            rotateImg[(matrixValue - y - 1)][x] = imgMatrix[x][y];
                break;
            case 2:
                /**  Should be rotated 90 degrees */
                for (int x = 0; x < imgMatrix.length; x++)
                    for (int y = 0; y < imgMatrix.length; y++)
                        rotateImg[(matrixValue - y - 1)][x] = imgMatrix[x][y];

                temp  = rotateImg.clone();
                /** reverse each row */
                for(int x = 0; x < matrixValue; x++)
                    for(int y = 0; y < matrixValue; y++)
                        rotateImg[matrixValue - x - 1][y] = temp[x][y];
                break;
            case 3:
                /**  Should be rotated 180 degrees */
                for(int i = 0; i < 2; i++)
                    for (int x = 0; x < imgMatrix.length; x++)
                        for (int y = 0; y < imgMatrix.length; y++)
                            rotateImg[(matrixValue - y - 1)][x] = imgMatrix[x][y];

                temp  = rotateImg.clone();
                /** reverse each row */
                for(int x = 0; x < matrixValue; x++)
                    for(int y = 0; y < matrixValue; y++)
                        rotateImg[matrixValue - x - 1][y] = temp[x][y];
                break;
        }
    }

    private static int getIndexGreatestValue(int value[]){
        int max = 0;
        int index = 0;
        for (int i = 0; i < value.length; i++) {
            if (value[i] > max) {
                max = value[i];
                index = i;
            }
        }
        return index;
    }
}
