package View.Riepilogo;

import View.Graph;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 04/02/17.
 */
public class ProgressiView {
    private JPanel mainPanel;
    private JPanel graphPanel;


    public ProgressiView() {
        ArrayList<Integer> test = new ArrayList<>();
        test.add(0);
        test.add(1);
        test.add(2);
        JPanel graph = new Graph(test);
        JScrollPane scrollGraph = new JScrollPane(graph);
        graph.setAutoscrolls(true);
        scrollGraph.setPreferredSize(new Dimension( 100, 300));
        graphPanel.add(scrollGraph);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
