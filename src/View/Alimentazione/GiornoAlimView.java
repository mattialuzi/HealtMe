package View.Alimentazione;

import Object.Enum.GiornoEnum;
import Object.Enum.StatusEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * La classe GiornoAlimView contiene attributi e metodi associati al file XML GiornoAlimView.form
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

    /**
     * Setta la visibilita della classe view GiornoALimForm
     */

    public void setaddButtonsVisible () {
       giornoeffettivo.setButtonsVisible();
    }

    /**
     * Metodo che prende le tabelle di un giorno
     * @param tipogiorno Tipo del giorno da cui recuperare le tabella
     * @return Tabelle dei pasti programmati
     */

    public ArrayList<JTable> getTables (GiornoEnum tipogiorno) {
        if (tipogiorno == GiornoEnum.effettivo) {
            return giornoeffettivo.getEffTables();
        } else {
            ArrayList<JTable> listatabelle = new ArrayList<JTable>(4);
            listatabelle.add(0, colazioneProgTable);
            listatabelle.add(1, pranzoProgTable);
            listatabelle.add(2, spuntiniProgTable);
            listatabelle.add(3, cenaProgTable);
            return listatabelle;
        }
    }

    /**
     * Setta le HashMap
     */

    public void setButtonFromTable(){
        giornoeffettivo.setButtonFromTable();
    }

    /**
     * Metodo che prende un elemento dell'HasHMap in base alla chiave
     * @param tablemodel Valore della chiave
     * @return Il bottone selezionato
     */

    public JButton getButtonFromTable(ListSelectionModel tablemodel){
        return giornoeffettivo.getButtonFromTable(tablemodel);
    }

    /**
     * Setta le HashMap
     */

    public void setTableFromButton(){
        giornoeffettivo.setTableFromButton();
    }

    /**
     * Metodo che prende un elemento dell'HasHMap in base alla chiave
     * @param nomebottone  Valore della chiave
     * @return La tabella selezionata
     */

    public JTable getTableFromButton(String nomebottone){
        return giornoeffettivo.getTableFromButton(nomebottone);
    }

    /**
     * Listener associati ad elementi di cui è composto il file XML GiornoAlimView.form
     */

    public void addListenersAndshowButtons(ActionListener listener) {
        giornoeffettivo.addListenersAndShowButtons(listener);
    }

    public void addListenersForRemoveButtons(ActionListener listener){
        giornoeffettivo.addListenersForRemoveButtons(listener);
    }

    public void addListenersConfermaButton(ActionListener listener){
        giornoeffettivo.addListenersConfermaButton(listener);
    }

    public void enableConfermaButton(StatusEnum status){
        giornoeffettivo.enableConfermaButton(status);
    }

    public void visibilityConfermaAndAddButtons(boolean comb, StatusEnum status){
        giornoeffettivo.visibilityConfermaAndAddButtons(comb, status);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        giornoeffettivo.addTableSelectionListener(listener);
    }

    /**
     * Metodo che crea componenti dell'User Interface
     */

    private void createUIComponents() {
        giornoeffettivo = new GiornoAlimForm("Cosa hai mangiato");
        effettivopanel = giornoeffettivo.getMainPanel();
    }
}


