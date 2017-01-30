package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 25/01/17.
 */
public class ProgAlimCombView {
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JButton indietroButton;
    private JButton generaProgrammaButton;

    public ProgAlimCombView() {
    }

    public void addIndietroButtonListener(ActionListener listener){
        indietroButton.addActionListener(listener);
    }

    public void addGeneraProgrammaButtonListener(ActionListener listener){
        generaProgrammaButton.addActionListener(listener);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
