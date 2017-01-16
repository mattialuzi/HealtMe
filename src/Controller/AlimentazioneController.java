package Controller;

import Helpers.Controller;
import Model.CiboModel;
import View.Alimentazione.*;
import View.Menu;
import Object.CiboObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneController extends Controller {

    private AlimentazioneView alimentazione;
    private FormCiboEffettivo dialog;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private ResultSet alimenti;
    private String pasto;

    public AlimentazioneController(Menu menu) {

        this.alimentazione = menu.getAlimentazioneview();
        variablePanel = alimentazione.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        showIndex();
        NewCiboView newcibo = alimentazione.getNewcibo();
        IndexAlimentazioneView indexalimentazione = alimentazione.getIndexalimentazione();
        LocalDate date = LocalDate.now();
        DayOfWeek giorno = date.getDayOfWeek();
        HashMap<DayOfWeek,GiornoAlimView> giorni = indexalimentazione.getGiorni();
        GiornoAlimView giornoattuale = (GiornoAlimView) giorni.get(giorno);
        dialog = new FormCiboEffettivo();

        menu.addNewProgAlimButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAlimPanel());
                cardLayout.show(variablePanel, "NewProgAlimView");
            }
        });

        menu.addNewCiboButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAlimPanel());
                cardLayout.show(variablePanel, "NewCiboView");
            }
        });

        newcibo.addAzzeraCampiButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newcibo.azzeraCampi();
            }
        });

        newcibo.addAggiungiAlimentoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newcibo.isValid()) {
                    CiboObject nuovocibo = newcibo.getNuovoCibo();
                    CiboModel cibomodel = new CiboModel();
                    boolean validator = cibomodel.findCiboByName(nuovocibo.getNome());
                    if (validator) {
                        JOptionPane.showMessageDialog(null, "Cibo già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        cibomodel.inserisciCibo(nuovocibo);
                        JOptionPane.showMessageDialog(null, "Cibo registrato con successo", "Operazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        newcibo.azzeraCampi();
                        cardLayout.show(variablePanel, "IndexAlimentazioneView");
                    }
                }
            }
            });

        giornoattuale.addListnersAndshowButtons(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                pasto = e.getActionCommand();
                setPortataItems();
                dialog.setTitle("Inserisci alimento a " +pasto);
                dialog.setVisible(true);
            }
        });


        dialog.addSetPortataItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(!dialog.getQuantita().getText().equals("")){
                    dialog.getButtonOK().setEnabled(true);
                }
                if(e.getStateChange()==e.SELECTED)
                showAlimenti();
            }
        });

        dialog.addSearchKeyListener(new KeyListener() {
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
        });

        dialog.addSetCiboListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting())
                dialog.getNomeAlimento().setText(dialog.getListaAlimenti().getSelectedValue().toString());
            }
        });

        dialog.addQuantitaKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyPressed(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {
                if (!checkIntero(dialog.getQuantita().getText()))
                    dialog.getQuantita().setText("");
                if(!dialog.getListaAlimenti().isSelectionEmpty())
                    dialog.getButtonOK().setEnabled(true);
            }
        });

        dialog.addPortataEffettivaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiungiPortataEffettiva();
            }
        });

    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }

    public void setPortataItems(){
        JComboBox portata =dialog.getPortata();
        dialog.getNomeAlimento().setEnabled(false);
        dialog.getNomeAlimento().setText("");
        dialog.getScrollPane().setVisible(false);
        if (pasto == "colazione" || pasto == "spuntino") {
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
        dialog.getScrollPane().setVisible(true);
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

    public void aggiungiPortataEffettiva(){
        String portata = dialog.getPortata().getSelectedItem().toString();
        String alimento = dialog.getNomeAlimento().getText();
        Integer quantita = Integer.parseInt(dialog.getQuantita().getText());
    }
}
