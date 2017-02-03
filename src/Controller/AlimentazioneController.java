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
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
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

        giornocorrenteview.enableConfermaButton(giornocorrente.getStatus());

        if ( utente.isProg_alim_comb()) {
            giornocorrenteview.addListenersAndShowConfermaButton(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StatusEnum status = StatusEnum.valueOf(e.getActionCommand());
                    int index = status.ordinal();
                    StatusEnum nuovostatus = StatusEnum.values()[index + 1];
                    giornocorrenteview.enableConfermaButton(nuovostatus);
                    ricombina();
                    giornocorrente.setStatus(nuovostatus);
                    HashMap<String, StatusEnum> campogiorno = new HashMap<String, StatusEnum>();
                    campogiorno.put("status", nuovostatus);
                    new GiornoAlimModel().updateGiornoAlimEff(utente.getUsername(), giornocorrente.getData(), campogiorno);
                }
            });
        }

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
        giornocorrente = giornomodel.getGiornoAlimEffettivo(utente.getUsername(), data);
        showPasti(giornocorrente, giornocorrenteview);
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

    private int calcolaCalorie(CiboObject cibo,int quantita){
        return quantita*(cibo.getKilocal())/100;
    }

    private void ricombina() {
        int indexstatus = giornocorrente.getStatus().ordinal();
        int indexoggi = giornocorrente.getData().getDayOfWeek().ordinal();
        GiornoAlimProgObject oggiprog = utente.getProgramma_alimentare().getSettimanaalimentare(indexoggi);
        GiornoAlimProgObject domaniprog = utente.getProgramma_alimentare().getSettimanaalimentare(indexoggi+1);
        ArrayList<PortataObject> portateeff = giornocorrente.getPasti(indexstatus).getPortate();
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
        giornocorrente.setCalorie(giornocorrente.getCalorie() + calorieeff);
        HashMap<String,Integer> mappa = new HashMap<String,Integer>();
        mappa.put("cal_assunte",giornocorrente.getCalorie());
        new GiornoAlimModel().updateGiornoAlimEff(giornocorrente.getUsername(),giornocorrente.getData(),mappa);
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
            if (portatediverse.size() !=0) ricombinaPortata(giorno.getPasti(indexstatus+2), portatediverse, indiciricombinaoggi);
            if (portatediversedomani.size() !=0){
                PastoObject pastodomani = getGiornoDinamico(domaniprog, indexoggi +1, -1).getPasti(indexstatus);
                ricombinaPortata(pastodomani, portatediversedomani, indiciricombinadomani);
            }
            if (limitecalorie) {
                int fabrestante = giorno.getCalorie() - giornocorrente.getCalorie();
                if(indexstatus == 0){
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+1), fabrestante * 47/100, indexoggi);
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+2), fabrestante * 13/100, indexoggi);
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+3), fabrestante * 40/100, indexoggi);
                } else if (indexstatus == 1){
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+1), fabrestante * 25/100, indexoggi);
                    ricombinaCaloriaPasto(giorno.getPasti(indexstatus+2), fabrestante * 75/100, indexoggi);
                } else if (indexstatus == 2) ricombinaCaloriaPasto(giorno.getPasti(indexstatus+1), fabrestante, indexoggi);
            }

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
            String nomecibo = portatedisponibili.get(randomPortata(portatedisponibili.size()));
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
            int i = 0;
            for (int j = i; j <= indexstatus; j++) {
                giornodinamico.setPasti(i, giornocorrente.getPasti(j));
            }
            if(indexstatus>0){
                giornodinamico.setData(giornocorrente.getData());
            } else {
                giornodinamico.setData(giornocorrente.getData().plusDays(1));
                indexstatus++;
            }
            PastoModel pastomodel = new PastoModel();
            PortataModel portatamodel = new PortataModel();
            for (int k = indexstatus; k<4; k++){
                ArrayList<PortataObject> portate = giorno.getPasti(k).getPortate();
                PastoObject nuovopasto = new PastoObject(giorno.getPasti(k).getTipo());
                pastomodel.inserisciPasto(nuovopasto);
                for( PortataObject portata : portate){
                    PortataObject nuovaportata = new PortataObject(portata.getCibo());
                    nuovaportata.setId_pasto(nuovopasto.getId());
                    portatamodel.inserisciPortata(nuovaportata);
                    nuovopasto.addPortata(nuovaportata);
                }
                giornodinamico.setPasti(k, nuovopasto);
            }
            new GiornoAlimModel().inserisciGiornoAlimDinamico(giornodinamico);
            utente.getProgramma_alimentare().setSettimanaalimentare(indexstatus,giornodinamico);
            return giornodinamico;
        }
        return giorno;
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
