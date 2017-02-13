package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * La classe ProgAlimCombView contiene attributi e metodi associati al file XML ProgAlimCombView.form
 */

public class ProgAlimCombView {
    private JPanel mainPanel;
    private JComboBox tipoalimBox;
    private JButton indietroButton;
    private JButton generaProgrammaButton;

    public ProgAlimCombView() {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JComboBox getTipoalimBox() {
        return tipoalimBox;
    }

    /** Metodo che crea componenti dell'User Interface */

    public void addIndietroButtonListener(ActionListener listener){
        indietroButton.addActionListener(listener);
    }

    public void addGeneraProgrammaButtonListener(ActionListener listener){
        generaProgrammaButton.addActionListener(listener);
    }
}
