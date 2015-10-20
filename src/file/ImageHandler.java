package file;

import java.util.ArrayList;

/**
 * Created by dv13thg on 10/13/15.
 *
 * rotates the image so it faces upwards.
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
        int divMax =4;
        int matrixDivided = imgMatrix.length /divMax;

        for(int divided = 0; divided < divMax; divided++){
            ArrayList<Integer> tempArr = new ArrayList<>();

            /** X low,high */
            int xHigh = matrixDivided * ((divided%2)+1);
            int xLow =  matrixDivided * (divided%2);
            /** Y low,high */
            int yHigh = (int) (matrixDivided * (Math.floor(divided / 2) + 1) * 2);
            int yLow = (int) (Math.floor(divided/2) * matrixDivided * 2);

            for(int x = xLow; x < xHigh; x++)
                for(int y = yLow; y < yHigh; y++)
                    tempArr.add(imgMatrix[x][y]);

            value[divided] = matrixSum(tempArr);
        }
        /**rotate the image accordingly */
        RotateImage(imgMatrix,value);
    }


    /** RotateImage
     * Rotates the image according to what quadrant of the 
     * image that has the highest value.
     *
     *
     *
     */
    private void RotateImage(int[][] imgMatrix, int value[]) {
        int matrixValue = imgMatrix.length;
        int[][] rotateImg = cloneArr(imgMatrix);
        switch (getIndexGreatestValue(value)) {
            case 0:
                /** Should not rotate */
                break;
            case 1:
                /**  Should be rotated 240 degrees */
                for (int i = 0; i < 3; i++)
                    for (int x = 0; x < imgMatrix.length; x++)
                        for (int y = 0; y < imgMatrix.length; y++)
                            imgMatrix[(matrixValue - y - 1)][x] = rotateImg[x][y];
                break;
            case 2:
                /**  Should be rotated 90 degrees */
                for (int x = 0; x < imgMatrix.length; x++)
                    for (int y = 0; y < imgMatrix.length; y++)
                        imgMatrix[(matrixValue - y - 1)][x] = rotateImg[x][y];

                rotateImg  = cloneArr(imgMatrix);

                /** reverse each row */
                for(int x = 0; x < matrixValue; x++)
                    for(int y = 0; y < matrixValue; y++)
                        imgMatrix[matrixValue - x - 1][y] = rotateImg[x][y];
                break;
            case 3:
                /**  Should be rotated 180 degrees */
                for(int i = 0; i < 2; i++)
                    for (int x = 0; x < imgMatrix.length; x++)
                        for (int y = 0; y < imgMatrix.length; y++)
                            imgMatrix[(matrixValue - y - 1)][x] = rotateImg[x][y];

                rotateImg  = cloneArr(imgMatrix);
                /** reverse each row */
                for(int x = 0; x < matrixValue; x++)
                    for(int y = 0; y < matrixValue; y++)
                        imgMatrix[matrixValue - x - 1][y] = rotateImg[x][y];
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

    private int [][] cloneArr(int [][] arr){
        int[][] clone = new int[arr.length][arr.length];
        for(int i = 0; i < arr.length;i++)
            for(int k = 0; k < arr.length;k++)
                clone[i][k] = arr[i][k];
        return clone;
    }
}