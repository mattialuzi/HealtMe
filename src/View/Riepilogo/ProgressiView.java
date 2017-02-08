package View.Riepilogo;

import View.Auth;
import View.Graph;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import Object.ProgressiObject;

/**
 * Created by lorenzobraconi on 04/02/17.
 */
public class ProgressiView {
    private JPanel mainPanel;
    private JPanel graphPanel1;
    private JPanel graphPanel2;
    private JPanel graphPanel3;


    public ProgressiView(ProgressiObject progressi) {
        /*ArrayList<LocalDate> test = new ArrayList<>();
        test.add(LocalDate.now());
        test.add(LocalDate.now().plusDays(1));
        test.add(LocalDate.now().plusDays(2));
        ArrayList<Double> test2 = new ArrayList<>();
        test2.add(2d);
        test2.add(50d);
        test2.add(10000d);
        ArrayList<Double> test3 = new ArrayList<>();
        test3.add(10d);
        test3.add(500d);
        test3.add(8000d);*/
        JPanel graph1 = new Graph(progressi.getData(),progressi.getPesi());
        JPanel graph2 = new Graph(progressi.getData(),progressi.getFabbisogno(),progressi.getCalorie_assunte());
        JPanel graph3 = new Graph(progressi.getData(),progressi.getCalorie_da_consumare(),progressi.getCalorie_consumate());
        JScrollPane scrollGraph1 = new JScrollPane(graph1);
        //graph1.setAutoscrolls(true);
        scrollGraph1.setPreferredSize(new Dimension( 650, 250));
        JScrollPane scrollGraph2 = new JScrollPane(graph2);
        //graph2.setAutoscrolls(true);
        scrollGraph2.setPreferredSize(new Dimension( 650, 250));
        JScrollPane scrollGraph3 = new JScrollPane(graph3);
        //graph3.setAutoscrolls(true);
        scrollGraph3.setPreferredSize(new Dimension( 650, 250));
        graphPanel1.add(scrollGraph1);
        graphPanel2.add(scrollGraph2);
        graphPanel3.add(scrollGraph3);

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}
