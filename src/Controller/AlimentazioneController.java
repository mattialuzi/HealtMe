package Controller;

import Helpers.Controller;
import Model.CiboModel;
import Model.GiornoAlimModel;
import Model.PastoModel;
import Model.PortataModel;
import Object.Enum.*;
import View.Alimentazione.*;
import View.Menu;
import Object.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneController extends Controller {

    private AlimentazioneView alimentazione;
    private FormCiboEffettivo dialog;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private ResultSet alimenti;
    private String nuovopasto;
    private UtenteObject utente;
    private GiornoAlimEffettivoObject giornocorrente;
    private GiornoAlimView giornocorrenteview;
    private IndexAlimentazioneView indexalimentazione;

    public AlimentazioneController(Menu menu,UtenteObject utente) {

        this.alimentazione = menu.getAlimentazioneview();
        this.utente = utente;
        variablePanel = alimentazione.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        NewCiboView newcibo = alimentazione.getNewcibo();
        indexalimentazione = alimentazione.getIndexalimentazione();
        setGiorni();
        dialog = new FormCiboEffettivo();
        showIndex();

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

        giornocorrenteview.addListnersAndshowButtons(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setLocationRelativeTo(null);
                nuovopasto = e.getActionCommand();
                setPortataItems();
                dialog.setTitle("Inserisci alimento a " +nuovopasto);
                dialog.pack();
                dialog.setVisible(true);
            }
        });

        giornocorrenteview.addTableSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JButton bottonescelto = giornocorrenteview.getButtonFromTable((ListSelectionModel)e.getSource());
                bottonescelto.setEnabled(true);
            }
        });

        giornocorrenteview.addListenersForRemoveButtons(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tabellascelta = giornocorrenteview.getTableFromButton(e.getActionCommand());
                removePortata(tabellascelta, e.getActionCommand());
                ((DefaultTableModel)tabellascelta.getModel()).removeRow(tabellascelta.getSelectedRow());
            }
        });

        dialog.addSetPortataItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()== ItemEvent.SELECTED) {
                    showAlimenti();
                    dialog.getScrollPane().setVisible(true);
                    dialog.pack();
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
                dialog.onCancel();
            }
        });

    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }

    public void setGiorni() {
        LocalDate data = LocalDate.now();
        DayOfWeek giornosettimana = data.getDayOfWeek();
        giornocorrenteview = indexalimentazione.getGiorni(giornosettimana);
        GiornoAlimModel giornomodel = new GiornoAlimModel();
        giornocorrente = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
        giornocorrenteview.setButtonFromTable();
        giornocorrenteview.setTableFromButton();
        showPasti(giornocorrente, giornocorrenteview);
        indexalimentazione.setTodayTab(giornosettimana);
        data = data.minusDays(1);
        while (!data.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            DayOfWeek giornoprimasettimana = data.getDayOfWeek();
            GiornoAlimView giornoprimaview = indexalimentazione.getGiorni(giornoprimasettimana);
            GiornoAlimObject giornoprima = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
            showPasti(giornoprima,giornoprimaview);
            data = data.minusDays(1);
        }
        data = LocalDate.now().plusDays(1);
        while (!data.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            DayOfWeek giornodoposettimana = data.getDayOfWeek();
            GiornoAlimView giornodopoview = indexalimentazione.getGiorni(giornodoposettimana);
            GiornoAlimObject giornodopo = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
            showPasti(giornodopo,giornodopoview);
            data = data.plusDays(1);
        }
    }

    private void showPasti(GiornoAlimObject giorno,GiornoAlimView giornoview) {
        ArrayList<JTable> tabelle = giornoview.getTables(giorno.getTipo());
        String[] tipipasto = new String[] {"colazione","pranzo","cena","spuntino"};
        for (int i=0; i<4; i++) {
            PastoObject pasto = giorno.getPastoByTipo(tipipasto[i]);
            Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
            String[] columnnames = {"Portata", "Alimento", "Quantita"};
            DefaultTableModel tablemodel = new DefaultTableModel(columnnames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            while (portateiterator.hasNext()) {
                PortataObject portata = portateiterator.next();
                String tipoportata = String.valueOf(portata.getTipo());
                String alimento = portata.getCibo().getNome();
                String quantita = Integer.toString(portata.getQuantita());
                tablemodel.addRow(new String[]{tipoportata, alimento, quantita});
            }
            tabelle.get(i).setModel(tablemodel);
        }

    }

    public void setPortataItems(){
        JComboBox portata =dialog.getPortata();
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

    public void aggiungiPortataEffettiva() {
        String portata = dialog.getPortata().getSelectedItem().toString();
        String alimento = dialog.getNomeAlimento().getText();
        int quantita = Integer.parseInt(dialog.getQuantita().getText());
        PastoObject pasto = giornocorrente.getPastoByTipo(nuovopasto);
        if (pasto.getId() == 0) {
            pasto.setTipo(PastoEnum.valueOf(nuovopasto));
            PastoModel pastomodel = new PastoModel();
            pastomodel.inserisciPasto(pasto);
            String username = giornocorrente.getUsername();
            LocalDate data = giornocorrente.getData();
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put(nuovopasto,pasto.getId());
            new GiornoAlimModel().updateGiornoAlimEff(username,data,mappa);
        }
        if (!aggiornaPortata(pasto, alimento, quantita)) {
            CiboModel cibomodel = new CiboModel();
            CiboObject nuovocibo = cibomodel.getCiboByName(alimento);
            PortataObject nuovaportata = new PortataObject(nuovocibo);
            nuovaportata.setId_pasto(pasto.getId());
            nuovaportata.setTipo(PortataEnum.valueOf(portata));
            nuovaportata.setQuantita(quantita);
            PortataModel portatamodel = new PortataModel();
            portatamodel.inserisciPortata(nuovaportata);
            pasto.addPortata(nuovaportata);
        }
    }

    private boolean aggiornaPortata(PastoObject pasto, String alimento, int quantita) {
        Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
        while ( portateiterator.hasNext() ) {
            PortataObject portata = portateiterator.next();
            if (alimento.equals(portata.getCibo().getNome())) {
                int nuovaquantita = portata.getQuantita() + quantita;
                portata.setQuantita(nuovaquantita);
                new PortataModel().updatePortata(portata.getId_pasto(), alimento, nuovaquantita);
                return true;
            }
        }
        return false;
    }

    public void removePortata(JTable tabella, String nomepasto){
        String cibo = tabella.getModel().getValueAt(tabella.getSelectedRow(), 1).toString();
        PastoObject pasto = giornocorrente.getPastoByTipo(nomepasto);
        pasto.removePortata(cibo);
        new PortataModel().eliminaPortata(pasto.getId(), cibo);
    }

}
