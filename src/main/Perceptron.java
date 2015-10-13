package main;

import file.FaceFile;
import file.ImageHandler;
import file.ImageParser;
import gui.Gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dv13thg on 10/7/15.
 */
public class Perceptron {

    public static void main(String[] args) {
        ImageParser parser = ImageParser.getInstance();
        ArrayList<FaceFile> imgMatris;

        try {
            imgMatris = parser.parseImage(args[0]);
            System.out.println("Image matris is length :" + imgMatris.size());
            Gui gui = new Gui(imgMatris);
            gui.setVisible();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error while reading file");
        } catch (NumberFormatException ex) {
            System.err.println("Image is wrong format or corrupted");
        }

        CLI cli;

        cli = new CLI();

        cli.run();
    }
}
