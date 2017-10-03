package com.neu.project.graphics;

import com.neu.project.initialization.Sensor;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author rohan
 */
public class DisplayGraphicsForSwarm extends JPanel {

    private ArrayList<Sensor> sensorList;
    private String iteration;
    private int i = 0;
    int fontSize = 30;
    char[] c = {'s'};

    /**
     *
     * @param sensorSwarm
     */
    public void drawGraphics(ArrayList<Sensor> sensorSwarm) {
        sensorList = sensorSwarm;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {

        int j = 0;
        super.paintComponent(g);
        Image img1 = Toolkit.getDefaultToolkit().getImage("C:\\Users\\rohan\\Desktop\\world-map-large.png");
        g.drawImage(img1, 0, 0, this);
        g.finalize();
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.drawString("Yellowstone Volcano", 260, 560);
        g.setColor(Color.red);
        g.fillOval(240, 560, 30, 30);
        g.setColor(Color.red);
        int k = 0, x = 210, y = 530, z = 100;

        while (k < 17) {
            g.drawOval(x, y, z, z);
            if (k == 10) {
                g.setColor(Color.ORANGE);
            }
            k++;
            x = x - 100;
            y = y - 100;
            z = z + 200;
        }
        iteration = "Iteration: " + i;
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
        g.drawString(iteration, 50, 50);

        for (Sensor h : sensorList) {
            g.setColor(Color.blue);
            g.fillOval((int) h.getLocation().getLocation()[0], (int) h.getLocation().getLocation()[1], 10, 10);
            g.setColor(Color.blue);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.drawString("s" + j, (int) h.getLocation().getLocation()[0], (int) h.getLocation().getLocation()[1]);

            j++;
        }
        i++;
    }

}
