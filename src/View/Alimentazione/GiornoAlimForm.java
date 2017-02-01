package View.Alimentazione;

import Object.Enum.GiornoEnum;
import Object.Enum.StatusEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mattia on 25/01/2017.
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
    //private HashMap<StatusEnum, JButton> statusmap;

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

    public void enableConfermaButton(StatusEnum status){
        if(status.equals(StatusEnum.colazione)) {
            confermaColazione.setEnabled(true);
        } else if(status.equals(StatusEnum.pranzo)) {
            confermaColazione.setEnabled(false);
            confermaPranzo.setEnabled(true);
            colazioneEffTable.setEnabled(false);
            addColazione.setEnabled(false);
        } else if(status.equals(StatusEnum.spuntino)) {
            confermaPranzo.setEnabled(false);
            confermaSpuntino.setEnabled(true);
            colazioneEffTable.setEnabled(false);
            pranzoEffTable.setEnabled(false);
            addColazione.setEnabled(false);
            addPranzo.setEnabled(false);
        } else if(status.equals(StatusEnum.cena)) {
            confermaSpuntino.setEnabled(false);
            confermaCena.setEnabled(true);
            colazioneEffTable.setEnabled(false);
            pranzoEffTable.setEnabled(false);
            spuntiniEffTable.setEnabled(false);
            addColazione.setEnabled(false);
            addPranzo.setEnabled(false);
            addSpuntino.setEnabled(false);
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

    public void setConfermaButtonVisible(){
        confermaColazione.setVisible(true);
        confermaCena.setVisible(true);
        confermaPranzo.setVisible(true);
        confermaSpuntino.setVisible(true);
    }

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

    public void addListenersAndShowConfermaButton(ActionListener listener){
        confermaColazione.addActionListener(listener);
        confermaPranzo.addActionListener(listener);
        confermaCena.addActionListener(listener);
        confermaSpuntino.addActionListener(listener);
        setConfermaButtonVisible();
    }

    public void addTableSelectionListener(ListSelectionListener listener) {
        colazioneEffTable.getSelectionModel().addListSelectionListener(listener);
        pranzoEffTable.getSelectionModel().addListSelectionListener(listener);
        cenaEffTable.getSelectionModel().addListSelectionListener(listener);
        spuntiniEffTable.getSelectionModel().addListSelectionListener(listener);
    }

    public ArrayList<JTable> getEffTables () {
        ArrayList<JTable> listatabelle = new ArrayList<JTable>(4);
            listatabelle.add(0, colazioneEffTable);
            listatabelle.add(1, pranzoEffTable);
            listatabelle.add(2, cenaEffTable);
            listatabelle.add(3, spuntiniEffTable);
        return listatabelle;
    }

    public void setButtonFromTable(){
        this.tabelle = new HashMap<ListSelectionModel, JButton>();
        tabelle.put(colazioneEffTable.getSelectionModel(), removeColazione);
        tabelle.put(pranzoEffTable.getSelectionModel(), removePranzo);
        tabelle.put(cenaEffTable.getSelectionModel(), removeCena);
        tabelle.put(spuntiniEffTable.getSelectionModel(), removeSpuntino);
    }

    public JButton getButtonFromTable(ListSelectionModel tablemodel){
        return tabelle.get(tablemodel);
    }

    public void setTableFromButton(){
        this.bottoni = new HashMap<String, JTable>();
        bottoni.put(removeColazione.getActionCommand(), colazioneEffTable);
        bottoni.put(removePranzo.getActionCommand(), pranzoEffTable);
        bottoni.put(removeCena.getActionCommand(), cenaEffTable);
        bottoni.put(removeSpuntino.getActionCommand(), spuntiniEffTable);
    }

    public JTable getTableFromButton(String nomebottone){
        return bottoni.get(nomebottone);
    }

    /*public void setConfermaFromStatus(){
        this.statusmap = new HashMap<StatusEnum, JButton>();
        statusmap.put(StatusEnum.colazione, confermaColazione);
        statusmap.put(StatusEnum.pranzo, confermaPranzo);
        statusmap.put(StatusEnum.spuntino, confermaSpuntino);
        statusmap.put(StatusEnum.cena, confermaCena);
    }*/
}

