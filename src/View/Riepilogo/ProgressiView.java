package View.Riepilogo;

import View.Auth;
import View.Graph;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 04/02/17.
 */
public class ProgressiView {
    private JPanel mainPanel;
    private JPanel graphPanel1;
    private JPanel graphPanel2;
    private JPanel graphPanel3;


    public ProgressiView() {
        ArrayList<LocalDate> test = new ArrayList<>();
        test.add(LocalDate.now());
        test.add(LocalDate.now().plusDays(1));
        test.add(LocalDate.now().plusDays(2));
        ArrayList<Float> test2 = new ArrayList<>();
        test2.add(2f);
        test2.add(50f);
        test2.add(10000f);
        ArrayList<Float> test3 = new ArrayList<>();
        test3.add(10f);
        test3.add(500f);
        test3.add(8000f);
        JPanel graph1 = new Graph(test,test2,test3);
        JPanel graph2 = new Graph(test,test2,test3);
        JPanel graph3 = new Graph(test,test2,test3);
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
