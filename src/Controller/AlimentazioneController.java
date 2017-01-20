package Controller;

import Helpers.Controller;
import Model.CiboModel;
import Model.GiornoAlimModel;
import Model.PastoModel;
import Model.PortataModel;
import Object.Enum.*;
import View.Alimentazione.*;
import View.Menu;
import Object.CiboObject;
import Object.UtenteObject;
import Object.GiornoAlimEffettivoObject;
import Object.PastoObject;
import Object.PortataObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
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
    private UtenteObject utente;
    private GiornoAlimEffettivoObject giornocorrente;

    public AlimentazioneController(Menu menu,UtenteObject utente) {

        this.alimentazione = menu.getAlimentazioneview();
        this.utente = utente;
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
        creaGiornoAlimEff(utente.getUsername(),date);

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
                if(e.getStateChange()==e.SELECTED) {
                    showAlimenti();
                }
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
                if(e.getValueIsAdjusting()) {
                    dialog.getNomeAlimento().setText(dialog.getListaAlimenti().getSelectedValue().toString());
                }
                if(!dialog.getQuantita().getText().equals("") && !dialog.getListaAlimenti().isSelectionEmpty())
                    dialog.getButtonOK().setEnabled(true);
                else dialog.getButtonOK().setEnabled(false);
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
                if (!checkIntero(dialog.getQuantita().getText())) {
                    dialog.getQuantita().setText("");
                }
                if(!dialog.getQuantita().getText().equals("") && !dialog.getListaAlimenti().isSelectionEmpty())
                    dialog.getButtonOK().setEnabled(true);
                else dialog.getButtonOK().setEnabled(false);
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

    public void creaGiornoAlimEff(String username,LocalDate data){
        GiornoAlimModel giorno = new GiornoAlimModel();
        giornocorrente = giorno.getGiornoAlimEffettivo(username, data);
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
        dialog.getScrollPane().setVisible(true);
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
        //GiornoAlimModel giorno = new GiornoAlimModel();
        String portata = dialog.getPortata().getSelectedItem().toString();
        String alimento = dialog.getNomeAlimento().getText();
        Integer quantita = Integer.parseInt(dialog.getQuantita().getText());
        PastoObject nuovopasto = giornocorrente.getPastoByTipo(pasto);
        if(nuovopasto.getId() == 0) {
            nuovopasto.setTipo(PastoEnum.valueOf(pasto));
            PastoModel pastomodel = new PastoModel();
            pastomodel.inserisciPasto(nuovopasto);
        }
        PortataObject nuovaportata = nuovopasto.
        nuovaportata.setId_pasto(nuovoid);
        CiboModel cibomodel = new CiboModel();
        CiboObject cibo = new CiboObject();
        ResultSet ciborecuperato = cibomodel.getCiboByName(alimento);
        try{
            while(ciborecuperato.next()){
                    cibo.setNome(ciborecuperato.getString("nome"));
                    cibo.setGruppo(GruppoEnum.valueOf(ciborecuperato.getString("gruppo")));
                    cibo.setKilocal(ciborecuperato.getInt("kilocal"));
                    cibo.setAllergia(AllergiaEnum.valueOf(ciborecuperato.getString("allergia")));
                    cibo.setPortata(PortataEnum.valueOf(ciborecuperato.getString("portata")));
                    cibo.setCompatibilita(CompatibilitaEnum.valueOf(ciborecuperato.getString("compatibilita")));
                }
            } catch (Exception e){
                System.out.println("C'è un errore:" + e);
            }
            nuovaportata.setCibo(); */
        }

    }
}
