package Controller;

import Helpers.Controller;
import Model.EsercizioModel;
import View.Allenamento.FormEsercizioEffettivo;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.sql.ResultSet;

/**
 * Created by lorenzobraconi on 06/02/17.
 */
public abstract class BaseAllenController extends Controller{

    protected FormEsercizioEffettivo dialog;
    protected ResultSet esercizi;

    public class ListenersAndShowButtonsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setLocationRelativeTo(null);
            dialog.setTitle("Inserisci esercizio svolto");
            //dialog.getButtonOK().setActionCommand(nuovopasto);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    public class SetUnitaItemAction implements ItemListener {
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
                dialog.getNomeEsercizio().setText(dialog.getListaEsercizi().getSelectedValue().toString());
            }
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaEsercizi().isSelectionEmpty())
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
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaEsercizi().isSelectionEmpty())
                dialog.getButtonOK().setEnabled(true);
            else dialog.getButtonOK().setEnabled(false);
        }
    }

    public void showEsercizi(){
        String unitascelta = dialog.getUnitamisura().getSelectedItem().toString();
        JTextField nomeEsercizio = dialog.getNomeEsercizio();
        dialog.getUnitamisura().removeItem("--scegli esercizio per svolgimento--");
        if (unitascelta.equals("minuti"))
            dialog.getMisuraLabel().setText("min");
        else if(unitascelta.equals("kilometri"))
            dialog.getMisuraLabel().setText("km");
        else dialog.getMisuraLabel().setText("n°rip.");
        nomeEsercizio.setEnabled(true);
        nomeEsercizio.setText("");
        EsercizioModel eserciziomodel = new EsercizioModel();
        esercizi = eserciziomodel.getEserciziByUnita(unitascelta);
        JList lista = dialog.getListaEsercizi();
        DefaultListModel listmodel = new DefaultListModel();
        try {
            while(esercizi.next()){
                listmodel.addElement(esercizi.getString("tipologia"));
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
                    listafiltrata.addElement(esercizi.getString("tipologia"));
                }
            }
            dialog.getListaEsercizi().setModel(listafiltrata);
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
    }
}
