package Controller;
import Helpers.Controller;
import Model.CiboModel;
import Object.Enum.GiornoEnum;
import View.Alimentazione.FormCiboEffettivo;
import View.Alimentazione.GiornoAlimForm;
import View.Alimentazione.GiornoAlimView;
import Object.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Lorenzo on 26/01/17.
 */
public abstract class BaseAlimController extends Controller {

    protected String nuovopasto;
    protected FormCiboEffettivo dialog;
    protected ResultSet alimenti;


    public class ListenersAndShowButtonsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setLocationRelativeTo(null);
            nuovopasto = e.getActionCommand();
            setPortataItems();
            dialog.setTitle("Inserisci alimento per " + nuovopasto);
            dialog.getButtonOK().setActionCommand(nuovopasto);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

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

    public void setPortataItems(){
        JComboBox portata = dialog.getPortata();
        dialog.getNomeAlimento().setEnabled(false);
        dialog.getNomeAlimento().setText("");
        dialog.getScrollPane().setVisible(false);
        if (nuovopasto == "colazione" || nuovopasto == "spuntino") {
            portata.setModel(new DefaultComboBoxModel(new String[]{"--scegli portata--","snack", "bevanda", "frutta"}));
        } else {
            portata.setModel(new DefaultComboBoxModel(new String[]{"--scegli portata--","primo", "secondo", "contorno", "dolce", "frutta", "bevanda"}));
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

    protected void showPasti(GiornoAlimObject giorno, GiornoAlimView giornoview) {
        ArrayList<JTable> tabelle = giornoview.getTables(giorno.getTipo());
        String[] tipipasto = new String[] {"colazione","pranzo","cena","spuntino"};
        for (int i=0; i<4; i++) {
            PastoObject pasto = giorno.getPastoByTipo(tipipasto[i]);
            Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
            DefaultTableModel model = (DefaultTableModel)tabelle.get(i).getModel();
            while (portateiterator.hasNext()) {
                PortataObject portata = portateiterator.next();
                String tipoportata = String.valueOf(portata.getTipo());
                String alimento = portata.getCibo().getNome();
                int quantita = portata.getQuantita();
                model.addRow(new Object[]{tipoportata, alimento, quantita});
            }
        }

    }

    protected void removePasti(GiornoAlimView giornoview, GiornoEnum tipogiorno){
        ArrayList<JTable> tabelle = giornoview.getTables(tipogiorno);
        for (int i=0; i<4; i++){
            DefaultTableModel model = (DefaultTableModel)tabelle.get(i).getModel();
            model.setRowCount(0);
        }
    }
}