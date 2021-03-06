package Presenter;

import DAO.*;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * La classe AlimentazionePresenter è il presenter utilizzato per inserire un nuovo alimento e per la gestione della settimana alimentare
 */

public class AlimentazionePresenter extends BaseAlimPresenter {

    private AlimentazioneView alimentazione;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private UtenteObject utente;
    private GiornoAlimView giornocorrenteview;
    private IndexAlimentazioneView indexalimentazione;


    public AlimentazionePresenter(Menu menu, UtenteObject utente) {

        this.alimentazione = menu.getAlimentazioneview();
        this.utente = utente;
        variablePanel = alimentazione.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        NewCiboView newcibo = alimentazione.getNewcibo();
        indexalimentazione = alimentazione.getIndexalimentazione();
        setGiorni();
        dialog = new FormCiboEffettivo();
        indexoggi = giornoeffcorrente.getData().getDayOfWeek().ordinal();
        giornocorrenteview.visibilityConfermaAndAddButtons(utente.isProg_alim_comb(), giornoeffcorrente.getStatus());
        if(utente.getProgramma_alimentare() == null){
            indexalimentazione.showHideCaloriePanel(false);
            indexalimentazione.setCalorieLabel(giornoeffcorrente.getCalorie());
        } else {
            indexalimentazione.showHideCaloriePanel(true);
            GiornoAlimProgObject giornoprogcorrente = utente.getProgramma_alimentare().getSettimanaalimentare(indexoggi);
            indexalimentazione.setCalorieLabel(giornoeffcorrente.getCalorie(), giornoprogcorrente.getCalorie());
            if(utente.isProg_alim_comb()) giornocorrenteview.enableConfermaButton(giornoeffcorrente.getStatus());
        }

        menu.addNewProgAlimButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgAlimPresenter(alimentazione, utente, giornoeffcorrente, giornoeffcorrente.getStatus());
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
                    CiboDAO ciboDAO = new CiboDAO();
                    boolean validator = ciboDAO.findCiboByName(nuovocibo.getNome());
                    if (validator) {
                        JOptionPane.showMessageDialog(null, "Cibo già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ciboDAO.inserisciCibo(nuovocibo);
                        JOptionPane.showMessageDialog(null, "Cibo registrato con successo", "Operazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        newcibo.azzeraCampi();
                        cardLayout.show(variablePanel, "IndexAlimentazioneView");
                    }
                }
            }
        });

        newcibo.addSetIdoneitaItemListener(new ItemListener () {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()== ItemEvent.SELECTED) {
                    JComboBox portatacombo =(JComboBox) e.getSource();
                    newcibo.setIdoneita((PortataEnum)portatacombo.getSelectedItem());
                }
            }
        });

        giornocorrenteview.addListenersAndshowButtons(new ListenersAndShowButtonsAction());

        giornocorrenteview.addListenersConfermaButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StatusEnum status = StatusEnum.valueOf(e.getActionCommand());
                int index = status.ordinal();
                StatusEnum nuovostatus = StatusEnum.values()[index + 1];
                giornocorrenteview.enableConfermaButton(nuovostatus);
                ricombina();
                giornoeffcorrente.setStatus(nuovostatus);
                HashMap<String, StatusEnum> campogiorno = new HashMap<String, StatusEnum>();
                campogiorno.put("status", nuovostatus);
                new GiornoAlimDAO().updateGiornoAlimEff(utente.getUsername(), giornoeffcorrente.getData(), campogiorno);
            }
        });

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

    /**
     * Metodo che mostra la carta (classe view) IndexAlimentazioneView del cardLayout
     */

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }

    /**
     * Metodo che recupera tutti i giorni della settimana alimentare
     */

    public void setGiorni() {
        LocalDate data = LocalDate.now();
        DayOfWeek giornosettimana = data.getDayOfWeek();
        GiornoAlimDAO giornoDAO = new GiornoAlimDAO();
        giornocorrenteview = indexalimentazione.getGiorni(giornosettimana);
        giornocorrenteview.setButtonFromTable();
        giornocorrenteview.setTableFromButton();
        indexalimentazione.setTodayTab(giornosettimana);
        int i = giornosettimana.getValue();
        ProgrammaAlimentareObject progalim = utente.getProgramma_alimentare();
        giornoeffcorrente = giornoDAO.getGiornoAlimEffettivo(utente.getUsername(), data);
        showPasti(giornoeffcorrente, giornocorrenteview);
        data = data.minusDays(1);
        for (int j = i-1; j > 0; j--) {
            GiornoAlimView giornoprimaview = indexalimentazione.getGiorni(DayOfWeek.of(j));
            GiornoAlimObject giornoprima = giornoDAO.getGiornoAlimEffettivo(utente.getUsername(), data);
            showPasti(giornoprima, giornoprimaview);
            data = data.minusDays(1);
        }
        if (progalim != null) {
            for (int j = 1; j <= 7; j++) {
                GiornoAlimView giornodopoview = indexalimentazione.getGiorni(DayOfWeek.of(j));
                GiornoAlimObject giornodopo = progalim.getSettimanaalimentare(j-1);
                showPasti(giornodopo, giornodopoview);
            }
        }
    }

    /**
     * Metodo che aggiunge alla tabella la portata effettiva di un pasto
     * @param tabellamodel Variabile di tipo DefaultTableModel della tabella
     */

    public void aggiungiPortataEffettiva(DefaultTableModel tabellamodel) {
        String portata = dialog.getPortata().getSelectedItem().toString();
        String alimento = dialog.getNomeAlimento().getText();
        int quantita = Integer.parseInt(dialog.getQuantita().getText());
        PastoObject pasto = giornoeffcorrente.getPastoByTipo(nuovopasto);
        if (pasto.getId() == 0) {
            pasto.setTipo(PastoEnum.valueOf(nuovopasto));
            PastoDAO pastoDAO = new PastoDAO();
            pastoDAO.inserisciPasto(pasto);
            String username = giornoeffcorrente.getUsername();
            LocalDate data = giornoeffcorrente.getData();
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put(nuovopasto,pasto.getId());
            new GiornoAlimDAO().updateGiornoAlimEff(username,data,mappa);
        }
        if (!aggiornaPortata(pasto, alimento, quantita, tabellamodel)) {
            CiboDAO ciboDAO = new CiboDAO();
            CiboObject nuovocibo = ciboDAO.getCiboByName(alimento);
            PortataObject nuovaportata = new PortataObject(nuovocibo);
            nuovaportata.setId_pasto(pasto.getId());
            nuovaportata.setTipo(PortataEnum.valueOf(portata));
            nuovaportata.setQuantita(quantita);
            PortataDAO portataDAO = new PortataDAO();
            portataDAO.inserisciPortata(nuovaportata);
            pasto.addPortata(nuovaportata);
            giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(nuovaportata));
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put("cal_assunte", giornoeffcorrente.getCalorie());
            new GiornoAlimDAO().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
            tabellamodel.addRow(new String[]{portata,alimento,Integer.toString(quantita)});
        }
        indexalimentazione.setCalorieLabel(giornoeffcorrente.getCalorie());
        new ProgressiDAO().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_assunte",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    /**
     * Metodo che verifica se aggiornare la quantita di un cibo di un pasto se è già presente
     * @param pasto Variabile PastoObject che contiene il pasto della portata da modificare
     * @param alimento Alimento che si vuole verificare se è già presente
     * @param quantita Valore della nuova quantità
     * @param tabellamodel DefaultTableModel della tabella
     * @return true se l'alimento è presente e la quantita è stata modificata, false se non è presente
     */

    private boolean aggiornaPortata(PastoObject pasto, String alimento, int quantita, DefaultTableModel tabellamodel) {
        Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
        while ( portateiterator.hasNext() ) {
            PortataObject portata = portateiterator.next();
            if (alimento.equals(portata.getCibo().getNome())) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(portata.getCibo(),quantita));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_assunte", giornoeffcorrente.getCalorie());
                new GiornoAlimDAO().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                int nuovaquantita = portata.getQuantita() + quantita;
                portata.setQuantita(nuovaquantita);
                new PortataDAO().updatePortata(portata.getId_pasto(), alimento, nuovaquantita);
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

    /**
     * Metodo che rimuove una portata di un pasto effettivo tra quelle già inserite
     * @param tabella Tabella da cui prendere la portata di un pasto
     * @param nomepasto Nome del pasto di cui si rimuovere la portata
     */

    public void removePortata(JTable tabella, String nomepasto){
        String cibo = tabella.getModel().getValueAt(tabella.getSelectedRow(), 1).toString();
        PastoObject pasto = giornoeffcorrente.getPastoByTipo(nomepasto);
        ArrayList<PortataObject> portate = pasto.getPortate();
        boolean exit = true;
        for(int i = 0; i < portate.size() && exit; i++){
            if(portate.get(i).getCibo().getNome().equals(cibo)) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() - calcolaCalorie(portate.get(i)));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_assunte", giornoeffcorrente.getCalorie());
                new GiornoAlimDAO().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                pasto.removePortata(portate.get(i));
                exit = false;
            }
        }
        new PortataDAO().eliminaPortata(pasto.getId(), cibo);
        indexalimentazione.setCalorieLabel(giornoeffcorrente.getCalorie());
        new ProgressiDAO().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_assunte",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    /**
     * Metodo che calcola le calorie di un cibo data una quantità
     * @param cibo Variabile CiboObject di cui si vuole calcolare le calorie
     * @param quantita Quantità di quel cibo
     * @return Calorie di quel cibo
     */

    private int calcolaCalorie(CiboObject cibo,int quantita){
        return quantita*(cibo.getKilocal())/100;
    }

    /**
     * Metodo che permette di ricombinare i pasti di un giorno alimentare programmato
     */

    private void ricombina() {
        int indexstatus = giornoeffcorrente.getStatus().ordinal();
        GiornoAlimProgObject oggiprog = utente.getProgramma_alimentare().getSettimanaalimentare(indexoggi);
        GiornoAlimProgObject domaniprog = utente.getProgramma_alimentare().getSettimanaalimentare(indexoggi+1);
        ArrayList<PortataObject> portateeff = giornoeffcorrente.getPasti(indexstatus).getPortate();
        ArrayList<PortataObject> portateprog = oggiprog.getPasti(indexstatus).getPortate();
        ArrayList<PortataObject> portatediverse = new ArrayList<PortataObject>();
        int calorieprog = 0;
        for (PortataObject portataprog : portateprog){
           for (PortataObject portataeff : portateeff) {
                if (portataprog.getTipo() == portataeff.getTipo() && !portataprog.getCibo().getNome().equals(portataeff.getCibo().getNome())) {
                    portatediverse.add(portataeff);
                }
           }
           calorieprog += calcolaCalorie(portataprog);
        }
        int calorieeff =0;
        for (PortataObject portataeff : portateeff) {
            calorieeff += calcolaCalorie(portataeff);
        }
        int tolleranza = (calorieprog/100)*10;
        boolean limitecalorie = false;
        if (calorieeff < calorieprog-tolleranza || calorieeff > calorieprog+tolleranza) {
            limitecalorie = true;
        }
        ArrayList<Integer> indiciricombinaoggi = new ArrayList<Integer>();
        ArrayList<Integer> indiciricombinadomani = new ArrayList<Integer>();
        ArrayList<PortataObject> portatediversedomani = new ArrayList<PortataObject>(portatediverse);
        if (portatediverse.size() != 0) {
            if (indexstatus < 2) {
                indiciricombinaoggi = controllaPortate(portatediverse, oggiprog.getPasti(indexstatus+2).getPortate());
                indiciricombinadomani = controllaPortate(portatediversedomani, domaniprog.getPasti(indexstatus).getPortate());
            } else {
                indiciricombinadomani = controllaPortate(portatediversedomani, domaniprog.getPasti(indexstatus).getPortate());
            }
        }
        if (limitecalorie || portatediverse.size() !=0 || portatediversedomani.size() !=0) {
            GiornoAlimProgObject giorno = getGiornoDinamico(oggiprog, indexoggi, indexstatus);
            if (indiciricombinaoggi.size() !=0) ricombinaPortata(giorno.getPasti(indexstatus+2), portatediverse, indiciricombinaoggi);
            if (indiciricombinadomani.size() !=0){
                GiornoAlimProgObject giornodomani = getGiornoDinamico(domaniprog, indexoggi +1, -1);
                PastoObject pastodomani = giornodomani.getPasti(indexstatus);
                ricombinaPortata(pastodomani, portatediversedomani, indiciricombinadomani);
                removePasti(indexalimentazione.getGiorni(DayOfWeek.of(indexoggi+2)),GiornoEnum.dinamico);
                showPasti(giornodomani,indexalimentazione.getGiorni(DayOfWeek.of(indexoggi+2)));
            }
            if (limitecalorie) {
                int fabrestante = giorno.getCalorie() - giornoeffcorrente.getCalorie();
                if(indexstatus == 0){
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+1), fabrestante * 47/100, indexoggi);
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+2), fabrestante * 13/100, indexoggi);
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+3), fabrestante * 40/100, indexoggi);
                } else if (indexstatus == 1){
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+1), fabrestante * 25/100, indexoggi);
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+2), fabrestante * 75/100, indexoggi);
                } else if (indexstatus == 2) ricombinaCaloriaPasto(giorno.getPasti(indexstatus+1), fabrestante, indexoggi);
            }
            removePasti(giornocorrenteview,giorno.getTipo());
            showPasti(giorno,giornocorrenteview);
        }

    }

    /**
     * Metodo che modifica le portate di un pasto programmato
     * @param pasto Pasto di cui si vuole modificare le portate
     * @param portateuguali Arraylist di PortateObject che si devono ricombinare dei pasti successivi
     * @param indiciricombina Arraylist di Integer che contiene gli indici delle portate da ricombinare
     */

    private void ricombinaPortata(PastoObject pasto, ArrayList<PortataObject> portateuguali, ArrayList<Integer> indiciricombina){
        AllergiaEnum allergia = utente.getAllergia();
        AlimentazioneEnum tipoalimentazione = utente.getProgramma_alimentare().getTipo_alimentazione();
        CiboDAO ciboDAO = new CiboDAO();
        PortataDAO portataDAO = new PortataDAO();
        ArrayList<PortataObject> portate = pasto.getPortate();
        int i= portateuguali.size();
        for (int j =0; j<i;j++) {
            int indice = indiciricombina.get(j);
            PortataObject portatadasostituire = portate.get(indice);
            generateIdoneitaMap();
            ArrayList<String> portatedisponibili = ciboDAO.getCiboForUser(allergia.toString(), tipoalimentazione.toString(), portateuguali.get(j).getTipo().toString(), getIdoneita(pasto.getTipo(), portateuguali.get(j).getTipo()));
            portatedisponibili.remove(portateuguali.get(j).getCibo().getNome());
            int fabbisognoportata = calcolaCalorie(portatadasostituire);
            String nomecibo = portatedisponibili.get(randomize(portatedisponibili.size()));
            CiboObject nuovocibo = ciboDAO.getCiboByName(nomecibo);
            portatadasostituire.setQuantita(calcolaQuantita(fabbisognoportata, nuovocibo.getKilocal()));
            portataDAO.updatePortata(portatadasostituire.getId_pasto(),portatadasostituire.getCibo().getNome(), nuovocibo.getNome() ,portatadasostituire.getQuantita());
            portatadasostituire.setCibo(nuovocibo);
        }
    }

    /**
     * Metodo che recupera un giorno alimentare dinamico
     * @param giorno Variabile di tipo GiornoAlimProgObject
     * @param indexgiorno Indice del giorno
     * @param indexstatus Indice dello status
     * @return
     */

    private GiornoAlimProgObject getGiornoDinamico (GiornoAlimProgObject giorno, int indexgiorno, int indexstatus) {
        if (giorno.getTipo().equals(GiornoEnum.programmato)) {
            GiornoAlimDinamicoObject giornodinamico = new GiornoAlimDinamicoObject();
            giornodinamico.setCalorie(giorno.getCalorie());
            giornodinamico.setId_programma(utente.getProgramma_alimentare().getId());
            utente.getProgramma_alimentare().setSettimanaalimentare(indexgiorno, giornodinamico);
            for (int j = 0; j <= indexstatus; j++) {
                giornodinamico.setPasti(j, giornoeffcorrente.getPasti(j));
            }
            if (indexstatus >= 0) {
                giornodinamico.setData(giornoeffcorrente.getData());
            } else {
                giornodinamico.setData(giornoeffcorrente.getData().plusDays(1));
            }
            PastoDAO pastoDAO = new PastoDAO();
            PortataDAO portataDAO = new PortataDAO();
            for (int k = indexstatus + 1; k < 4; k++) {
                ArrayList<PortataObject> portate = giorno.getPasti(k).getPortate();
                PastoObject nuovopasto = new PastoObject(giorno.getPasti(k).getTipo());
                pastoDAO.inserisciPasto(nuovopasto);
                for (PortataObject portata : portate) {
                    PortataObject nuovaportata = new PortataObject(portata.getCibo());
                    nuovaportata.setId_pasto(nuovopasto.getId());
                    nuovaportata.setTipo(portata.getTipo());
                    nuovaportata.setQuantita(portata.getQuantita());
                    portataDAO.inserisciPortata(nuovaportata);
                    nuovopasto.addPortata(nuovaportata);
                }
                giornodinamico.setPasti(k, nuovopasto);
            }
            new GiornoAlimDAO().inserisciGiornoAlimDinamico(giornodinamico);
            utente.getProgramma_alimentare().setSettimanaalimentare(indexgiorno, giornodinamico);
            return giornodinamico;
        } else {
            PastoEnum tipopasto = giornoeffcorrente.getPasti(indexstatus).getTipo();
            new GiornoAlimDAO().updateGiornoAlimDinamico(giornoeffcorrente.getPasti(indexstatus).getId(), giorno.getPasti(indexstatus).getId(), tipopasto);
            giorno.setPasti(indexstatus,giornoeffcorrente.getPasti(indexstatus));

            return giorno;
        }
    }

    /**
     * Metodo "costruisce" gli indici delle portate da ricombinare
     * @param portatediverse Arraylist di PortataObject che contiene le portate effettive
     * @param portateprog Arraylist di PortataObject che contiene le portate programmate
     * @return Arraylist di Integer che contiene gli indici delle portate da ricombinare
     */

    private ArrayList<Integer> controllaPortate (ArrayList<PortataObject> portatediverse, ArrayList<PortataObject> portateprog) {
        ArrayList<Integer> indiciportatedaricombinare = new ArrayList<Integer>();
        int portateprogsize = portateprog.size();
        int i=0;
        while (i<portatediverse.size()) {
            boolean flag = true;
            for (int j=0; j<portateprogsize && flag; j++) {
                if (portateprog.get(j).getCibo().getNome().equals(portatediverse.get(i).getCibo().getNome())) {
                    flag = false;
                    indiciportatedaricombinare.add(j);
                }
            }
            if (flag) portatediverse.remove(i);
            else i++;
        }
        return indiciportatedaricombinare;
    }

    /**
     * Metodo che ricombina le calorie di un pasto
     * @param pasto Pasto di cui si deve ricombinare le calorie
     * @param fabbisogno Fabbisogno di quel pasto
     * @param indicegiorno Indice del gionro
     */


    private void ricombinaCaloriaPasto (PastoObject pasto, int fabbisogno, int indicegiorno){
        PastoEnum tipopasto = pasto.getTipo();
        ArrayList<PortataObject> portate = pasto.getPortate();
        if (tipopasto.equals(PastoEnum.pranzo)) {
            if(indicegiorno != 6) {
                aggiornaQuantita(portate.get(0), (fabbisogno*45)/100);
                aggiornaQuantita(portate.get(1), (fabbisogno*32)/100);
                aggiornaQuantita(portate.get(2), (fabbisogno*18)/100);
                aggiornaQuantita(portate.get(3), (fabbisogno*5)/100);
            } else {
                aggiornaQuantita(portate.get(0), (fabbisogno*38)/100);
                aggiornaQuantita(portate.get(1), (fabbisogno*28)/100);
                aggiornaQuantita(portate.get(2), (fabbisogno*11)/100);
                aggiornaQuantita(portate.get(3), (fabbisogno*23)/100);
            }
        } else if (tipopasto.equals(PastoEnum.cena)) {
            if ((indicegiorno + 1) % 2 == 0) {
                aggiornaQuantita(portate.get(0), (fabbisogno * 40) / 100);
                aggiornaQuantita(portate.get(1), (fabbisogno * 53) / 100);
                aggiornaQuantita(portate.get(2), (fabbisogno * 7) / 100);
            } else {
                aggiornaQuantita(portate.get(0), (fabbisogno * 53) / 100);
                aggiornaQuantita(portate.get(1), (fabbisogno * 40) / 100);
                aggiornaQuantita(portate.get(2), (fabbisogno * 7) / 100);
            }
        } else {
            aggiornaQuantita(portate.get(0), (fabbisogno * 50) / 100);
            aggiornaQuantita(portate.get(1), (fabbisogno * 30) / 100);
            aggiornaQuantita(portate.get(2), (fabbisogno * 20) / 100);
        }
        PortataDAO portataDAO = new PortataDAO();
        for (PortataObject portata : portate){
            portataDAO.updatePortata(pasto.getId(), portata.getCibo().getNome(), portata.getQuantita());
        }
    }

    /**
     * Metodo che aggiorna la quantita di una portata
     * @param portata Portata di cui si deve modificare la quantita
     * @param fabportata Fabbisogno della portata
     */

    private void aggiornaQuantita (PortataObject portata, int fabportata){
        int nuovaquantita = (fabportata*100)/(portata.getCibo().getKilocal());
        portata.setQuantita(nuovaquantita);
    }
}
