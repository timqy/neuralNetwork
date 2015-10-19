package file;

import core.Node;

import java.util.ArrayList;

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
        this.imgMatrix[i][k] = value;
    }

    public String getName() {
        return name;
    }

    public int[][] getImgMatrix() {
        return imgMatrix;
    }


}
