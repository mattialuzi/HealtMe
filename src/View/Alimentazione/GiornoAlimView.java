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
    private JTable spuntiniProgTable;
    private JTable cenaProgTable;
    private JTable pranzoProgTable;
    private JTable colazioneProgTable;
    private JPanel effettivopanel;
    private GiornoAlimForm giornoeffettivo;

    public GiornoAlimView() {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addListenersAndshowButtons(ActionListener listener) {
        giornoeffettivo.addListenersAndshowButtons(listener);
    }

    public void addListenersForRemoveButtons(ActionListener listener){
        giornoeffettivo.addListenersForRemoveButtons(listener);
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


