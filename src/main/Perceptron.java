package main;

/**
 * @author dv13lan, dv13thg
 * @version 20 okt - 2015
 */
public class Perceptron {

    /**
     * Launches the program either in CLI mode or in automatic mode depending
     * on the number of arguments.
     *
     * @param args If 0 arguments is passed then the program will automaticly
     *             launch in CLI mode. If 3 arguments are passed into the program
     *             it will launch in automatic mode and will give
     */
    public static void main(String[] args) {

        // Launch in skynet mode.
        if(args.length == 0)
            new CLI().run();

        else if(args.length == 3) {
            // launch automatic mode.
        } else {
            System.out.println("You need either 3 or 0 arguments" +
                    " to launch the program");
            System.exit(1);
        }

    }
}
