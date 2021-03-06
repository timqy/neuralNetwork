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
     * @param args If 0 arguments is passed then the program will automatically
     *             launch in CLI mode. If 3 arguments are passed into the program
     *             it will launch in automatic mode and will give
     */
    public static void main(String[] args) {

        // Launch in skynet mode.
        if (args.length == 0)
            new CLI().run();

            // Run in automatic mode
        else if (args.length == 3)
            new AutoRunner(args[0], args[1], args[2]).run();

            //invalid arguments.
        else {
            System.out.println("You need either 3 or 0 arguments" +
                    " to launch the program");

            System.exit(1);
        }

    }
}
