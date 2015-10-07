package file;

import core.Node;

import java.util.ArrayList;

/**
 * Created by dv13thg on 10/7/15.
 */
public class FaceFile {
    private String name;

    private ArrayList<Node> imgMatrix;

    public FaceFile() {

        this.imgMatrix = new ArrayList<Node>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgMatrix(int i , int k, int value) {
        this.imgMatrix.add(new Node(i,k,value));
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getNodeArr() {
        return imgMatrix;
    }


}
