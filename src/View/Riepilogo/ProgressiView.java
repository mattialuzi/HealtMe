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
        JScrollPane scrollGraph = new JScrollPane(new Graph(test));
        mainPanel.add(scrollGraph);


    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
