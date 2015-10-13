package gui;

import core.Node;
import file.FaceFile;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dv13thg on 10/7/15.
 */
public class Gui {

    private final JFrame windowFrame;
    private final ArrayList<FaceFile> imgMatris;
    private Canvas canvas;
    private int imgIndex;

    public Gui(ArrayList<FaceFile> imgMatris, int imgIndex) {
        this.imgMatris = imgMatris;
        this.imgIndex = imgIndex;
        windowFrame = new JFrame();
        windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windowFrame.setSize(1280, 960);
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

                FaceFile f = imgMatris.get(imgIndex);

                for (Node n: f.getNodeArr()) {
                    if (n.getValue() > 20) {
                        g.setColor(Color.black);
                    } else if (n.getValue() > 10) {
                        g.setColor(Color.darkGray);
                    } else {
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(n.getX() * 10, n.getY() * 10, 1 * 10, 1 * 10);
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
