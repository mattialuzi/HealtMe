package View.Riepilogo;

import View.Graph;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lorenzobraconi on 04/02/17.
 */
public class ProgressiView {
    private JPanel mainPanel;
    private JPanel graphPanel;


    public ProgressiView() {
        ArrayList<LocalDate> test = new ArrayList<>();
        test.add(LocalDate.now());
        test.add(LocalDate.now().plusDays(1));
        test.add(LocalDate.now().plusDays(2));
        ArrayList<Float> test2 = new ArrayList<>();
        test2.add(2f);
        test2.add(50f);
        test2.add(10000f);
        JPanel graph = new Graph(test,test2);
        JScrollPane scrollGraph = new JScrollPane(graph);
        graph.setAutoscrolls(true);
        scrollGraph.setPreferredSize(new Dimension( 100, 200));
        graphPanel.add(scrollGraph);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
