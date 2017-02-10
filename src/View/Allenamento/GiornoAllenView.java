package View.Allenamento;

import Object.Enum.GiornoEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 07/02/17.
 */
public class GiornoAllenView {
    private JPanel mainPanel;
    private JPanel effettivoPanel;
    private JTable sedutaProgTable;
    private GiornoAllenForm giornoeffettivo;

    public GiornoAllenView() {
        String[] columnnames = {"Esercizio", "Quantita", "Unità di misura"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sedutaProgTable.setModel(new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addListenersAndshowButtons(ActionListener listener) {
        giornoeffettivo.addListenersAndShowButtons(listener);
    }

    public void addListenersForRemoveButtons(ActionListener listener){
        giornoeffettivo.addListenersForRemoveButtons(listener);
    }

    public void addListenerConfermaButton(ActionListener listener){
        giornoeffettivo.addListenerConfermaButton(listener);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        giornoeffettivo.addTableSelectionListener(listener);
    }

    public void visibilityConfermaAndAddButtons(boolean comb, boolean giornopieno, boolean completato){
        giornoeffettivo.visibilityConfermaAndAddButtons(comb, giornopieno, completato);
    }

    public void enableConfermaButton(boolean completato){
        giornoeffettivo.enableConfermaButton(completato);
    }

    public JTable getTable(GiornoEnum tipogiorno){
        if(tipogiorno==GiornoEnum.effettivo) {
            return giornoeffettivo.getSedutaEffTable();
        }
        else return sedutaProgTable;
    }

    public JButton getRemoveButton() { return giornoeffettivo.getRemoveSeduta(); }

    private void createUIComponents() {
        giornoeffettivo = new GiornoAllenForm("Cosa hai svolto");
        effettivoPanel = giornoeffettivo.getMainPanel();
    }
}
