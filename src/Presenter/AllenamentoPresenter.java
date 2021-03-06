package Presenter;

import DAO.*;
import Object.Enum.GiornoEnum;
import Object.UtenteObject;
import Object.EsercizioObject;
import Object.SedutaObject;
import Object.AttivitaObject;
import Object.ProgrammaAllenamentoObject;
import Object.ProgAllenCombObject;
import Object.GiornoAllenObject;
import Object.GiornoAllenProgObject;
import Object.GiornoAllenDinamicoObject;
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
 * La classe AllenamentoPresenter è il presenter utilizzato per inserire un nuovo esercizio e per la gestione della settimana d'allenamento
 */
public class AllenamentoPresenter extends BaseAllenPresenter {

    private UtenteObject utente;
    private AllenamentoView allenamento;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private GiornoAllenView giornocorrenteview;
    private IndexAllenamentoView indexallenamento;

    public AllenamentoPresenter(Menu menu, UtenteObject utente) {

        this.utente = utente;
        this.allenamento = menu.getAllenamentoview();
        variablePanel = allenamento.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        NewEsercizioView newesercizio = allenamento.getNewesercizio();
        indexallenamento = allenamento.getIndexallenamento();
        setGiorni();
        dialog = new FormEsercizioEffettivo();
        boolean giornopieno = false;
        if(giornoeffcorrente.getCalorie() != 0) giornopieno = true;
        indexoggi = giornoeffcorrente.getData().getDayOfWeek().ordinal();
        giornocorrenteview.visibilityConfermaAndAddButtons(utente.isProg_allen_comb(),giornopieno,giornoeffcorrente.isCompletato());
        if(utente.getProgramma_allenamento() == null){
            indexallenamento.showHideCaloriePanel(false);
            indexallenamento.setCalorieLabel(giornoeffcorrente.getCalorie());
            giornocorrenteview.addListenersAndshowButtons(new ListenersAndShowButtonsAction());
        } else {
            indexallenamento.showHideCaloriePanel(true);
            GiornoAllenProgObject giornoprogcorrente = utente.getProgramma_allenamento().getSettimanaallenamento(indexoggi);
            indexallenamento.setCalorieLabel(giornoeffcorrente.getCalorie(), giornoprogcorrente.getCalorie());
            if(!utente.isProg_allen_comb() || giornoprogcorrente.getCalorie() != 0){
                giornocorrenteview.addListenersAndshowButtons(new ListenersAndShowButtonsAction());
            }
            if(utente.isProg_allen_comb()) giornocorrenteview.enableConfermaButton(giornoeffcorrente.isCompletato());
        }

        menu.addNewProgAllenButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgAllenPresenter(allenamento,utente,giornoeffcorrente);
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
                    EsercizioDAO esercizioDAO = new EsercizioDAO();
                    boolean validator = esercizioDAO.findEsercizioByName(nuovoesercizio.getTipologia());
                    if (validator) {
                        JOptionPane.showMessageDialog(null, "Esercizio già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        esercizioDAO.inserisciEsercizio(nuovoesercizio);
                        JOptionPane.showMessageDialog(null, "Esercizio registrato con successo", "Operazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        newesercizio.azzeraCampi();
                        cardLayout.show(variablePanel, "IndexAllenamentoView");
                    }
                }
            }
        });

        giornocorrenteview.addListenerConfermaButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                giornocorrenteview.visibilityConfermaAndAddButtons(true, true, true);
                ricombina();
                giornoeffcorrente.setCompletato(true);
                HashMap<String,Integer> campogiorno = new HashMap<String, Integer>();
                campogiorno.put("completato",1);
                new GiornoAllenDAO().updateGiornoAllenEff(utente.getUsername(),giornoeffcorrente.getData(),campogiorno);
            }
        });

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

        dialog.addSetUnitaItemListener(new SetIntensitaItemAction());

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

    /**
     * Metodo che mostra la carta (classe view) IndexAllenamentoView del cardLayout
     */

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAllenamentoView");
    }

    /**
     * Metodo che recupera tutti i giorni della settimana d'allenamento
     */

    public void setGiorni() {
        LocalDate data = LocalDate.now();
        DayOfWeek giornosettimana = data.getDayOfWeek();
        GiornoAllenDAO giornoDAO = new GiornoAllenDAO();
        giornocorrenteview = indexallenamento.getGiorni(giornosettimana);
        //giornocorrenteview.setButtonFromTable();
        //giornocorrenteview.setTableFromButton();
        indexallenamento.setTodayTab(giornosettimana);
        int i = giornosettimana.getValue();
        ProgrammaAllenamentoObject progallen = utente.getProgramma_allenamento();
        giornoeffcorrente = giornoDAO.getGiornoAllenEffettivo(utente.getUsername(), data);
        showSeduta(giornoeffcorrente, giornocorrenteview);
        data = data.minusDays(1);
        for (int j = i-1; j > 0; j--) {
            GiornoAllenView giornoprimaview = indexallenamento.getGiorni(DayOfWeek.of(j));
            GiornoAllenObject giornoprima = giornoDAO.getGiornoAllenEffettivo(utente.getUsername(), data);
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

    /**
     * Metodo che aggiunge alla tabella l'attività effettiva di una seduta
     * @param tabellamodel Variabile di tipo DefaultTableModel della tabella
     */

    public void aggiungiEsercizioEffettivo(DefaultTableModel tabellamodel) {
        String unita = dialog.getMisuraLabel().getText();
        String esercizio = dialog.getNomeEsercizio().getText();
        double quantita = Double.parseDouble(dialog.getQuantita().getText());
        SedutaObject seduta = giornoeffcorrente.getSeduta();
        if (seduta.getId() == 0) {
            SedutaDAO sedutamodel = new SedutaDAO();
            sedutamodel.inserisciSeduta(seduta);
            String username = giornoeffcorrente.getUsername();
            LocalDate data = giornoeffcorrente.getData();
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put("seduta",seduta.getId());
            new GiornoAllenDAO().updateGiornoAllenEff(username,data,mappa);
        }
        if (!aggiornaAttivita(seduta, esercizio, quantita, tabellamodel)) {
            EsercizioDAO esercizioDAO = new EsercizioDAO();
            EsercizioObject nuovoesercizio = esercizioDAO.getEsercizioByTipologia(esercizio);
            AttivitaObject nuovaattivita = new AttivitaObject(nuovoesercizio);
            nuovaattivita.setId_seduta(seduta.getId());
            nuovaattivita.setQuantita(quantita);
            AttivitaDAO attivitaDAO = new AttivitaDAO();
            attivitaDAO.inserisciAttivita(nuovaattivita);
            seduta.addAttivita(nuovaattivita);
            giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(nuovaattivita));
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put("cal_consumate", giornoeffcorrente.getCalorie());
            new GiornoAllenDAO().updateGiornoAllenEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
            tabellamodel.addRow(new String[]{esercizio, Double.toString(quantita), unita});
        }
        indexallenamento.setCalorieLabel(giornoeffcorrente.getCalorie());
        new ProgressiDAO().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_consumate",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    /**
     * Metodo che verifica se aggiornare la quantita di un esercizio di un seduta se è già presente
     * @param seduta Variabile SedutaObject che contiene la seduta dell'attività da modificare
     * @param esercizio Esercizio che si vuole verificare se è già presente
     * @param quantita Valore della nuova quantità
     * @param tabellamodel DefaultTableModel della tabella
     * @return true se l'esercizio è presente e la quantita è stata modificata, false se non è presente
     */

    private boolean aggiornaAttivita(SedutaObject seduta, String esercizio, double quantita, DefaultTableModel tabellamodel) {
        Iterator<AttivitaObject> attivitaiterator = seduta.getAttivita().iterator();
        while ( attivitaiterator.hasNext() ) {
            AttivitaObject attivita = attivitaiterator.next();
            if (esercizio.equals(attivita.getEsercizio().getTipologia())) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(attivita.getEsercizio(),quantita));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_consumate", giornoeffcorrente.getCalorie());
                new GiornoAllenDAO().updateGiornoAllenEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                double nuovaquantita = attivita.getQuantita() + quantita;
                attivita.setQuantita(nuovaquantita);
                new AttivitaDAO().updateAttivita(attivita.getId_seduta(), esercizio, nuovaquantita);
                int rowcount = tabellamodel.getRowCount();
                boolean exit = true;
                for(int indexrow = 0; indexrow < rowcount && exit; indexrow ++){
                    if(tabellamodel.getValueAt(indexrow,0).equals(esercizio)) {
                        tabellamodel.setValueAt(nuovaquantita, indexrow, 1);
                        exit = false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo che rimuove un'attività di una seduta effettiva tra quelle già inserite
     * @param tabella Tabella da cui prendere l'attività di una seduta
     */

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
                new GiornoAllenDAO().updateGiornoAllenEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                seduta.removeAttivita(listaattivita.get(i));
                exit = false;
            }
        }
        new AttivitaDAO().eliminaAttivita(seduta.getId(), esercizio);
        indexallenamento.setCalorieLabel(giornoeffcorrente.getCalorie());
        new ProgressiDAO().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_consumate",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    /**
     * Metodo che permette di ricombinare gli esercizi di un giorno d'allenamento programmato
     */

    public void ricombina(){
        ProgAllenCombObject programma =(ProgAllenCombObject) utente.getProgramma_allenamento();
        int disponibilita = programma.getDisponibilita();
        if (disponibilita > 1) {
            int calorieconsumate = giornoeffcorrente.getCalorie();
            int calorieprog = programma.getSettimanaallenamento(indexoggi).getCalorie();
            float tolleranza = calorieprog*0.05f;
            boolean limitecalorie = false;
            if (calorieconsumate < calorieprog-tolleranza || calorieconsumate > calorieprog+tolleranza) {
                limitecalorie = true;
            }
            if (limitecalorie) {
                GiornoAllenProgObject giorno = creaGiornoDinamico(indexoggi,programma.getSettimanaallenamento(indexoggi));
                int eccesso = calorieprog - calorieconsumate;
                boolean flag = true;
                int indexgiorno = indexoggi+1;
                while (flag  && indexgiorno < 6) {
                    if (programma.getSettimanaallenamento(indexgiorno).getCalorie() != 0) {
                        GiornoAllenProgObject giornodopo = creaGiornoDinamico(indexgiorno,programma.getSettimanaallenamento(indexgiorno));
                        int nuovoobiettivo = giornodopo.getCalorie() + eccesso;
                        if (nuovoobiettivo > 0) {
                            AttivitaObject attivita = giornodopo.getSeduta().getAttivita().get(0);
                            int consumocalorico = attivita.getEsercizio().getConsumo_calorico();
                            attivita.setQuantita((double) Math.round((nuovoobiettivo/consumocalorico)*10d)/10d);
                            giornodopo.setCalorie(nuovoobiettivo);
                            new AttivitaDAO().updateAttivita(attivita.getId_seduta(),attivita.getEsercizio().getTipologia(),attivita.getQuantita());
                            new GiornoAllenDAO().updateCalorieGiornoAllenDinamico(utente.getProgramma_allenamento().getId(), giornoeffcorrente.getData().plusDays(indexgiorno-indexoggi), giornodopo.getCalorie());
                            flag = false;
                        }
                        else {
                            eccesso += giornodopo.getCalorie();
                            giornodopo.setCalorie(0);
                            new AttivitaDAO().eliminaAttivita(giornodopo.getSeduta().getId(), giornodopo.getSeduta().getAttivita().get(0).getEsercizio().getTipologia());
                            giornodopo.getSeduta().getAttivita().remove(0);
                            new GiornoAllenDAO().updateCalorieGiornoAllenDinamico(utente.getProgramma_allenamento().getId(), giornoeffcorrente.getData().plusDays(indexgiorno-indexoggi), giornodopo.getCalorie());
                        }
                        removeSeduta(indexallenamento.getGiorni(DayOfWeek.of(indexgiorno+1)),GiornoEnum.dinamico);
                        showSeduta(giornodopo,indexallenamento.getGiorni(DayOfWeek.of(indexgiorno+1)));
                    }
                    indexgiorno++;
                }
                removeSeduta(giornocorrenteview,giorno.getTipo());
                showSeduta(giorno,giornocorrenteview);
            }
        }
    }

    /**
     * Metodo che recupera un giorno d'allenamento dinamico
     * @param indexgiorno Indice del giorno
     * @param giorno Variabile di tipo GiornoAllenProgObject
     * @return
     */

    public GiornoAllenProgObject creaGiornoDinamico(int indexgiorno, GiornoAllenProgObject giorno) {
        if (giorno.getTipo().equals(GiornoEnum.programmato))
        {
            GiornoAllenDinamicoObject giornodinamico = new GiornoAllenDinamicoObject();
            giornodinamico.setCalorie(giorno.getCalorie());
            giornodinamico.setId_programma(utente.getProgramma_allenamento().getId());
            ArrayList<AttivitaObject> listaattivita = new ArrayList<AttivitaObject>();
            if (indexgiorno == indexoggi) {
                giornodinamico.setData(giornoeffcorrente.getData());
                listaattivita = giornoeffcorrente.getSeduta().getAttivita();
            }
            else {
                giornodinamico.setData(giornoeffcorrente.getData().plusDays(indexgiorno-indexoggi));
                listaattivita = giorno.getSeduta().getAttivita();
            }
            new SedutaDAO().inserisciSeduta(giornodinamico.getSeduta());
            AttivitaDAO attivitaDAO = new AttivitaDAO();
            for (AttivitaObject attivita : listaattivita) {
                AttivitaObject nuovaattivita = new AttivitaObject(attivita.getEsercizio());
                nuovaattivita.setId_seduta(giornodinamico.getSeduta().getId());
                nuovaattivita.setQuantita(attivita.getQuantita());
                attivitaDAO.inserisciAttivita(nuovaattivita);
                giornodinamico.getSeduta().addAttivita(nuovaattivita);
            }
            new GiornoAllenDAO().inserisciGiornoAllenDinamico(giornodinamico);
            utente.getProgramma_allenamento().setSettimanaallenamento(indexgiorno,giornodinamico);
            return giornodinamico;
        }
        else {
            new GiornoAllenDAO().updateGiornoAllenDinamico(giornoeffcorrente.getSeduta().getId(), giorno.getSeduta().getId());
            giorno.setSeduta(giornoeffcorrente.getSeduta());
            return giorno;
        }
    }

    /**
     * Metodo che calcola le calorie di un esercizio data la quantità
     * @param esercizio Variabile EsercizioObject di cui si vuole calcolare le calorie
     * @param quantita Quantità di quel cibo
     * @return Calorie di quel esercizio
     */

    private int calcolaCalorie(EsercizioObject esercizio, double quantita){
        return (int) quantita*esercizio.getConsumo_calorico();
    }

}
