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
            if(value >= 25)
                sum += value;
        }
        return sum;
    }

    /**
     * analyzes the image by converting the image into 
     * four smaller ones, sums each image, calls rotate.
     * @param image the image to be rotated
     */
    public void RotateImageAnalyzer(FileImage image) {
        int[][] imgMatrix = image.getImgMatrix();
        int value[] = new int[4];
        int divMax = 4;
        int matrixDivided = (imgMatrix.length / divMax) * 2;

        for (int divided = 0; divided < divMax; divided++) {
            ArrayList<Integer> tempArr = new ArrayList<>();

            /** X low,high */
            int xHigh = matrixDivided * ((divided % 2) + 1);
            int xLow = matrixDivided * (divided % 2);
            /** Y low,high */
            int yHigh = (int) (matrixDivided * (Math.floor(divided / 2) + 1));
            int yLow = (int) (Math.floor(divided / 2) * matrixDivided);

            for (int x = xLow; x < xHigh; x++)
                for (int y = yLow; y < yHigh; y++)
                    tempArr.add(imgMatrix[x][y]);

            value[divided] = matrixSum(tempArr);
        }


        /**rotate the image accordingly */
        image.setCurrentImage(RotateImage(imgMatrix, value));

    }


    /** RotateImage
     * Rotates the image according to what quadrant of the 
     * image that has the highest value.
     *
     *
     *
     */
    private int[][] RotateImage(int[][] imgMatrix, int value[]) {
        int northHalf = value[0]+value[1];
        int westHalf = value[0]+value[2];
        int eastHalf = value[1]+value[3];
        int southHalf = value[2]+value[3];

        switch (getIndexGreatestValue(value)) {
            case 0:
                /** Should not rotate */
                break;
            case 1:
                /** if north half has the most value, it's already upright */
                if(northHalf > eastHalf){
                    System.out.println("top half has greatest values");
                    break;
                }
                /** east side has greatest value */
                /** rotate 240 degrees */
                imgMatrix = RotateMatrix(imgMatrix,3);
                break;
            case 2:
                if(westHalf > southHalf){
                    /** south half is greatest */
                    imgMatrix = RotateMatrix(imgMatrix,1);

                } else {
                    /** South has greatest value */
                    imgMatrix = RotateMatrix(imgMatrix, 2);
                }
                break;
            case 3:
                if(eastHalf > southHalf){
                    /** east half is greatest */
                    imgMatrix = RotateMatrix(imgMatrix,3);
                } else {
                    /** south has greatest value */
                    System.out.println("south has greatest values");
                    imgMatrix = RotateMatrix(imgMatrix,2);
                }
        }
        return imgMatrix;
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

    int[][] RotateMatrix(int[][] matrix, int rotateTimes) {
        int[][] ret = new int[matrix.length][matrix.length];

        for (int rotate = 1; rotate < rotateTimes; rotate++){
            /** 90 degrees */
            for (int i = 0; i < matrix.length; ++i) {
                for (int j = 0; j < matrix.length; ++j) {
                    ret[i][j] = matrix[matrix.length - j - 1][i];
                }
            }
        }

        return ret;
    }
}