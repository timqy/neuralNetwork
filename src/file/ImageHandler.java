package file;

import java.util.ArrayList;

/**
 * Created by dv13thg on 10/13/15.
 *
 * rotates the image so it faces upwards.
 */
public class ImageHandler {

    /** Northwest,northeast,southwest,southeast*/
    int greatestCorner = 1;
    /** North,east,south,west */
    int greatestHalf = 1;

    /**
     * analyzes the image by converting the image into 
     * four smaller ones, sums each image, calls rotate.
     * @param image the image to be rotated
     */
    public void RotateImageAnalyzer(FileImage image) {
        int mirrorimage = 0, rotations = 4;
        while(greatestCorner != 0 && greatestHalf != 0) {
            if(mirrorimage == rotations) {
                image.setCurrentImage(mirrorMatrix(image.getImgMatrix()));
                rotations--;
                mirrorimage = 0;
            }
            double[][] imgMatrix = image.getImgMatrix();

            int divMax = 4;
            int value[] = new int[divMax];
            int matrixDivided = (imgMatrix.length / (divMax / 2));

            for (int divided = 0; divided < divMax; divided++) {
                ArrayList<Double> tempArr = new ArrayList<>();

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
            mirrorimage++;
        }
    }


    /** MatrixSum
     *  sums the value's in the matrix
     *
     * @param imgMatrix the matrix to be analyzed
     * @return the value of the matrix
     */
    private int matrixSum(ArrayList<Double> imgMatrix){
        int sum = 0;
        for(double value : imgMatrix) {
            if(value >= 25)
                sum += value;
        }
        return sum;
    }

    /** RotateImage
     * Rotates the image according to what quadrant of the 
     * image that has the highest value.
     *
     *
     *
     */
    private double[][] RotateImage(double[][] imgMatrix, int value[]) {
        int northHalf = value[0]+value[1];
        int westHalf = value[0]+value[2];
        int eastHalf = value[1]+value[3];
        int southHalf = value[2]+value[3];

        int[] halves = {northHalf,eastHalf,southHalf,westHalf};
        System.out.println("north" + northHalf + "east " + eastHalf + "south " +southHalf + " west" +westHalf);
        System.out.println("Values " + value[0] + " " + value[1] + " " +value[2]+ " " +value[3]);
        greatestCorner = getIndexGreatestValue(value);
        greatestHalf = getIndexGreatestValue(halves);

        switch (getIndexGreatestValue(value)) {
            case 0:
                /** Should not rotate */
                break;
            case 1:
                /** if north half has the most value, it's already upright */
                if(northHalf > eastHalf){
                    break;
                }
                /** east side has greatest value */
                /** rotate 240 degrees */
                imgMatrix = RotateMatrix(imgMatrix,3);
                break;
            case 2:
                if(westHalf > southHalf){
                    /** West is greatest */
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

    private double[][] mirrorMatrix(double[][] matrix) {
        double [][] out = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                out[i][matrix.length - j - 1] = matrix[i][j];
            }
        }
        return out;
    }

    private double[][] RotateMatrix(double[][] matrix, int rotateTimes) {
        double[][] ret = new double[matrix.length][matrix.length];

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