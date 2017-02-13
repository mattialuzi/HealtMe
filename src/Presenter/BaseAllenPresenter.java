package Presenter;

import Helpers.Presenter;
import Helpers.Item;
import DAO.EsercizioDAO;
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
 *  La classe BaseAllenPresenter contiene attributi e metodi comuni ad AllenamentoPresenter ed ProgAllenPresenter
 */

public abstract class BaseAllenPresenter extends Presenter {

    protected GiornoAllenEffettivoObject giornoeffcorrente;
    protected FormEsercizioEffettivo dialog;
    protected ResultSet esercizi;
    protected int indexoggi;

    /**
     * Metodo che aggiunge il listener alla form di inserimento di un esercizio effettivo
     */

    public class ListenersAndShowButtonsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setLocationRelativeTo(null);
            dialog.getIntensita().setModel(new DefaultComboBoxModel(new String[]{"--scegli esercizio--","leggero", "moderato", "intenso"}));
            dialog.setTitle("Inserisci esercizio svolto");
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    /**
     * Metodo che aggiunge il listener all'inserimento dell'intensità della form di inserimento di un cibo effettivo
     */

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

    /**
     * Metodo che aggiunge il listener all'inserimento di un carattere della form di inserimento di un esercizio effettivo
     */

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

    /**
     * Metodo che aggiunge il listener alla selezione di un esercizio della form di inserimento di un esercizio effettivo
     */

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

    /**
     * Metodo che aggiunge il listener all'inserimento della quantità di un esercizio della form di inserimento di un esercizio effettivo
     */

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

    /**
     *  Metodo che permette di mostrare gli esercizi nella form di inserimento di un esercizio effettivo
     */

    public void showEsercizi(){
        String intensitascelta = dialog.getIntensita().getSelectedItem().toString();
        JTextField nomeEsercizio = dialog.getNomeEsercizio();
        dialog.getIntensita().removeItem("--scegli esercizio--");
        nomeEsercizio.setEnabled(true);
        nomeEsercizio.setText("");
        EsercizioDAO esercizioDAO = new EsercizioDAO();
        esercizi = esercizioDAO.getEserciziByIntensita(intensitascelta);
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

    /**
     * Metodo che permette di filtrare gli esercizi nella form di inserimento di un esercizio effettivo
     */

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

    /**
     * Metodo che permette di mostrare tutte le attività di tutti le sedute di un determinato giorno
     * @param giorno Variabile di tipo GiornoAllenObject di cui si vuole mostrare tutti le attività di tutti le sedute
     * @param giornoview Variabile della classe view GiornoAllenView su di cui si vogliono mostrare le informazioni
     */

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

    /**
     * Metodo che permette di rimuovere  tutte le attività della tabella seduta
     * @param giornoview Variabile della classe view GiornoAllenView da cui prendere la tabella
     * @param tipogiorno Tipo del giorno di cui rimuovere le attività
     */

    protected void removeSeduta(GiornoAllenView giornoview, GiornoEnum tipogiorno){
        JTable tabella = giornoview.getTable(tipogiorno);
        DefaultTableModel model = (DefaultTableModel)tabella.getModel();
        model.setRowCount(0);
    }

    /**
     * Metodo che permette a di calcolare le calorie a partire da una variabile AttivitàObject
     * @param attivita Variabile di tipo AttivitaObject di cui si vuole calcolare le calorie
     * @return Il valore delle calorie
     */

    protected int calcolaCalorie(AttivitaObject attivita){
        return (int) attivita.getQuantita()*attivita.getEsercizio().getConsumo_calorico();
    }
}
