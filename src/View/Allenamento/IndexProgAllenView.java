package View.Allenamento;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by ALLDE on 07/02/2017.
 */
public class IndexProgAllenView {

    private JPanel mainPanel;
    private JButton combinatoButton;
    private JButton manualeButton;

    public IndexProgAllenView() {
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
