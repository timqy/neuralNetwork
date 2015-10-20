package main;

import core.ANN;
import file.FileImage;
import file.ImageHandler;
import file.ImageParser;
import gui.Gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author dv13lan
 * @version 2015-10-13
 *
 * A basic commandline interface for the perception robot.
 */
public class CLI {

    public static final String RESOURCES_TRAINING_TXT = "resources/training.txt";
    public static final String RESOURCES_TRAINING_FACIT_TXT = "resources/training-facit.txt";

    private ImageParser parser;
    private HashMap<String, Integer> facitMap;
    private ArrayList<FileImage> fileImages;
    private ImageHandler imageHandler;


    private Scanner scanner;

    /**
     * Constructs a new CLI. Setups the hashmaps and the arraylists.
     * It also gets an instance of the Imageparser as well as a new scanner from System.in
     */
    public CLI() {
        facitMap = new HashMap<>();
        fileImages = new ArrayList<>();
        imageHandler = new ImageHandler();

        scanner = new Scanner(System.in);
        parser = ImageParser.getInstance();
    }


    public void run() {
        boolean quit = false;
        String userInput;

        System.out.println("Welcome to Percetron CLI (: ");
        System.out.println("Use help for available commands");

        while (!quit) {

            System.out.print("skynet -> ");
            userInput = scanner.nextLine();
            String[] argv = userInput.split(" ");

            if (argv[0].equals("help")) {

                printHelp();

            } else if(argv[0].equals("loadfacit")) {

                if(argv.length == 2)
                    loadfacit(argv[1]);
                else
                    loadfacit();

            } else if(argv[0].equals("loadimages")) {

                if (argv.length == 2)
                    loadimages(argv[1]);
                else
                    loadimages();

            } else if(argv[0].equals("status")) {
                status();

            } else if (argv[0].equals("showimg")) {
                if(argv.length == 2) {
                    try {
                        showImage(Integer.parseInt(argv[1]));
                    }catch (NumberFormatException ex) {
                        System.err.println("Error: Second argument needs to be a number.");
                    }
                }
            } else if(argv[0].equals("train")) {

                startTraining(argv);

            } else {
                //HELLO
                System.err.println("Unknown command.");
            }
        }
    }

    /**
     * Starts the trainer. Will reset the nuralnetwork.
     * @param argv
     */
    private void startTraining(String[] argv) {
        if(argv.length == 2) {
            ANN trainer = new ANN(fileImages, facitMap);
            trainer.start(Integer.parseInt(argv[1]));
        }
    }

    private void performanceCheck(){

    }

    /**
     *
     * @param imgIndex
     */
    private void showImage(int imgIndex) {

        imageHandler.RotateImageAnalyzer(fileImages.get(imgIndex).getImgMatrix());

        Gui g = new Gui(fileImages, imgIndex);
        g.setVisible();

    }

    /**
     * Prints out the statistics of the program. Such as
     * how many noads that are loaded and how many answers that are loaded in.
     */
    private void status() {
        System.out.println("There is "+ fileImages.size() +" nodes loaded.");
        System.out.println("There is "+facitMap.size() +" facit entries loaded");
    }

    /**
     * Loads the facit files into the facit map.
     */
    private void loadfacit() {

        try {
            facitMap = parser.parseFacit(RESOURCES_TRAINING_FACIT_TXT);
        } catch (FileNotFoundException ff) {
            System.err.println("Could not load file "+RESOURCES_TRAINING_FACIT_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Loaded default faceit path, "+facitMap.size() +" entities loaded!");
    }

    /**
     * Loads a facit file from a path.
     * @param filePath
     */
    private void loadfacit(String filePath) {
        try{
            facitMap = parser.parseFacit(filePath);
        } catch (FileNotFoundException ff) {
            System.err.println("Could not load file "+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }



        System.out.println("Loaded faceit path, "+facitMap.size() +" entities loaded!");
    }

    /**
     * Loads the imagefiles.
     */
    private void loadimages() {

        try {
            fileImages = parser.parseImage(RESOURCES_TRAINING_TXT);
            startImagePreProcessor();
        } catch (FileNotFoundException ff) {
            System.err.println("Could not load file "+RESOURCES_TRAINING_TXT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //for(FileImage face : fileImages){
          //  imageHandler.RotateImageAnalyzer(face.getImgMatrix());
        //}

        System.out.println("Loaded default fileImages path, "+ fileImages.size() +" entities loaded!");

    }

    /**
     * Overloaded method to use a custom filepath.
     * @param filePath A path to the imagefile.
     */
    private void loadimages(String filePath) {
        try {
            fileImages = parser.parseImage(filePath);
            startImagePreProcessor();
        } catch (FileNotFoundException ff) {
            System.err.println("Could not load file "+filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded fileImages path, "+ fileImages.size() +" entities loaded!");
    }

    private void startImagePreProcessor() {
        for(FileImage image : fileImages) {
            image.preProcessImage();
        }
    }


    /**
     * Prints out the help menu.
     */
    private void printHelp() {
        System.out.println("Available Commands: ");
        System.out.println("\t help");
        System.out.println("\t loadimages");
        System.out.println("\t loadfacit");
        System.out.println("\t status");
    }
}
