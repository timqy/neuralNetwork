package file;

/**
 * @author dv13lan, dv13thg
 * @version 2015-10-22
 *
 * This class handles the rotation of images.
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

    /**
     * Splices the 2d array into 4 sub arrays, sums each sub array up and
     * returns the number of rotations needed to get a correctly rotated image.
     *
     * @param newImg 2D array to be sliced
     *
     * @return An integer representing the number of 90 degree
     * rotations.
     */
    private int analyzeRotation(double[][] newImg) {

        double[][] northWest = split(newImg,0,0,10,10); // North west
        double[][] southWest = split(newImg,0,10,10,20); // Southwest
        double[][] northEast = split(newImg,10,0,20,10); // North East
        double[][] southEast = split(newImg,10,10,20,20); // South East

        int sumNW = matrixSum(northWest);
        int sumNE = matrixSum(southWest);
        int sumSE = matrixSum(northEast);
        int sumSW = matrixSum(southEast);

        switch (rotationOffset(sumNW,sumNE,sumSE,sumSW)) {
            case -1:
                /** if two sides are the same */
                if(sumNE == sumNW) {
                    return 0;
                } else if(sumNE == sumSE) {
                    return 1;
                } else if(sumSE == sumSW) {
                    return 2;
                } else if(sumSE == sumNW) {
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

    /**
     * Mirrors the image horizontally.
     * @param matrix 2D array to mirror.
     *
     * @return the new mirrored 2d image.
     */
    private double[][] mirrorX(double[][] matrix) {
        double [][] out = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                out[i][matrix.length - j - 1] = matrix[i][j];
            }
        }
        return out;
    }

    /**
     * Mirrors the image vertically (Y axis).
     * @param matrix 2D array to mirror.
     * @return the new mirrored 2d image.
     */
    private double[][] mirrorY(double[][] matrix) {
        double [][] out = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                out[matrix.length - i- 1][j ] = matrix[i][j];
            }
        }
        return out;
    }

    /**
     * Calculates the rotation offset from the 4 subarrays
     * @param sumNW 2D sub array of northwest corner.
     * @param sumNE 2D sub array of northeast corner.
     * @param sumSE 2D sub array of southeast corner.
     * @param sumSW 2D sub array of southwest corner.
     *
     * @return An integer representing the sub array of the
     * corner with most active pixels.
     */
    private int rotationOffset(int sumNW, int sumNE, int sumSE, int sumSW) {
        int[] collection = {sumNW,sumNE,sumSW,sumSE};

        int max = 0;
        int index = 0;
        for (int i = 0; i < collection.length; i++) {
            if (collection[i] >= max) {
                if(collection[i] == max){
                    return -1;
                }
                max = collection[i];
                index = i;

            }
        }
        return index;
    }

    /**
     * Sums all the values in an array and returns an int representing the
     * number of active pixels in that array.
     *
     * @param array a 2D array image.
     * @return An integer representing the number of active pixels.
     */
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

    /**
     * Slices an array given a set of train and end coordinates.
     * @param image Array to be sliced.
     * @param startX train x value to begin slicing from.
     * @param startY Starting y value to begin slicing from.
     * @param endX End x coordinate to slice to.
     * @param endY End y coordinate to slice to.
     *
     * @return A sub array containing a part of the original array.
     */
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

    /**
     * Rotates the image 90 degrees.
     * @param image Image to be rotated.
     * @return A new rotated 2D array.
     */
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