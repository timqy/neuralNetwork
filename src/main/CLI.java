package main;

import file.FaceFile;
import file.ImageParser;

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

    private ImageParser parser;
    private HashMap<String, Integer> facitMap;
    private ArrayList<FaceFile> nodeList;

    private Scanner scanner;

    /**
     * Constructs a new CLI
     */
    public CLI() {
        facitMap = new HashMap<>();
        nodeList = new ArrayList<>();

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

            if (userInput.equals("help")) {
                printHelp();
            } else if(userInput.equals("loadfacit")) {
                loadfacit();
            } else if(userInput.equals("loadimages")) {
                loadimages();
            } else if(userInput.equals("status")) {
                status();
            } else {
                System.err.println("Unknown command.");
            }
        }
    }

    /**
     * Prints out the statistics of the program. Such as
     * how many noads that are loaded and how many answers that are loaded in.
     */
    private void status() {
        System.out.println("There is "+nodeList.size() +" nodes loaded.");
        System.out.println("There is "+facitMap.size() +" facit entries loaded");
    }

    /**
     * Loads the facit files into the facit map.
     */
    private void loadfacit() {

        try {
            facitMap = parser.parseFacit("resources/training-facit.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the imagefiles.
     */
    private void loadimages() {

        try {
            nodeList = parser.parseImage("resources/training.txt");
        } catch (IOException e) {
            e.printStackTrace();
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
