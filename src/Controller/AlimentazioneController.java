package Controller;

import Model.*;
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
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneController extends BaseAlimController {

    private AlimentazioneView alimentazione;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private UtenteObject utente;
    private GiornoAlimView giornocorrenteview;
    private IndexAlimentazioneView indexalimentazione;


    public AlimentazioneController(Menu menu, UtenteObject utente) {

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
                new ProgAlimController(alimentazione, utente, giornoeffcorrente, giornoeffcorrente.getStatus());
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
                new GiornoAlimModel().updateGiornoAlimEff(utente.getUsername(), giornoeffcorrente.getData(), campogiorno);
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

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }

    public void setGiorni() {
        LocalDate data = LocalDate.now();
        DayOfWeek giornosettimana = data.getDayOfWeek();
        GiornoAlimModel giornomodel = new GiornoAlimModel();
        giornocorrenteview = indexalimentazione.getGiorni(giornosettimana);
        giornocorrenteview.setButtonFromTable();
        giornocorrenteview.setTableFromButton();
        indexalimentazione.setTodayTab(giornosettimana);
        int i = giornosettimana.getValue();
        ProgrammaAlimentareObject progalim = utente.getProgramma_alimentare();
        giornoeffcorrente = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
        showPasti(giornoeffcorrente, giornocorrenteview);
        data = data.minusDays(1);
        for (int j = i-1; j > 0; j--) {
            GiornoAlimView giornoprimaview = indexalimentazione.getGiorni(DayOfWeek.of(j));
            GiornoAlimObject giornoprima = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
            showPasti(giornoprima, giornoprimaview);
            data = data.minusDays(1);
        }
        if (progalim != null) {
            for (int j = 1; j <= 7; j++) { //perchè DayOfWeek.of(j) ritorna Lunedì se j=1 .... Domenica se j=7
                GiornoAlimView giornodopoview = indexalimentazione.getGiorni(DayOfWeek.of(j));
                GiornoAlimObject giornodopo = progalim.getSettimanaalimentare(j-1); //perchè DayOfWeek.of(j) ritorna Lunedì se j=1 .... Domenica se j=7
                showPasti(giornodopo, giornodopoview);
            }
        }
    }

    public void aggiungiPortataEffettiva(DefaultTableModel tabellamodel) {
        String portata = dialog.getPortata().getSelectedItem().toString();
        String alimento = dialog.getNomeAlimento().getText();
        int quantita = Integer.parseInt(dialog.getQuantita().getText());
        PastoObject pasto = giornoeffcorrente.getPastoByTipo(nuovopasto);
        if (pasto.getId() == 0) {
            pasto.setTipo(PastoEnum.valueOf(nuovopasto));
            PastoModel pastomodel = new PastoModel();
            pastomodel.inserisciPasto(pasto);
            String username = giornoeffcorrente.getUsername();
            LocalDate data = giornoeffcorrente.getData();
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
            giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(nuovaportata));
            HashMap<String,Integer> mappa = new HashMap<String,Integer>();
            mappa.put("cal_assunte", giornoeffcorrente.getCalorie());
            new GiornoAlimModel().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
            tabellamodel.addRow(new String[]{portata,alimento,Integer.toString(quantita)});
        }
        indexalimentazione.setCalorieLabel(giornoeffcorrente.getCalorie());
        new ProgressiModel().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_assunte",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    private boolean aggiornaPortata(PastoObject pasto, String alimento, int quantita, DefaultTableModel tabellamodel) {
        Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
        while ( portateiterator.hasNext() ) {
            PortataObject portata = portateiterator.next();
            if (alimento.equals(portata.getCibo().getNome())) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() + calcolaCalorie(portata.getCibo(),quantita));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_assunte", giornoeffcorrente.getCalorie());
                new GiornoAlimModel().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
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
        PastoObject pasto = giornoeffcorrente.getPastoByTipo(nomepasto);
        ArrayList<PortataObject> portate = pasto.getPortate();
        boolean exit = true;
        for(int i = 0; i < portate.size() && exit; i++){
            if(portate.get(i).getCibo().getNome().equals(cibo)) {
                giornoeffcorrente.setCalorie(giornoeffcorrente.getCalorie() - calcolaCalorie(portate.get(i)));
                HashMap<String,Integer> mappa = new HashMap<String,Integer>();
                mappa.put("cal_assunte", giornoeffcorrente.getCalorie());
                new GiornoAlimModel().updateGiornoAlimEff(giornoeffcorrente.getUsername(), giornoeffcorrente.getData(),mappa);
                pasto.removePortata(portate.get(i));
                exit = false;
            }
        }
        new PortataModel().eliminaPortata(pasto.getId(), cibo);
        indexalimentazione.setCalorieLabel(giornoeffcorrente.getCalorie());
        new ProgressiModel().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_assunte",String.valueOf(giornoeffcorrente.getCalorie()));
    }

    private int calcolaCalorie(CiboObject cibo,int quantita){
        return quantita*(cibo.getKilocal())/100;
    }

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

    private void ricombinaPortata(PastoObject pasto, ArrayList<PortataObject> portateuguali, ArrayList<Integer> indiciricombina){
        AllergiaEnum allergia = utente.getAllergia();
        AlimentazioneEnum tipoalimentazione = utente.getProgramma_alimentare().getTipo_alimentazione();
        CiboModel cibomodel = new CiboModel();
        PortataModel portatamodel = new PortataModel();
        ArrayList<PortataObject> portate = pasto.getPortate();
        int i= portateuguali.size();
        for (int j =0; j<i;j++) {
            int indice = indiciricombina.get(j);
            PortataObject portatadasostituire = portate.get(indice);
            generateIdoneitaMap();
            ArrayList<String> portatedisponibili = cibomodel.getCiboForUser(allergia.toString(), tipoalimentazione.toString(), portateuguali.get(j).getTipo().toString(), getIdoneita(pasto.getTipo(), portateuguali.get(j).getTipo()));
            portatedisponibili.remove(portateuguali.get(j).getCibo().getNome());
            int fabbisognoportata = calcolaCalorie(portatadasostituire);
            String nomecibo = portatedisponibili.get(randomize(portatedisponibili.size()));
            CiboObject nuovocibo = cibomodel.getCiboByName(nomecibo);
            portatadasostituire.setQuantita(calcolaQuantita(fabbisognoportata, nuovocibo.getKilocal()));
            portatamodel.updatePortata(portatadasostituire.getId_pasto(),portatadasostituire.getCibo().getNome(), nuovocibo.getNome() ,portatadasostituire.getQuantita());
            portatadasostituire.setCibo(nuovocibo);
        }
    }

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
            PastoModel pastomodel = new PastoModel();
            PortataModel portatamodel = new PortataModel();
            for (int k = indexstatus + 1; k < 4; k++) {
                ArrayList<PortataObject> portate = giorno.getPasti(k).getPortate();
                PastoObject nuovopasto = new PastoObject(giorno.getPasti(k).getTipo());
                pastomodel.inserisciPasto(nuovopasto);
                for (PortataObject portata : portate) {
                    PortataObject nuovaportata = new PortataObject(portata.getCibo());
                    nuovaportata.setId_pasto(nuovopasto.getId());
                    nuovaportata.setTipo(portata.getTipo());
                    nuovaportata.setQuantita(portata.getQuantita());
                    portatamodel.inserisciPortata(nuovaportata);
                    nuovopasto.addPortata(nuovaportata);
                }
                giornodinamico.setPasti(k, nuovopasto);
            }
            new GiornoAlimModel().inserisciGiornoAlimDinamico(giornodinamico);
            utente.getProgramma_alimentare().setSettimanaalimentare(indexgiorno, giornodinamico);
            return giornodinamico;
        } else {
            PastoEnum tipopasto = giornoeffcorrente.getPasti(indexstatus).getTipo();
            new GiornoAlimModel().updateGiornoAlimDinamico(giornoeffcorrente.getPasti(indexstatus).getId(), giorno.getPasti(indexstatus).getId(), tipopasto);
            giorno.setPasti(indexstatus,giornoeffcorrente.getPasti(indexstatus));

            return giorno;
        }
    }

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
        PortataModel portatamodel = new PortataModel();
        for (PortataObject portata : portate){
            portatamodel.updatePortata(pasto.getId(), portata.getCibo().getNome(), portata.getQuantita());
        }
    }


    private void aggiornaQuantita (PortataObject portata, int fabportata){
        int nuovaquantita = (fabportata*100)/(portata.getCibo().getKilocal());
        portata.setQuantita(nuovaquantita);
    }
}
