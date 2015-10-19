package file;

import core.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by dv13thg on 10/13/15.
 */
public class ImageHandler {

    /** MatrixSum
     *  sums the value's in the matrix
     *
     * @param imgMatrix the matrix to be analyzed
     * @return the value of the matrix
     */
    private int matrixSum(ArrayList<Integer> imgMatrix){
        int sum = 0;
        for(int value : imgMatrix) {

            if (value> (32 / 2))
                sum += value;
        }
        return sum;
    }

    /**
     * analyzes the image by converting the image into 
     * four smaller ones, sums each image, calls rotate.
     */
    public void RotateImageAnalyzer(int[][] imgMatrix) {
        int value[] = new int[4];
        int matrixValue = imgMatrix.length;
        
        for(int divided = 0; divided < 4; divided++; 
            ArrayList<Integer> tempArr = new ArrayList<>();

            /** X low,high */
            int xHigh = matrixValue * ((divided%2)i+1);
            int xLow = ((divided%2) * matrixValue);
            /** Y low,high */
            int yHigh = (int) (matrixValue * (Math.floor(divided/2)+1));
            int yLow = (int) (Math.floor(divided/2) * matrixValue);

            for(int x = xLow; x < xHigh; x++)
                for(int y = yLow; y < yHigh; y++)
                    tempArr.add(imgMatrix[x][y]);
            
            value[divided] = matrixSum(tempArr);
        }
        /**rotate the image accordingly */
        imgMatrix = RotateImage(imgMatrix,value);
    }


    /** RotateImage
     * Rotates the image according to what quadrant of the 
     * image that has the highest value.
     *
     *
     *
     */
    private int[][] RotateImage(int[][] imgMatrix, int value[]) {
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
        return rotateImg;
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
