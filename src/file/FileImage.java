package file;

/**
 * A FileImage is a 2d array of integers associated with a name.
 * The FileImage contains a name with a 2D array representing the
 * image data.
 *
 * @author dv13thg, dv13lan
 * @version 20 okt - 2015
 */
public class FileImage {

    public static final int PIXEL_THRESHOLD = 8;
    private String name;

    private double[][] imgMatrix;

    /**
     * Creates a new FileImage and allocates a 20*20 2d array for
     * the pixels.
     */
    public FileImage() {
        imgMatrix = new double[20][20];
    }

    /**
     * Sets the name of the Image, this is so it can be compared to the
     * facit file later.
     * @param name A string representing the name for this FileImage.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a value into the 2d array of integers. It uses a threshold to
     * determine if the pixel is black enough to be counted as 1 or as 0.
     *
     * @param x X axis coordinate.
     * @param y Y axis coordinate.
     * @param value An integer representing a pixel value.
     */
    public void setImgMatrix(int x , int y, int value) {
       this.imgMatrix[x][y] = value;
    }

    /**
     * Returns the name associated with this FaceImage object.
     * @return A String representing the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the 2d array (Image) of this Faceimage.
     * @return The image represented in a 2D array of integers.
     */
    public double[][] getImgMatrix() {
        return imgMatrix;
    }

    public void setCurrentImage(double[][] imgMatrix){
        this.imgMatrix = imgMatrix;
    }

    /**
     * Removes alone dots in the image for better looks
     * and easier processing.
     */
    public void preProcessImage() {

        for (int x = 0; x < imgMatrix.length; x++) {
            for (int y = 0; y < imgMatrix[0].length; y++) {
                if(imgMatrix[x][y] > PIXEL_THRESHOLD)
                    imgMatrix[x][y] = 1;
                else
                    imgMatrix[x][y] = 0;
            }
        }

        for (int i = 0; i < imgMatrix.length; i++) {
            for (int j = 0; j < imgMatrix[0].length; j++) {
                if(!adjNodes(i,j)) {
                    imgMatrix[i][j] = 0;
                }
            }
        }
    }

    /**
     * Find out adjNodes
     * @param x Coordinate in the X-axis
     * @param y Coordinate in the Y-axis
     * @return True if a another activated pixel has been
     * found in the vicinity of the pixel.
     */
    private boolean adjNodes(int x, int y) {
        boolean foundAdjNode = false;

        if(x - 1 != -1)
            if(imgMatrix[x-1][y] == 1)
                foundAdjNode = true;
        if(x+1 < imgMatrix.length)
            if(imgMatrix[x+1][y] == 1)
                foundAdjNode = true;

        if(y-1 != -1)
            if(imgMatrix[x][y-1] == 1)
                foundAdjNode = true;

        if(y+1 < imgMatrix.length)
            if(imgMatrix[x][y+1] == 1)
                foundAdjNode = true;

        return foundAdjNode;
    }
}
