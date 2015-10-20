package gui;

import file.FileImage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Will paint a image to a Jframe. Has been used to debug image processing
 * and rotation of images.
 *
 * @author dv13thg, dv13lan
 * @version 15 okt - 2015
 */
public class Gui {

    private final JFrame windowFrame;
    private final ArrayList<FileImage> imgMatris;
    private Canvas canvas;
    private int imgIndex;

    /**
     * Constructs a new GUI.
     * @param imgMatris 2D image array to be used as database.
     * @param imgIndex index to fetch from the database.
     */
    public Gui(ArrayList<FileImage> imgMatris, int imgIndex) {
        this.imgMatris = imgMatris;
        this.imgIndex = imgIndex;
        windowFrame = new JFrame();
        windowFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        windowFrame.setSize(640, 640);
        windowFrame.setFocusable(true);
        windowFrame.requestFocusInWindow();
        windowFrame.add(buildCanvas());
    }


    /**
     * builds a jtable with a OfferTableModel, rowsorter and
     * Tableselectionlistener and returns it as an scrollpane.
     *
     * @return JScrollPane the pane to be added to windowFrame.
     */
    public Canvas buildCanvas() {
        canvas = new Canvas() {
            //FIELDS
            public int WIDTH = 1024;
            public int HEIGHT = WIDTH / 16 * 9;

            public void paint(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, WIDTH, HEIGHT);

                FileImage f = imgMatris.get(imgIndex);

                for (int x = 0; x < f.getImgMatrix().length ; x++) {
                    for (int y = 0; y < f.getImgMatrix().length ; y++) {
                        if (f.getImgMatrix()[x][y] == 1) {
                            g.setColor(Color.black);
                        } else {
                            g.setColor(Color.WHITE);
                        }
                        g.fillRect(x * 10, y *10, 10, 10);
                    }
                }
            }
        };
        canvas.setBackground(Color.WHITE);
        return canvas;
    }


    /**
     * sets the frame visibility to true
     */
    public void setVisible() {
        windowFrame.setVisible(true);
    }

}
