package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 12/01/17.
 */
public class GiornoAlimView {
    private JPanel mainPanel;
    private JButton addColazione;
    private JButton removeColazione;
    private JButton addPranzo;
    private JButton removePranzo;
    private JButton addCena;
    private JButton removeCena;
    private JButton addSpuntino;
    private JButton removeSpuntino;

    public GiornoAlimView() {
    }

    public void addAggiungiCiboEffettivoButtonListener(ActionListener listener) {
        addColazione.addActionListener(listener);
        addPranzo.addActionListener(listener);
        addCena.addActionListener(listener);
        addSpuntino.addActionListener(listener);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
