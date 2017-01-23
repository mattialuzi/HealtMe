package View.Alimentazione;

import Object.Enum.GiornoEnum;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private JTable colazioneProgTable;
    private JScrollPane Cena;

    public GiornoAlimView() {
    }

    public void addListnersAndshowButtons(ActionListener listener) {
        addColazione.addActionListener(listener);
        addPranzo.addActionListener(listener);
        addCena.addActionListener(listener);
        addSpuntino.addActionListener(listener);
        setaddButtonsVisible();
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

}

