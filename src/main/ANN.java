package main;

import java.util.Arrays;
import java.util.Scanner;

public class ANN {

    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        double threshold = 1.2;
        double learningRate = 0.08;

        // Init weights

        double[] weights = { -1.4, 1.8 };

        int[][][] trainingData = {
                {{8, 1}, {-1}},
                {{3, 2}, {-1}},
                {{6, 3}, {-1}},
                {{1, 4}, {-1}},
                {{9, 5}, {1}},
                {{5, 6}, {1}},
                {{2, 7}, {1}},
                {{4, 8}, {1}},
                {{7, 9}, {1}},
        };

        // Start training loop
        while (true) {
            int errorCount = 0;
            // Loop over training data
            for (int i = 0; i < trainingData.length; i++) {
                System.out.println("Starting weights: " + Arrays.toString(weights));
                // Calculate weighted input
                double weightedSum = 0;
                for (int ii = 0; ii < trainingData[i][0].length; ii++) {
                    weightedSum += trainingData[i][0][ii] * weights[ii];
                }

                // Calculate output
                int output = 0;
                if (threshold <= weightedSum) {
                    output = 1;
                }

                System.out.println("Target output: " + trainingData[i][1][0]
                        + ", " + "Actual Output: " + output);

                // Calculate error
                int error = trainingData[i][1][0] - output;
                System.out.println("Error:  " + error);
                // Increase error count for incorrect output
                if (error != 0) {
                    errorCount++;
                }

                // Update weights
                for (int ii = 0; ii < trainingData[i][0].length; ii++) {
                    weights[ii] += learningRate * error
                            * trainingData[i][0][ii];
                }

                System.out.println("New weights: " + Arrays.toString(weights));
                System.out.println();
            }

            // If there are no errors, stop
            if (errorCount == 0) {
                System.out
                        .println("Final weights: " + Arrays.toString(weights));
                System.exit(0);
            }
        }
    }

}