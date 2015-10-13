package file;

import core.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by dv13thg on 10/13/15.
 */
public class ImageHandler {

    /** MatrixValue
     * @param imgMatrix the matrix to be analyzed
     * @return the value of the matrix
     */
    private int matrixValue(ArrayList<Node> imgMatrix){
        int value = 0;
        for(Node node : imgMatrix) {

            if (node.getValue() > (32 / 2))
                value += node.getValue();
        }
        return value;
    }

    public void RotateImageAnalyzer(ArrayList<Node> imgMatrix) {
        int divided = 0;
        int value[] = new int[4];
        int matrixValue = (imgMatrix.size() / 10) / 4;
        while (divided < 4) {
            ArrayList<Node> tempArr = new ArrayList<Node>();

            int xHigh = matrixValue * ((divided%2) + 1);
            int xLow = ((divided%2) * matrixValue);
            int yHigh = (int) (matrixValue * (Math.floor(divided/2) + 1));
            int yLow = (int) (Math.floor(divided/2) * matrixValue);

            for (Node node : imgMatrix) {
                if (node.getX() < xHigh && xLow <= node.getX()
                        && node.getY() < yHigh && yLow <= node.getY() ) {
                    tempArr.add(node);
                }
            }
            value[divided] = matrixValue(tempArr);
            divided++;
        }
        RotateImage(imgMatrix,value);
    }

    private void RotateImage(ArrayList<Node> imgMatrix, int value[]) {
        int matrixValue = (imgMatrix.size() / 10)/2;
        switch(getIndexGreatestValue(value)){
            case 0:
                /** Should not rotate */
                break;
            case 1:
                /**  Should be rotated 240 degrees */
                for(int i = 0; i < 3; i++)
                    for(Node node : imgMatrix){
                        int x = node.getX();
                        int Y = node.getY();
                        node.setX(matrixValue - node.getY() - 1);
                        node.setY(x);
                    }
                break;
            case 2:
                /**  Should be rotated 90 degrees */
                for(Node node : imgMatrix){
                    int x = node.getX();
                    int Y = node.getY();
                    node.setX(matrixValue - node.getY() - 1);
                    node.setY(x);
                }

                /** reverse each row */
                for(Node node : imgMatrix){
                    int x = node.getX();
                    node.setX(matrixValue - x - 1);
                }

                break;
            case 3:
                /**  Should be rotated 180 degrees */
                for(int i = 0; i < 2; i++)
                    for(Node node : imgMatrix){
                        int x = node.getX();
                        int Y = node.getY();
                        node.setX(matrixValue - node.getY() - 1);
                        node.setY(x);
                    }

                /** reverse each row */
                for(Node node : imgMatrix){
                    int x = node.getX();
                    node.setX(matrixValue - x - 1);
                }
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
}
