package View.Allenamento;

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

    public void addTableSelectionListener(ListSelectionListener listener) {
        giornoeffettivo.addTableSelectionListener(listener);
    }

    public JTable getTable(){
        return giornoeffettivo.getSedutaEffTable();
    }

    private void createUIComponents() {
        giornoeffettivo = new GiornoAllenForm("Cosa hai svolto");
        effettivoPanel = giornoeffettivo.getMainPanel();
    }
}
