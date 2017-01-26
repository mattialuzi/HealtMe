package Controller;
import Helpers.Controller;
import Model.CiboModel;
import View.Alimentazione.FormCiboEffettivo;
import View.Alimentazione.GiornoAlimForm;
import View.Alimentazione.GiornoAlimView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

/**
 * Created by Lorenzo on 26/01/17.
 */
public abstract class BaseAlimController extends Controller {

    protected String nuovopasto;
    protected FormCiboEffettivo dialog;


    public class SetPortataItemAction implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange()== ItemEvent.SELECTED) {
                showAlimenti();
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
            filtraAlimenti();
        }
    }

    public class SetCiboListSelectionAction implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                dialog.getNomeAlimento().setText(dialog.getListaAlimenti().getSelectedValue().toString());
            }
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaAlimenti().isSelectionEmpty())
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
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaAlimenti().isSelectionEmpty())
                dialog.getButtonOK().setEnabled(true);
            else dialog.getButtonOK().setEnabled(false);
        }
    }

    public void showAlimenti(){
        String portatascelta = dialog.getPortata().getSelectedItem().toString();
        JTextField nomeAlimento = dialog.getNomeAlimento();
        dialog.getPortata().removeItem("--scegli portata--");
        if (portatascelta.equals("bevanda"))
            dialog.getMisuraLabel().setText("ml");
        else
            dialog.getMisuraLabel().setText("grammi");
        nomeAlimento.setEnabled(true);
        nomeAlimento.setText("");
        CiboModel cibomodel = new CiboModel();
        alimenti = cibomodel.getCibiByPortata(portatascelta);
        JList lista = dialog.getListaAlimenti();
        DefaultListModel listmodel = new DefaultListModel();
        try {
            while(alimenti.next()){
                listmodel.addElement(alimenti.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
        lista.setModel(listmodel);
    }

    public void filtraAlimenti(){
        String input = dialog.getNomeAlimento().getText();
        DefaultListModel listafiltrata = new DefaultListModel();
        try {
            alimenti.beforeFirst();
            while(alimenti.next()){
                if(alimenti.getString("nome").contains(input)){
                    listafiltrata.addElement(alimenti.getString("nome"));
                }
            }
            dialog.getListaAlimenti().setModel(listafiltrata);
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
    }
}