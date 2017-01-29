package Controller;

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
import javax.swing.table.TableModel;
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
public class AlimentazioneController extends BaseAlimController {

    private AlimentazioneView alimentazione;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    //private ResultSet alimenti;
    //private String nuovopasto;
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

        //showIndex();

        menu.addNewProgAlimButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgAlimController(alimentazione,utente);
                SubMenuVisibility(menu.getSubMenuAlimPanel());
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

        giornocorrenteview.addListenersAndshowButtons(new ListenersAndShowButtonsAction());

        giornocorrenteview.addTableSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) {
                    JButton bottonescelto = giornocorrenteview.getButtonFromTable((ListSelectionModel) e.getSource());
                    bottonescelto.setEnabled(true);
                }
            }
        });

        giornocorrenteview.addListenersForRemoveButtons(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tabellascelta = giornocorrenteview.getTableFromButton(e.getActionCommand());
                removePortata(tabellascelta, e.getActionCommand());
                ((DefaultTableModel)tabellascelta.getModel()).removeRow(tabellascelta.getSelectedRow());
                JButton bottone = (JButton)e.getSource();
                bottone.setEnabled(false);
            }
        });

        dialog.addSetPortataItemListener(new SetPortataItemAction());

        dialog.addSearchKeyListener(new SearchKeyAction());

        dialog.addSetCiboListSelectionListener(new SetCiboListSelectionAction());

        dialog.addQuantitaKeyListener(new QuantitaKeyAction());

        dialog.addPortataEffettivaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tabellascelta = giornocorrenteview.getTableFromButton(e.getActionCommand());
                aggiungiPortataEffettiva((DefaultTableModel)tabellascelta.getModel());
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
        /*data = LocalDate.now().plusDays(1);
        while (!data.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            DayOfWeek giornodoposettimana = data.getDayOfWeek();
            GiornoAlimView giornodopoview = indexalimentazione.getGiorni(giornodoposettimana);
            GiornoAlimObject giornodopo = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
            showPasti(giornodopo,giornodopoview);
            data = data.plusDays(1);
        }*/
    }

    public void aggiungiPortataEffettiva(DefaultTableModel tabellamodel) {
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
        if (!aggiornaPortata(pasto, alimento, quantita, tabellamodel)) {
            CiboModel cibomodel = new CiboModel();
            CiboObject nuovocibo = cibomodel.getCiboByName(alimento);
            PortataObject nuovaportata = new PortataObject(nuovocibo);
            nuovaportata.setId_pasto(pasto.getId());
            nuovaportata.setTipo(PortataEnum.valueOf(portata));
            nuovaportata.setQuantita(quantita);
            PortataModel portatamodel = new PortataModel();
            portatamodel.inserisciPortata(nuovaportata);
            pasto.addPortata(nuovaportata);
            tabellamodel.addRow(new String[]{portata,alimento,Integer.toString(quantita)});
        }

    }

    private boolean aggiornaPortata(PastoObject pasto, String alimento, int quantita, DefaultTableModel tabellamodel) {
        Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
        while ( portateiterator.hasNext() ) {
            PortataObject portata = portateiterator.next();
            if (alimento.equals(portata.getCibo().getNome())) {
                int nuovaquantita = portata.getQuantita() + quantita;
                portata.setQuantita(nuovaquantita);
                new PortataModel().updatePortata(portata.getId_pasto(), alimento, nuovaquantita);
                int rowcount = tabellamodel.getRowCount();
                boolean exit = true;
                for(int indexrow = 0; indexrow < rowcount && exit; indexrow ++){
                    if(tabellamodel.getValueAt(indexrow,1).equals(alimento)) {
                        tabellamodel.setValueAt(nuovaquantita, indexrow, 2);
                        exit = false;
                    }
                }
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
