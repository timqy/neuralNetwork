package file;

/**
 * Created by dv13thg on 10/7/15.
 */
public class FileImage {
    private String name;

    private int[][] imgMatrix;

    public FileImage() {
        imgMatrix = new int[20][20];
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgMatrix(int i , int k, int value) {
        if(value > 8)
            this.imgMatrix[i][k] = 1;
        else
            this.imgMatrix[i][k] = 0;
    }

    public String getName() {
        return name;
    }

    public int[][] getImgMatrix() {
        return imgMatrix;
    }

    /**
     * Removes alone dots in the image for better looks
     * and easier processing.
     */
    public void preProcessImage() {
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
