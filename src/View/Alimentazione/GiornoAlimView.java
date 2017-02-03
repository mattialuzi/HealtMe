package View.Alimentazione;

import Object.Enum.GiornoEnum;
import Object.Enum.StatusEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 12/01/17.
 */
public class GiornoAlimView {
    private JPanel mainPanel;
    private JTable spuntiniProgTable;
    private JTable cenaProgTable;
    private JTable pranzoProgTable;
    private JTable colazioneProgTable;
    private JPanel effettivopanel;
    private GiornoAlimForm giornoeffettivo;

    public GiornoAlimView() {
        String[] columnnames = {"Portata", "Alimento", "Quantita"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        colazioneProgTable.setModel(new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        pranzoProgTable.setModel( new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        cenaProgTable.setModel( new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        spuntiniProgTable.setModel( new DefaultTableModel(columnnames, 0) {
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

    public void addListenersAndShowConfermaButton(ActionListener listener){
        giornoeffettivo.addListenersAndShowConfermaButton(listener);
    }

    public void enableConfermaButton(StatusEnum status){
        giornoeffettivo.enableConfermaButton(status);
    }

    public void visibilityConfermaAndAddButtons(boolean comb){
        giornoeffettivo.visibilityConfermaAndAddButtons(comb);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        giornoeffettivo.addTableSelectionListener(listener);
    }

    public void setaddButtonsVisible () {
       giornoeffettivo.setButtonsVisible();
    }

    public ArrayList<JTable> getTables (GiornoEnum tipogiorno) {
        if (tipogiorno == GiornoEnum.effettivo) {
            return giornoeffettivo.getEffTables();
        } else {
            ArrayList<JTable> listatabelle = new ArrayList<JTable>(4);
            listatabelle.add(0, colazioneProgTable);
            listatabelle.add(1, pranzoProgTable);
            listatabelle.add(2, cenaProgTable);
            listatabelle.add(3, spuntiniProgTable);
            return listatabelle;
        }
    }

    public void setButtonFromTable(){
        giornoeffettivo.setButtonFromTable();
    }

    public JButton getButtonFromTable(ListSelectionModel tablemodel){
        return giornoeffettivo.getButtonFromTable(tablemodel);
    }

    public void setTableFromButton(){
        giornoeffettivo.setTableFromButton();
    }

    public JTable getTableFromButton(String nomebottone){
        return giornoeffettivo.getTableFromButton(nomebottone);
    }

    private void createUIComponents() {
        giornoeffettivo = new GiornoAlimForm("Cosa hai mangiato");
        effettivopanel = giornoeffettivo.getMainPanel();
    }
}


