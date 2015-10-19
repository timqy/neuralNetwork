package core;

import java.util.ArrayList;

/**
 * Created by dv13thg on 10/7/15.
 */
public class Node {
    private int x;
    private int y;
    private int value;
    private ArrayList<Node> connection;

    public Node(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setConnection(ArrayList<Node> nodeList) {
        nodeList.remove(this);
        this.connection = nodeList;
    }
}
