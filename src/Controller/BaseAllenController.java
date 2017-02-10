package Controller;

import Helpers.Controller;
import Helpers.Item;
import Model.EsercizioModel;
import Object.Enum.GiornoEnum;
import View.Allenamento.FormEsercizioEffettivo;
import Object.GiornoAllenEffettivoObject;
import Object.GiornoAllenObject;
import Object.SedutaObject;
import Object.AttivitaObject;
import View.Allenamento.GiornoAllenView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Iterator;

/**
 * Created by lorenzobraconi on 06/02/17.
 */
public abstract class BaseAllenController extends Controller{

    protected GiornoAllenEffettivoObject giornoeffcorrente;
    protected FormEsercizioEffettivo dialog;
    protected ResultSet esercizi;
    protected int indexoggi;

    public class ListenersAndShowButtonsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setLocationRelativeTo(null);
            dialog.getIntensita().setModel(new DefaultComboBoxModel(new String[]{"--scegli esercizio--","leggero", "moderato", "intenso"}));
            dialog.setTitle("Inserisci esercizio svolto");
            //dialog.getButtonOK().setActionCommand(nuovopasto);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    public class SetIntensitaItemAction implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange()== ItemEvent.SELECTED) {
                showEsercizi();
                dialog.getScrollPane().setVisible(true);
                dialog.pack();
            }
        }
    }

    public class SearchKeyAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            filtraEsercizi();
        }
    }

    public class SetEsercizioListSelectionAction implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                Item esercizioscelto = (Item) dialog.getListaEsercizi().getSelectedValue();
                dialog.getNomeEsercizio().setText(esercizioscelto.getLabel());
                dialog.getMisuraLabel().setText(esercizioscelto.getValue());
            }
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaEsercizi().isSelectionEmpty() && !dialog.getQuantita().getText().equals("0"))
                dialog.getButtonOK().setEnabled(true);
            else dialog.getButtonOK().setEnabled(false);
        }
    }

    public class QuantitaKeyAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if (!checkIntero(dialog.getQuantita().getText())) {
                dialog.getQuantita().setText("");
            }
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaEsercizi().isSelectionEmpty() && !dialog.getQuantita().getText().equals("0"))
                dialog.getButtonOK().setEnabled(true);
            else dialog.getButtonOK().setEnabled(false);
        }
    }

    public void showEsercizi(){
        String intensitascelta = dialog.getIntensita().getSelectedItem().toString();
        JTextField nomeEsercizio = dialog.getNomeEsercizio();
        dialog.getIntensita().removeItem("--scegli esercizio--");
        nomeEsercizio.setEnabled(true);
        nomeEsercizio.setText("");
        EsercizioModel eserciziomodel = new EsercizioModel();
        esercizi = eserciziomodel.getEserciziByIntensita(intensitascelta);
        JList lista = dialog.getListaEsercizi();
        DefaultListModel listmodel = new DefaultListModel();
        try {
            while(esercizi.next()){
                listmodel.addElement(new Item(esercizi.getString("unita_misura"),esercizi.getString("tipologia")));
            }
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
        lista.setModel(listmodel);
    }

    public void filtraEsercizi(){
        String input = dialog.getNomeEsercizio().getText();
        DefaultListModel listafiltrata = new DefaultListModel();
        try {
            esercizi.beforeFirst();
            while(esercizi.next()){
                if(esercizi.getString("tipologia").contains(input)){
                    listafiltrata.addElement(new Item(esercizi.getString("unita_misura"),esercizi.getString("tipologia")));
                }
            }
            dialog.getListaEsercizi().setModel(listafiltrata);
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
    }

    protected void showSeduta(GiornoAllenObject giorno, GiornoAllenView giornoview) {
        JTable tabella = giornoview.getTable(giorno.getTipo());
        SedutaObject seduta = giorno.getSeduta();
        Iterator<AttivitaObject> attivitaiterator = seduta.getAttivita().iterator();
        DefaultTableModel model = (DefaultTableModel)tabella.getModel();
        while (attivitaiterator.hasNext()) {
            AttivitaObject attivita = attivitaiterator.next();
            String unitamisura = String.valueOf(attivita.getEsercizio().getUnita_misura());
            String esercizio = attivita.getEsercizio().getTipologia();
            double quantita = attivita.getQuantita();
            model.addRow(new Object[]{esercizio, quantita, unitamisura});
        }
    }

    protected void removeSeduta(GiornoAllenView giornoview, GiornoEnum tipogiorno){
        JTable tabella = giornoview.getTable(tipogiorno);
        DefaultTableModel model = (DefaultTableModel)tabella.getModel();
        model.setRowCount(0);
    }


    protected int calcolaCalorie(AttivitaObject attivita){
        return (int) attivita.getQuantita()*attivita.getEsercizio().getConsumo_calorico();
    }
}
