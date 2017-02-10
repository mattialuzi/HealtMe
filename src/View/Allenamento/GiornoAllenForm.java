package View.Allenamento;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 07/02/17.
 */
public class GiornoAllenForm {
    private JPanel mainPanel;
    private JLabel Titlelabel;
    private JButton addSeduta;
    private JTable sedutaEffTable;
    private JButton confermaSeduta;
    private JButton removeSeduta;

    public GiornoAllenForm(String Title) {
        Titlelabel.setText(Title);
        String[] columnnames = {"Esercizio", "Quantità", "Unità di misura"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sedutaEffTable.setModel(new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setButtonsVisible () {
        addSeduta.setVisible(true);
        removeSeduta.setVisible(true);
    }

    public void enableConfermaButton(boolean completato){
        confermaSeduta.setEnabled(!completato);
    }

    public void visibilityConfermaAndAddButtons(boolean comb, boolean giornopieno, boolean completato){
        confermaSeduta.setVisible(comb && giornopieno);
        enableConfermaButton(completato);
        addSeduta.setVisible(giornopieno);
        removeSeduta.setVisible(giornopieno);
        addSeduta.setEnabled(!comb || !completato);
        sedutaEffTable.setEnabled(!comb || !completato );
    }

    public void addListenersAndShowButtons(ActionListener listener) {
        addSeduta.addActionListener(listener);
        setButtonsVisible();
    }

    public void addListenersForRemoveButtons(ActionListener listener){
        removeSeduta.addActionListener(listener);
    }

    public void addListenerConfermaButton(ActionListener listener){ confermaSeduta.addActionListener(listener);}

    public void addTableSelectionListener(ListSelectionListener listener) {
        sedutaEffTable.getSelectionModel().addListSelectionListener(listener);
    }

    public JTable getSedutaEffTable() {
        return sedutaEffTable;
    }

    public JButton getRemoveSeduta() {
        return removeSeduta;
    }
}
