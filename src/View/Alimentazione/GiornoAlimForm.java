package View.Alimentazione;

import Object.Enum.StatusEnum;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe GiornoAlimForm contiene attributi e metodi associati al file XML GiornoAlimForm.form
 */

public class GiornoAlimForm {
    private JPanel mainPanel;
    private JButton addSpuntino;
    private JButton removeSpuntino;
    private JTable spuntiniEffTable;
    private JButton addColazione;
    private JButton removeColazione;
    private JTable colazioneEffTable;
    private JButton addCena;
    private JButton removeCena;
    private JTable cenaEffTable;
    private JButton addPranzo;
    private JButton removePranzo;
    private JTable pranzoEffTable;
    private JLabel Titlelabel;
    private JButton confermaColazione;
    private JButton confermaPranzo;
    private JButton confermaCena;
    private JButton confermaSpuntino;
    private HashMap<ListSelectionModel, JButton> tabelle;
    private HashMap<String, JTable> bottoni;

    public GiornoAlimForm(String Title) {

        Titlelabel.setText(Title);
        String[] columnnames = {"Portata", "Alimento", "Quantita"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        colazioneEffTable.setModel(new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        pranzoEffTable.setModel( new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        cenaEffTable.setModel( new DefaultTableModel(columnnames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        spuntiniEffTable.setModel( new DefaultTableModel(columnnames, 0) {
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
     * Metodo che setta la visibilità dei bottoni per aggiungere una nuova portata nei pasti effettivi
     */

    public void setButtonsVisible () {
        addColazione.setVisible(true);
        removeColazione.setVisible(true);
        addPranzo.setVisible(true);
        removePranzo.setVisible(true);
        addCena.setVisible(true);
        removeCena.setVisible(true);
        addSpuntino.setVisible(true);
        removeSpuntino.setVisible(true);
    }

    /**
     * Metodo che mostra la visibilità dei componenti dell'interfaccia grafice in base allo status
     * @param status Valore enumerativo di status su cui basare la visibilità
     */

    public void enableConfermaButton(StatusEnum status){
        if(status.equals(StatusEnum.colazione)) {
            confermaColazione.setEnabled(true);
            addPranzo.setEnabled(false);
            addCena.setEnabled(false);
            addSpuntino.setEnabled(false);
        } else if(status.equals(StatusEnum.pranzo)) {
            confermaColazione.setEnabled(false);
            confermaPranzo.setEnabled(true);
            colazioneEffTable.setEnabled(false);
            addColazione.setEnabled(false);
            addPranzo.setEnabled(true);
            addCena.setEnabled(false);
            addSpuntino.setEnabled(false);
        } else if(status.equals(StatusEnum.spuntino)) {
            confermaPranzo.setEnabled(false);
            confermaSpuntino.setEnabled(true);
            colazioneEffTable.setEnabled(false);
            pranzoEffTable.setEnabled(false);
            addColazione.setEnabled(false);
            addPranzo.setEnabled(false);
            addSpuntino.setEnabled(true);
            addCena.setEnabled(false);
        } else if(status.equals(StatusEnum.cena)) {
            confermaSpuntino.setEnabled(false);
            confermaCena.setEnabled(true);
            colazioneEffTable.setEnabled(false);
            pranzoEffTable.setEnabled(false);
            spuntiniEffTable.setEnabled(false);
            addColazione.setEnabled(false);
            addPranzo.setEnabled(false);
            addSpuntino.setEnabled(false);
            addCena.setEnabled(true);
        } else {
            confermaCena.setEnabled(false);
            colazioneEffTable.setEnabled(false);
            pranzoEffTable.setEnabled(false);
            spuntiniEffTable.setEnabled(false);
            cenaEffTable.setEnabled(false);
            addColazione.setEnabled(false);
            addPranzo.setEnabled(false);
            addSpuntino.setEnabled(false);
            addCena.setEnabled(false);
        }
    }

    /**
     * Metodo che setta la visibilità del bottone conferma e i bottoni di aggiunta di una portata
     * @param comb Variabile booleana che indica se il programma alimentare è combinato
     * @param status Valore enumerativo di status su cui basare la visibilità
     */


    public void visibilityConfermaAndAddButtons(boolean comb, StatusEnum status){
        confermaColazione.setVisible(comb);
        confermaPranzo.setVisible(comb);
        confermaSpuntino.setVisible(comb);
        confermaCena.setVisible(comb);
        if(comb) {
            enableConfermaButton(status);
        }
        else{
            addColazione.setEnabled(true);
            addPranzo.setEnabled(true);
            addSpuntino.setEnabled(true);
            addCena.setEnabled(true);
        }
    }

    /**
     * Metodo che aggiunge le tabelle dei pasti effettivi ad un arrylist di JTable
     * @return Un ArrayList di Jtable
     */

    public ArrayList<JTable> getEffTables () {
        ArrayList<JTable> listatabelle = new ArrayList<JTable>(4);
        listatabelle.add(0, colazioneEffTable);
        listatabelle.add(1, pranzoEffTable);
        listatabelle.add(2, spuntiniEffTable);
        listatabelle.add(3, cenaEffTable);
        return listatabelle;
    }

    /**
     * Metodo che riepie un HashMap con dei componenti la cui la chiave è il model della tabella ed il valore è il bottone di di rimuovi
     */

    public void setButtonFromTable(){
        this.tabelle = new HashMap<ListSelectionModel, JButton>();
        tabelle.put(colazioneEffTable.getSelectionModel(), removeColazione);
        tabelle.put(pranzoEffTable.getSelectionModel(), removePranzo);
        tabelle.put(cenaEffTable.getSelectionModel(), removeCena);
        tabelle.put(spuntiniEffTable.getSelectionModel(), removeSpuntino);
    }

    /**
     * Ottiene il valore di un componente dell'HasHMap in base alla chiave
     * @param tablemodel Variabile di tipo ListSelectionModel
     * @return Il bottone rimuovi della tabella ottenuta
     */

    public JButton getButtonFromTable(ListSelectionModel tablemodel){
        return tabelle.get(tablemodel);
    }

    /**
     * Metodo che riepie un HashMap con dei componenti la cui la chiave è l'action command del bottone ed il valore è una tabella
     */

    public void setTableFromButton(){
        this.bottoni = new HashMap<String, JTable>();
        bottoni.put(removeColazione.getActionCommand(), colazioneEffTable);
        bottoni.put(removePranzo.getActionCommand(), pranzoEffTable);
        bottoni.put(removeCena.getActionCommand(), cenaEffTable);
        bottoni.put(removeSpuntino.getActionCommand(), spuntiniEffTable);
    }

    /**
     * Ottiene il valore di un componente dell'HasHMap in base alla chiave
     * @param nomebottone Stringa del bottone
     * @return Tabella selezionata
     */

    public JTable getTableFromButton(String nomebottone){
        return bottoni.get(nomebottone);
    }

    /** Listener associati ad elementi di cui è composto il file XML GiornoAlimForm.form  */

    public void addListenersAndShowButtons(ActionListener listener) {
        addColazione.addActionListener(listener);
        addPranzo.addActionListener(listener);
        addCena.addActionListener(listener);
        addSpuntino.addActionListener(listener);
        setButtonsVisible();
    }

    public void addListenersForRemoveButtons(ActionListener listener){
        removeColazione.addActionListener(listener);
        removePranzo.addActionListener(listener);
        removeCena.addActionListener(listener);
        removeSpuntino.addActionListener(listener);
    }

    public void addListenersConfermaButton(ActionListener listener){ // Funzione che aggiunge i listener ai bottini di Conferma e li rende visibili
        confermaColazione.addActionListener(listener);
        confermaPranzo.addActionListener(listener);
        confermaCena.addActionListener(listener);
        confermaSpuntino.addActionListener(listener);
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        colazioneEffTable.getSelectionModel().addListSelectionListener(listener);
        pranzoEffTable.getSelectionModel().addListSelectionListener(listener);
        cenaEffTable.getSelectionModel().addListSelectionListener(listener);
        spuntiniEffTable.getSelectionModel().addListSelectionListener(listener);
    }
}

