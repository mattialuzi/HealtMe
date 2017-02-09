package Controller;

import Model.*;
import Object.Enum.GiornoEnum;
import Object.UtenteObject;
import Object.EsercizioObject;
import Object.SedutaObject;
import Object.AttivitaObject;
import Object.ProgrammaAllenamentoObject;
import Object.GiornoAllenObject;
import View.Allenamento.*;
import View.Menu;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AllenamentoController extends BaseAllenController{

    private UtenteObject utente;
    private AllenamentoView allenamento;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private GiornoAllenView giornocorrenteview;
    private IndexAllenamentoView indexallenamento;

    public AllenamentoController(Menu menu, UtenteObject utente) {

        this.utente = utente;
        this.allenamento = menu.getAllenamentoview();
        variablePanel = allenamento.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        NewEsercizioView newesercizio = allenamento.getNewesercizio();
        indexallenamento = allenamento.getIndexallenamento();
        setGiorni();
        dialog = new FormEsercizioEffettivo();
        indexoggi = giornoeffcorrente.getData().getDayOfWeek().ordinal();

        menu.addNewProgAllenButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgAllenController(allenamento,utente,giornoeffcorrente);
                SubMenuVisibility(menu.getSubMenuAllenPanel());
            }
        });

        menu.addNewEsercizioButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAllenPanel());
                cardLayout.show(variablePanel, "NewEsercizioView");
            }
        });

        newesercizio.addAzzeraCampiButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newesercizio.azzeraCampi();
            }
        });

        newesercizio.addAggiungiEsercizioButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newesercizio.isValid()){
                    EsercizioObject nuovoesercizio = newesercizio.getNuovoEsercizio();
                    EsercizioModel eserciziomodel = new EsercizioModel();
                    boolean validator = eserciziomodel.findEsercizioByName(nuovoesercizio.getTipologia());
                    if (validator) {
                        JOptionPane.showMessageDialog(null, "Esercizio già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        eserciziomodel.inserisciEsercizio(nuovoesercizio);
                        JOptionPane.showMessageDialog(null, "Esercizio registrato con successo", "Operazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        newesercizio.azzeraCampi();
                        cardLayout.show(variablePanel, "IndexAllenamentoView");
                    }
                }
            }
        });

        giornocorrenteview.addListenersAndshowButtons(new ListenersAndShowButtonsAction());

        giornocorrenteview.addTableSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()) {
                    giornocorrenteview.getRemoveButton().setEnabled(true);
                }
            }
        });

        giornocorrenteview.addListenersForRemoveButtons(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tabella = giornocorrenteview.getTable(GiornoEnum.effettivo);
                removeAttivita(tabella);
                ((DefaultTableModel)tabella.getModel()).removeRow(tabella.getSelectedRow());
                JButton bottone = (JButton)e.getSource();
                bottone.setEnabled(false);
            }
        });

        dialog.addSetUnitaItemListener(new SetUnitaItemAction());

        dialog.addSearchKeyListener(new SearchKeyAction());

        dialog.addSetEsercizioListSelectionListener(new SetEsercizioListSelectionAction());

        dialog.addQuantitaKeyListener(new QuantitaKeyAction());

        dialog.addAttivitaEffettivaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tabella = giornocorrenteview.getTable(GiornoEnum.effettivo);
                aggiungiEsercizioEffettivo((DefaultTableModel)tabella.getModel());
                dialog.onCancel();
            }
        });
    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAllenamentoView");
    }

    public void setGiorni() {
        LocalDate data = LocalDate.now();
        DayOfWeek giornosettimana = data.getDayOfWeek();
        GiornoAllenModel giornomodel = new GiornoAllenModel();
        giornocorrenteview = indexallenamento.getGiorni(giornosettimana);
        //giornocorrenteview.setButtonFromTable();
        //giornocorrenteview.setTableFromButton();
        indexallenamento.setTodayTab(giornosettimana);
        int i = giornosettimana.getValue();
        ProgrammaAllenamentoObject progallen = utente.getProgramma_allenamento();
        giornoeffcorrente = giornomodel.getGiornoAllenEffettivo(utente.getUsername(), data);
        showSeduta(giornoeffcorrente, giornocorrenteview);
        data = data.minusDays(1);
        for (int j = i-1; j > 0; j--) {
            GiornoAllenView giornoprimaview = indexallenamento.getGiorni(DayOfWeek.of(j));
            GiornoAllenObject giornoprima = giornomodel.getGiornoAllenEffettivo(utente.getUsername(), data);
            showSeduta(giornoprima, giornoprimaview);
            data = data.minusDays(1);
        }
        if (progallen != null) {
            for (int j = 1; j <= 7; j++) { //perchè DayOfWeek.of(j) ritorna Lunedì se j=1 .... Domenica se j=7
                GiornoAllenView giornodopoview = indexallenamento.getGiorni(DayOfWeek.of(j));
                GiornoAllenObject giornodopo = progallen.getSettimanaallenamento(j-1); //perchè DayOfWeek.of(j) ritorna Lunedì se j=1 .... Domenica se j=7
                showSeduta(giornodopo, giornodopoview);
            }
        }
    }

    public void aggiungiEsercizioEffettivo(DefaultTableModel tabellamodel) {
        String unita = dialog.getUnitamisura().getSelectedItem().toString();
        String esercizio = dialog.getNomeEsercizio().getText();
        int quantita = Integer.parseInt(dialog.getQuantita().getText());
        SedutaObject seduta = giornoeffcorrente.getSeduta();
        if (seduta.getId() == 0) {
            SedutaModel sedutamodel = new SedutaModel();
            sedutamodel.inserisciSeduta(seduta);
            String username = giornoeffcorrente.getUsername();
            LocalDate data = giornoeffcorrente.getData();
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put("seduta",seduta.getId());
            new GiornoAllenModel().updateGiornoAllenEff(username,data,mappa);
        }
        if (!aggiornaAttivita(seduta, esercizio, quantita, tabellamodel)) {
            EsercizioModel eserciziomodel = new EsercizioModel();
            EsercizioObject nuovoesercizio = eserciziomodel.getEsercizioByTipologia(esercizio);
            AttivitaObject nuovaattivita = new AttivitaObject(nuovoesercizio);
            nuovaattivita.setId_seduta(seduta.getId());
            nuovaattivita.setQuantita(quantita);
            AttivitaModel attivitamodel = new AttivitaModel();
            attivitamodel.inserisciAttivita(nuovaattivita);
            seduta.addAttivita(nuovaattivita);
            giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(nuovaattivita));
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put("cal_consumate", giornoeffcorrente.getCalorie());
            new GiornoAllenModel().updateGiornoAllenEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
            tabellamodel.addRow(new String[]{esercizio, Integer.toString(quantita), unita});
        }
        //indexallenamento.setCalorieLabel(giornoeffcorrente.getCalorie());
        //new ProgressiModel().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_consumate",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    private boolean aggiornaAttivita(SedutaObject seduta, String esercizio, int quantita, DefaultTableModel tabellamodel) {
        Iterator<AttivitaObject> attivitaiterator = seduta.getAttivita().iterator();
        while ( attivitaiterator.hasNext() ) {
            AttivitaObject attivita = attivitaiterator.next();
            if (esercizio.equals(attivita.getEsercizio().getTipologia())) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(attivita.getEsercizio(),quantita));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_consumate", giornoeffcorrente.getCalorie());
                new GiornoAlimModel().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                int nuovaquantita = attivita.getQuantita() + quantita;
                attivita.setQuantita(nuovaquantita);
                new PortataModel().updatePortata(attivita.getId_seduta(), esercizio, nuovaquantita);
                int rowcount = tabellamodel.getRowCount();
                boolean exit = true;
                for(int indexrow = 0; indexrow < rowcount && exit; indexrow ++){
                    if(tabellamodel.getValueAt(indexrow,1).equals(esercizio)) {
                        tabellamodel.setValueAt(nuovaquantita, indexrow, 1);
                        exit = false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void removeAttivita(JTable tabella){
        String esercizio = tabella.getModel().getValueAt(tabella.getSelectedRow(), 0).toString();
        SedutaObject seduta = giornoeffcorrente.getSeduta();
        ArrayList<AttivitaObject> listaattivita = seduta.getAttivita();
        boolean exit = true;
        for(int i = 0; i < listaattivita.size() && exit; i++){
            if(listaattivita.get(i).getEsercizio().getTipologia().equals(esercizio)) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() - calcolaCalorie(listaattivita.get(i)));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_consumate", giornoeffcorrente.getCalorie());
                new GiornoAllenModel().updateGiornoAllenEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                seduta.removeAttivita(listaattivita.get(i));
                exit = false;
            }
        }
        new AttivitaModel().eliminaAttivita(seduta.getId(), esercizio);
        //indexallenamento.setCalorieLabel(giornoeffcorrente.getCalorie());
        //new ProgressiModel().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_consumate",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    private int calcolaCalorie(EsercizioObject esercizio, int quantita){
        return quantita*esercizio.getConsumo_calorico();
    }

}
