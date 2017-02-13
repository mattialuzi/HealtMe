package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * La classe IndexProgAlimView contiene attributi e metodi associati al file XML IndexProgAlimView.form
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

    /** Listener associati ad elementi di cui è composto il file XML IndexProgAlimForm.form */

    public void addNewProgManButtonListener(ActionListener listener){
        manualeButton.addActionListener(listener);
    }

    public void addNewProgCombButtonListener(ActionListener listener){
        combinatoButton.addActionListener(listener);
    }
}
