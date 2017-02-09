package View.Allenamento;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;


/**
 * Created by ALLDE on 07/02/2017.
 */
public class ProgAllenCombView {
    private JPanel mainPanel;
    private JComboBox comboDisponibilita;
    private JList listaEsercizi;
    private JButton addEsercizio;
    private JButton removeEsercizio;
    private JScrollPane scrollPane;
    private JButton indietroButton;
    private JButton generaProgrammaButton;
    private FormEserciziPraticati esercizipraticati;

    public ProgAllenCombView() {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

        /*esercizipraticati.setLocationRelativeTo(null);
        //nuovopasto = e.getActionCommand();
        //setPortataItems();
        esercizipraticati.setTitle("Inserisci nuovo esercizio praticato ");
        esercizipraticati.getButtonOK();
        esercizipraticati.pack();
        esercizipraticati.setVisible(true); */

    public void addPlusButtonListener(ActionListener listener) {
        addEsercizio.addActionListener(listener);
    }

    public void addMinusButtonListener(ActionListener listener){
        removeEsercizio.addActionListener(listener);
    }

    public void addIndietroButtonListener(ActionListener listener){
        indietroButton.addActionListener(listener);
    }

    public void addGeneraProgrammaButtonListener (ActionListener listener ) {
        generaProgrammaButton.addActionListener(listener);
    }

    public JList getListaEsercizi() {
        return listaEsercizi;
    }

    public void enableGeneraProgrammaButton(boolean flag) {
        generaProgrammaButton.setEnabled(flag);
    }

    public JComboBox getComboDisponibilita () {
        return comboDisponibilita;
    }

}
