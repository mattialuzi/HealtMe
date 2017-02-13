package View.Riepilogo;

import View.Graph;
import javax.swing.*;
import java.awt.*;

import Object.ProgressiObject;

/**
 *  La classe ProgressiView contiene attributi e metodi associati al file XML ProgressiView.form
 */

public class ProgressiView {
    private JPanel mainPanel;
    private JPanel graphPanel1;
    private JPanel graphPanel2;
    private JPanel graphPanel3;


    public ProgressiView(ProgressiObject progressi) {
        JPanel graph1 = new Graph(progressi.getData(),progressi.getPesi());
        JPanel graph2 = new Graph(progressi.getData(),progressi.getFabbisogno(),progressi.getCalorie_assunte());
        JPanel graph3 = new Graph(progressi.getData(),progressi.getCalorie_da_consumare(),progressi.getCalorie_consumate());
        JScrollPane scrollGraph1 = new JScrollPane(graph1);
        scrollGraph1.setPreferredSize(new Dimension( 650, 250));
        JScrollPane scrollGraph2 = new JScrollPane(graph2);
        scrollGraph2.setPreferredSize(new Dimension( 650, 250));
        JScrollPane scrollGraph3 = new JScrollPane(graph3);
        scrollGraph3.setPreferredSize(new Dimension( 650, 250));
        graphPanel1.add(scrollGraph1);
        graphPanel2.add(scrollGraph2);
        graphPanel3.add(scrollGraph3);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
