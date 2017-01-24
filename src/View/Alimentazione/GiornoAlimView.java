package View.Alimentazione;

import Object.Enum.GiornoEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private JTable pranzoEffTable;
    private JTable cenaEffTable;
    private JTable colazioneEffTable;
    private JTable spuntiniEffTable;
    private JTable spuntiniProgTable;
    private JTable cenaProgTable;
    private JTable pranzoProgTable;
    private JTable table1;
    private JTable colazioneProgTable;
    private JScrollPane Cena;
    private HashMap<ListSelectionModel, JButton> tabelle;
    private HashMap<String, JTable> bottoni;

    public GiornoAlimView() {
    }

    public void addListnersAndshowButtons(ActionListener listener) {
        addColazione.addActionListener(listener);
        addPranzo.addActionListener(listener);
        addCena.addActionListener(listener);
        addSpuntino.addActionListener(listener);
        setaddButtonsVisible();
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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setaddButtonsVisible () {
        addColazione.setVisible(true);
        removeColazione.setVisible(true);
        addPranzo.setVisible(true);
        removePranzo.setVisible(true);
        addCena.setVisible(true);
        removeCena.setVisible(true);
        addSpuntino.setVisible(true);
        removeSpuntino.setVisible(true);
    }

    public ArrayList<JTable> getTables (GiornoEnum tipogiorno) {
        ArrayList<JTable> listatabelle = new ArrayList<JTable>(4);
        if (tipogiorno == GiornoEnum.effettivo) {
            listatabelle.add(0, colazioneEffTable);
            listatabelle.add(1, pranzoEffTable);
            listatabelle.add(2, cenaEffTable);
            listatabelle.add(3, spuntiniEffTable);
        } else {
            listatabelle.add(0, colazioneProgTable);
            listatabelle.add(1, pranzoProgTable);
            listatabelle.add(2, cenaProgTable);
            listatabelle.add(3, spuntiniProgTable);
        }
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

