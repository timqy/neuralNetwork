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
            System.out.println( xLow + " < nodeX < " + xHigh  );
            System.out.println( yLow + " < nodeY < " + yHigh  );
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
        System.out.println("Value " + Arrays.toString(value));
        int matrixValue = (imgMatrix.size() / 10)/2;
        switch(getIndexGreatestValue(value)){
            case 0:
                System.out.println(" Should NOT rotate");

                break;
            case 1:
                /**  bla bla */
                System.out.println("1  Rotated 240 degrees");
                for(int i = 0; i < 3; i++)
                    for(Node node : imgMatrix){
                        int x = node.getX();
                        int Y = node.getY();
                        node.setX(matrixValue - node.getY() - 1);
                        node.setY(x);
                    }
                break;
            case 2:
                System.out.println(" 2 Should rotate 180 degrees");
                for(int i = 0; i < 2; i++)
                    for(Node node : imgMatrix){
                        int x = node.getX();
                        int Y = node.getY();
                        node.setX(matrixValue - node.getY() - 1);
                        node.setY(x);
                    }
                break;
            case 3:
                System.out.println(" 3 should rotate 90 degrees");
                for(Node node : imgMatrix){
                    int x = node.getX();
                    int Y = node.getY();
                    node.setX(matrixValue - node.getY() - 1);
                    node.setY(x);
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
