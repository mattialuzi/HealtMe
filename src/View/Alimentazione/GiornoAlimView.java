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

    public void addListnersAndshowButtons(ActionListener listener) {
        addColazione.addActionListener(listener);
        addPranzo.addActionListener(listener);
        addCena.addActionListener(listener);
        addSpuntino.addActionListener(listener);
        setaddButtonsVisible();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    
    public void setaddButtonsVisible () {
        addColazione.setVisible(true);
        removeCena.setVisible(true);
        addPranzo.setVisible(true);
        removePranzo.setVisible(true);
        addCena.setVisible(true);
        removeCena.setVisible(true);
        addSpuntino.setVisible(true);
        removeSpuntino.setVisible(true);
    }
}

