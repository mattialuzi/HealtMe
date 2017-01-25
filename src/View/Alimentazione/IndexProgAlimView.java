package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 25/01/17.
 */
public class IndexProgAlimView {

    private JPanel mainPanel;
    private JButton combinatoButton;
    private JButton manualeButton;

    public IndexProgAlimView() {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addNewProgManButtonListener(ActionListener listener){
        manualeButton.addActionListener(listener);
    }

    public void addNewProgCombButtonListener(ActionListener listener){
        combinatoButton.addActionListener(listener);
    }
}
