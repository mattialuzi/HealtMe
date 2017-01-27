package View.Alimentazione;

import Object.Enum.GiornoEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
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
    private HashMap<ListSelectionModel, JButton> tabelle;
    private HashMap<String, JTable> bottoni;

    public GiornoAlimForm(String Title) {
        Titlelabel.setText(Title);
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

    public void addListenersAndshowButtons(ActionListener listener) {
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
}

