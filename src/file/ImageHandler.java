package file;

/**
 * Created by dv13thg on 10/13/15.
 *
 * rotates the image so it faces upwards.
 */
public class ImageHandler {

    /**
     * analyzes the image by converting the image into 
     * four smaller ones, sums each image, calls rotate.
     * @param image the image to be rotated
     */
    public void RotateImageAnalyzer(FileImage image) {
        double[][] newImg = image.getImgMatrix();

        int noOfRotations = analyzeRotation(newImg);

        if(noOfRotations == -1) {
            newImg = mirrorX(newImg);
        } else if(noOfRotations == -2) {
            newImg = mirrorY(newImg);
        } else {
            for(int i = 0; i < noOfRotations; i++) {
                newImg = rotateImage(newImg);
            }
        }

        image.setCurrentImage(newImg);
    }

    private int analyzeRotation(double[][] newImg) {

        double[][] northWest = split(newImg,0,0,10,10); // North west
        double[][] southWest = split(newImg,0,10,10,20); // Southwest
        double[][] northEast = split(newImg,10,0,20,10); // North East
        double[][] southEast = split(newImg,10,10,20,20); // South East

        int sumNW = matrixSum(northWest);
        int sumNE = matrixSum(southWest);
        int sumSE = matrixSum(northEast);
        int sumSW = matrixSum(southEast);



        System.out.println("rotation offeset : " + rotationOffset(sumNW,sumNE,sumSE,sumSW));
        System.out.println("sumNW " + sumNW + " sumNe " + sumNE + " sumsw " + sumSW + " sumse " + sumSE);
        switch (rotationOffset(sumNW,sumNE,sumSE,sumSW)) {
            case -1:
                /** if two sides are the same */
                if(sumNE == sumNW && sumNE + sumNW > 0) {
                    return 0;
                } else if(sumNE == sumSE && sumNE + sumSE > 0) {
                    return 1;
                } else if(sumSE == sumSW && sumSE + sumSW > 0) {
                    return 2;
                } else if(sumSE == sumNW && sumSE + sumNE > 0) {
                    return 3;
                }
            case 0:
                /** Should not rotate */
                return 0;
            case 1:
                /** if north half has the most value, it's already upright */
                if(sumNE + sumNW > sumNE + sumSE){
                    return -1;
                } else {
                    /** east side has greatest value */
                    /** rotate 240 degrees */
                    return 3;
                }
            case 2:
                if(sumNW + sumSW > sumSE + sumSW){
                    /** West is greatest */
                    return -2;

                } else {
                    /** South has greatest value */
                    return 1;
                }
            case 3:
                if(sumNE + sumSE > sumSE + sumSW){
                    /** east half is greatest */
                    return 2;
                } else {
                    /** south has greatest value */
                    return 3;
                }
        }

        return 0;
    }

    private double[][] mirrorX(double[][] matrix) {
        double [][] out = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                out[i][matrix.length - j - 1] = matrix[i][j];
            }
        }
        return out;
    }


    private double[][] mirrorY(double[][] matrix) {
        double [][] out = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                out[matrix.length - i- 1][j ] = matrix[i][j];
            }
        }
        return out;
    }

    private int rotationOffset(int sumNW, int sumNE, int sumSE, int sumSW) {
        int[] collection = {sumNW,sumNE,sumSW,sumSE};

        int max = 0;
        int index = 0;
        for (int i = 0; i < collection.length; i++) {
            if (collection[i] >= max) {
                if(collection[i] == max && max != 0){
                    return -1;
                }
                max = collection[i];
                index = i;

            }
        }
        return index;
    }

    private int matrixSum(double[][] array){
        int sum = 0;
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[0].length; y++) {
                if(array[x][y] > 26) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    private double[][] split(double[][] image, int startX, int startY, int endX, int endY) {
        double[][] subArray = new double[startX+endX][startY+endY];

        int xCounter = 0;
        int yCounter = 0;

        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                subArray[xCounter][yCounter] = image[i][j];
                yCounter++;
            }
            yCounter = 0;
            xCounter++;
        }

        return subArray;
    }

    private double[][] rotateImage(double[][] image) {
        double[][] ret = new double[20][20];

        for (int i = 0; i < image.length; ++i) {
            for (int j = 0; j < image[0].length; ++j) {
                ret[i][j] = image[image.length - j - 1][i];
            }
        }
        return ret;
    }

}