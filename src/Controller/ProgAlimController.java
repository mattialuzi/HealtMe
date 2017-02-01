package Controller;


import Model.CiboModel;
import Model.ProgrammaAlimentareModel;
import Model.UtenteModel;
import Object.Enum.*;
import View.Alimentazione.*;
import Object.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by lorenzobraconi on 25/01/17.
 */
public class ProgAlimController extends BaseAlimController {

    private UtenteObject utente;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private NewProgAlimView progalim;
    private ProgAlimManView progalimman;
    private ProgAlimCombView progalimcomb;
    private GiornoAlimForm giornoselezionato;
    private AlimentazioneView alimentazione;
    private int fabbisogno;

    public ProgAlimController(AlimentazioneView alimentazione, UtenteObject utente) {

        this.utente = utente;
        progalim = new NewProgAlimView();
        this.alimentazione = alimentazione;
        mainPanel = progalim.getMainPanel();
        JPanel alimMainPanel =  alimentazione.getMainPanel();
        alimMainPanel.add(mainPanel,"NewProgAlimView");
        CardLayout alimCardLayout = (CardLayout)alimMainPanel.getLayout();
        alimCardLayout.show(alimMainPanel, "NewProgAlimView");
        cardLayout = (CardLayout)mainPanel.getLayout();
        IndexProgAlimView indexprog = progalim.getIndexprogalimview();
        ProgAlimCombView progcomb = progalim.getProgalimcombview();
        dialog = new FormCiboEffettivo();

        indexprog.addNewProgManButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progalimman = new ProgAlimManView();
                mainPanel.add(progalimman.getMainPanel(),"ProgAlimManView");
                cardLayout.show(mainPanel, "ProgAlimManView");
                giornoselezionato = progalimman.getTabView(0);
                dialog.addPortataEffettivaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        giornoselezionato.setTableFromButton();
                        JTable tabellascelta = giornoselezionato.getTableFromButton(e.getActionCommand());
                        aggiungiPortataManuale((DefaultTableModel)tabellascelta.getModel());
                        dialog.onCancel();
                    }
                });


                progalimman.addTabbedSelectionListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        JTabbedPane pane = (JTabbedPane) e.getSource();
                        giornoselezionato = progalimman.getTabView(pane.getSelectedIndex());
                        dialog.removePortataEffettivaButtonListener();
                        dialog.addPortataEffettivaButtonListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                giornoselezionato.setTableFromButton();
                                JTable tabellascelta = giornoselezionato.getTableFromButton(e.getActionCommand());
                                aggiungiPortataManuale((DefaultTableModel)tabellascelta.getModel());
                                dialog.onCancel();
                            }
                        });
                    }
                });

                for (int i = 0; i < 7; i++) {
                    GiornoAlimForm giorno = progalimman.getTabView(i);
                    giorno.addListenersAndshowButtons(new ListenersAndShowButtonsAction());

                    giorno.addTableSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if (e.getValueIsAdjusting()) {
                                giorno.setButtonFromTable();
                                JButton bottonescelto = giorno.getButtonFromTable((ListSelectionModel) e.getSource());
                                bottonescelto.setEnabled(true);
                            }
                        }
                    });

                    giorno.addListenersForRemoveButtons(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JTable tabellascelta = giorno.getTableFromButton(e.getActionCommand());
                            ((DefaultTableModel)tabellascelta.getModel()).removeRow(tabellascelta.getSelectedRow());
                            JButton bottone = (JButton)e.getSource();
                            bottone.setEnabled(false);
                        }
                    });
                }

                dialog.addSetPortataItemListener(new SetPortataItemAction());

                dialog.addSearchKeyListener(new SearchKeyAction());

                dialog.addSetCiboListSelectionListener(new SetCiboListSelectionAction());

                dialog.addQuantitaKeyListener(new QuantitaKeyAction());

                progalimman.addAnnullaProgrammaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.removePortataEffettivaButtonListener();
                        cardLayout.show(mainPanel, "IndexProgAlimView");
                    }
                });

                progalimman.addConfermaProgrammaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        aggiungiProgrammaManuale();
                        showNewProg();
                        alimCardLayout.show(alimMainPanel,"IndexAlimentazioneView");
                    }
                });

            }
        });

        indexprog.addNewProgCombButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progalimcomb = new ProgAlimCombView();
                mainPanel.add(progalimcomb.getMainPanel(),"ProgAlimCombView");
                cardLayout.show(mainPanel, "ProgAlimCombView");

                progalimcomb.addIndietroButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "IndexProgAlimView");
                    }
                });

                progalimcomb.addGeneraProgrammaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        generaProgramma();
                        showNewProg();
                        alimCardLayout.show(alimMainPanel,"IndexAlimentazioneView");
                    }
                });
            }
        });
    }
        public void aggiungiPortataManuale(DefaultTableModel tabellamodel) {
            String portata = dialog.getPortata().getSelectedItem().toString();
            String alimento = dialog.getNomeAlimento().getText();
            int quantita = Integer.parseInt(dialog.getQuantita().getText());
            if (aggiornaPortataManuale(tabellamodel,alimento,quantita)) {
            tabellamodel.addRow(new Object[]{portata, alimento, quantita});
            }
        }

        public boolean aggiornaPortataManuale(DefaultTableModel tabellamodel,String alimento,int quantita){
            boolean exit = true;
            int rowcount = tabellamodel.getRowCount();
            for(int indexrow = 0; indexrow != rowcount && exit; indexrow ++){
                if(tabellamodel.getValueAt(indexrow,1).equals(alimento)) {
                    int nuovaquantita = quantita+(Integer)tabellamodel.getValueAt(indexrow,2);
                    tabellamodel.setValueAt(nuovaquantita, indexrow, 2);
                    exit = false;
                }
            }
            return exit;
        }

        public void aggiungiProgrammaManuale(){
            ProgAlimManObject nuovoprogmanuale = new ProgAlimManObject();
            for(int i = 0; i<7 ; i++) {
                GiornoAlimProgObject giornosettimana = nuovoprogmanuale.getSettimanaalimentare(i);
                GiornoAlimForm giornosettimanaview = progalimman.getTabView(i);
                ArrayList<JTable> tabellegiorno = giornosettimanaview.getEffTables();
                int fabbisogno = 0;
                for (int j = 0; j < 4; j++) {
                    PastoObject pasto = giornosettimana.getPasti(j);
                    pasto.setTipoByIndex(j);
                    DefaultTableModel tabellamodel = (DefaultTableModel) tabellegiorno.get(j).getModel();
                    int rowcount = tabellamodel.getRowCount();
                    for(int indexrow = 0; indexrow < rowcount ; indexrow++){
                        CiboObject cibo = new CiboModel().getCiboByName(tabellamodel.getValueAt(indexrow,1).toString());
                        PortataObject portata = new PortataObject(cibo);
                        portata.setQuantita((Integer)tabellamodel.getValueAt(indexrow,2));
                        portata.setTipo(PortataEnum.valueOf(tabellamodel.getValueAt(indexrow,0).toString()));
                        pasto.getPortate().add(portata);
                        fabbisogno += calcolaCalorie(portata);
                    }
                }
                giornosettimana.setCalorie(fabbisogno);
            }
            utente.setProgramma_alimentare(nuovoprogmanuale);
            utente.setProg_alim_comb(false);
            new ProgrammaAlimentareModel().inserisciProgrammaManuale(nuovoprogmanuale);
            UtenteModel utentemodel = new UtenteModel();
            HashMap<String, Object> campo = new HashMap<String, Object>();
            campo.put("programma_alimentare", utente.getProgramma_alimentare().getId());
            campo.put("prog_alim_comb", 0);
            utentemodel.updateInfoUtente(utente.getUsername(), campo);
        }

        private void generaProgramma(){
            fabbisogno = calcolaFabbisogno();
            CiboModel cibomodel = new CiboModel();
            String tipoalim = progalimcomb.getTipoalimBox().getSelectedItem().toString();
            String allergia = utente.getAllergia().toString();
            ArrayList<String> snackcolazione = cibomodel.getCiboForUser(allergia,tipoalim,"snack", new String[] {"colazione","colazione_spuntino"});
            ArrayList<String> snackspuntino = cibomodel.getCiboForUser(allergia,tipoalim,"snack", new String[] {"spuntino","colazione_spuntino"});
            ArrayList<String> frutta = cibomodel.getCiboForUser(allergia,tipoalim,"frutta", new String[] {"tutti"});
            ArrayList<String> primopranzo = cibomodel.getCiboForUser(allergia,tipoalim,"primo", new String[] {"pranzo","pranzo_cena"});
            ArrayList<String> primocena = cibomodel.getCiboForUser(allergia,tipoalim,"primo", new String[] {"cena","pranzo_cena"});
            ArrayList<String> secondo = cibomodel.getCiboForUser(allergia,tipoalim,"secondo", new String[] {"pranzo_cena"});
            ArrayList<String> contorno = cibomodel.getCiboForUser(allergia,tipoalim,"contorno", new String[] {"pranzo_cena"});
            ArrayList<String> dolci = cibomodel.getCiboForUser(allergia,tipoalim,"dolce", new String[] {"pranzo_cena"});
            ArrayList<String> bevandapranzocena = cibomodel.getCiboForUser(allergia,tipoalim,"bevanda", new String[] {"pranzo_cena","tutti"});
            ArrayList<String> bevandacolazione = cibomodel.getCiboForUser(allergia,tipoalim,"bevanda", new String[] {"colazione","colazione_spuntino","tutti"});
            ArrayList<String> bevandaspuntino = cibomodel.getCiboForUser(allergia,tipoalim,"bevanda", new String[] {"spuntino","colazione_spuntino","tutti"});
            ArrayList<GiornoAlimProgObject> giorniProgComb = new ArrayList<GiornoAlimProgObject>();
            for (int i=1; i<7; i++) {
                if (i % 2 == 0)
                    giorniProgComb.add(generaGiorniPari(snackcolazione, snackspuntino, frutta, primopranzo, secondo, contorno, bevandapranzocena, bevandacolazione, bevandaspuntino, primocena));
                else
                    giorniProgComb.add(generaGiorniDispari(snackcolazione, snackspuntino, frutta, primopranzo, secondo, contorno, bevandapranzocena, bevandacolazione, bevandaspuntino));
            }
            giorniProgComb.add(generaDomenica(snackcolazione, snackspuntino, frutta, primopranzo, secondo, contorno, bevandapranzocena, bevandacolazione, bevandaspuntino, dolci));
            ProgAlimCombObject nuovoprogcombinato = new ProgAlimCombObject(giorniProgComb, fabbisogno, AlimentazioneEnum.valueOf(tipoalim));
            utente.setProgramma_alimentare(nuovoprogcombinato);
            utente.setProg_alim_comb(true);
            new ProgrammaAlimentareModel().inserisciProgrammaCombinato(nuovoprogcombinato);
            UtenteModel utentemodel = new UtenteModel();
            HashMap<String, Object> campo = new HashMap<String, Object>();
            campo.put("programma_alimentare", utente.getProgramma_alimentare().getId());
            campo.put("prog_alim_comb", 1);
            utentemodel.updateInfoUtente(utente.getUsername(), campo);
        }

        private int calcolaFabbisogno(){
            Float pesoforma = utente.getPeso_forma();
            int sesso = utente.getSesso();
            int eta = utente.getEta();
            LavoroEnum lavoro = utente.getLavoro();
            LivelloAttivitaFisicaEnum attivitafisica = utente.getLivello_attivita_fisica();
            float mb;
            float laf;
            if(sesso == 1) {
                TreeMap<Integer, Float> uomomap = new TreeMap<Integer, Float>();
                uomomap.put(10, (15.3f * pesoforma) + 679);
                uomomap.put(30, (11.6f * pesoforma) + 879);
                uomomap.put(60, (11.9f * pesoforma) + 700);
                uomomap.put(74, (8.4f * pesoforma) + 819);
                mb = uomomap.floorEntry(eta).getValue();
                if(attivitafisica.equals(LivelloAttivitaFisicaEnum.assente) || attivitafisica.equals(LivelloAttivitaFisicaEnum.leggero)){
                    HashMap<LavoroEnum,Float> uomohash = new HashMap<LavoroEnum,Float>();
                    uomohash.put(LavoroEnum.leggero,1.41f);
                    uomohash.put(LavoroEnum.moderato,1.70f);
                    uomohash.put(LavoroEnum.pesante,2.01f);
                    laf=uomohash.get(lavoro);
                } else {
                    HashMap<LavoroEnum,Float> uomohash = new HashMap<LavoroEnum,Float>();
                    uomohash.put(LavoroEnum.leggero,1.55f);
                    uomohash.put(LavoroEnum.moderato,1.78f);
                    uomohash.put(LavoroEnum.pesante,2.10f);
                    laf=uomohash.get(lavoro);
                }
            } else {
                TreeMap<Integer, Float> donnamap = new TreeMap<Integer, Float>();
                donnamap.put(10, (14.7f * pesoforma) + 496);
                donnamap.put(30, (8.7f * pesoforma) + 829);
                donnamap.put(60, (9.2f * pesoforma) + 688);
                donnamap.put(74, (9.8f * pesoforma) + 624);
                mb = donnamap.floorEntry(eta).getValue();
                if(attivitafisica.equals(LivelloAttivitaFisicaEnum.assente) || attivitafisica.equals(LivelloAttivitaFisicaEnum.leggero)){
                    HashMap<LavoroEnum,Float> donnahash = new HashMap<LavoroEnum,Float>();
                    donnahash.put(LavoroEnum.leggero,1.42f);
                    donnahash.put(LavoroEnum.moderato,1.56f);
                    donnahash.put(LavoroEnum.pesante,1.73f);
                    laf=donnahash.get(lavoro);
                } else {
                    HashMap<LavoroEnum,Float> donnahash = new HashMap<LavoroEnum,Float>();
                    donnahash.put(LavoroEnum.leggero,1.56f);
                    donnahash.put(LavoroEnum.moderato,1.64f);
                    donnahash.put(LavoroEnum.pesante,1.82f);
                    laf=donnahash.get(lavoro);
                }
            }
            return Math.round(mb*laf);
        }

        private GiornoAlimProgObject generaGiorniDispari(ArrayList<String> snackcolazione, ArrayList<String> snackspuntino, ArrayList<String> frutta, ArrayList<String> primopranzo, ArrayList<String> secondo, ArrayList<String> contorno, ArrayList<String> bevandapranzocena, ArrayList<String> bevandacolazione, ArrayList<String> bevandaspuntino){
            ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
            pasti.add(generaColazione(snackcolazione, frutta, bevandacolazione));
            pasti.add(generaPranzo(primopranzo, secondo, contorno, bevandapranzocena, frutta));
            pasti.add(generaCenaDispari(secondo, contorno, frutta, bevandapranzocena));
            pasti.add(generaSpuntino(snackspuntino, frutta, bevandaspuntino));
            return new GiornoAlimProgObject(pasti, fabbisogno);
        }

        private GiornoAlimProgObject generaGiorniPari(ArrayList<String> snackcolazione, ArrayList<String> snackspuntino, ArrayList<String> frutta, ArrayList<String> primopranzo, ArrayList<String> secondo, ArrayList<String> contorno, ArrayList<String> bevandapranzocena, ArrayList<String> bevandacolazione, ArrayList<String> bevandaspuntino, ArrayList<String> primocena){
            ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
            pasti.add(generaColazione(snackcolazione, frutta, bevandacolazione));
            pasti.add(generaPranzo(primopranzo, secondo, contorno, bevandapranzocena, frutta));
            pasti.add(generaCenaPari(secondo, primocena, frutta, bevandapranzocena));
            pasti.add(generaSpuntino(snackspuntino, frutta, bevandaspuntino));
            return new GiornoAlimProgObject(pasti, fabbisogno);
        }

        private GiornoAlimProgObject generaDomenica(ArrayList<String> snackcolazione, ArrayList<String> snackspuntino, ArrayList<String> frutta, ArrayList<String> primopranzo, ArrayList<String> secondo, ArrayList<String> contorno, ArrayList<String> bevandapranzocena, ArrayList<String> bevandacolazione, ArrayList<String> bevandaspuntino, ArrayList<String> dolci){
            ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
            pasti.add(generaColazione(snackcolazione, frutta, bevandacolazione));
            pasti.add(generaPranzoDolce(primopranzo, secondo, contorno, bevandapranzocena, dolci));
            pasti.add(generaCenaDispari(secondo, contorno, frutta, bevandapranzocena));
            pasti.add(generaSpuntino(snackspuntino, frutta, bevandaspuntino));
            return new GiornoAlimProgObject(pasti, fabbisogno);
        }

        private PastoObject generaColazione(ArrayList<String> snack, ArrayList<String> frutta, ArrayList<String> bevanda){
            ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
            int fabsnack = (fabbisogno*10)/100;
            portate.add(generaPortata(snack, fabsnack, PortataEnum.snack));
            int fabfrutta = (fabbisogno*10)/100;
            portate.add(generaPortata(frutta,fabfrutta, PortataEnum.frutta));
            int fabbevanda = (fabbisogno*5)/100;
            portate.add(generaPortata(bevanda,fabbevanda, PortataEnum.bevanda));
            return new PastoObject(portate, PastoEnum.colazione);
        }

        private PastoObject generaPranzo(ArrayList<String> primo, ArrayList<String> secondo, ArrayList<String> contorno, ArrayList<String> bevanda, ArrayList<String> frutta){
            ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
            int fabprimo = (fabbisogno*15)/100;
            portate.add(generaPortata(primo, fabprimo, PortataEnum.primo));
            int fabsecondo = (fabbisogno*10)/100;
            portate.add(generaPortata(secondo,fabsecondo, PortataEnum.secondo));
            int fabcontorno = (fabbisogno*5)/100;
            portate.add(generaPortata(contorno,fabcontorno, PortataEnum.contorno));
            int fabbevanda = (fabbisogno*2)/100;
            portate.add(generaPortata(bevanda, fabbevanda, PortataEnum.bevanda));
            int fabfrutta = (fabbisogno*3)/100;
            portate.add(generaPortata(frutta, fabfrutta, PortataEnum.frutta));
            return new PastoObject(portate, PastoEnum.pranzo);
        }

        private PastoObject generaPranzoDolce(ArrayList<String> primo, ArrayList<String> secondo, ArrayList<String> contorno, ArrayList<String> bevanda, ArrayList<String> dolce){
            ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
            int fabprimo = (fabbisogno*13)/100;
            portate.add(generaPortata(primo, fabprimo, PortataEnum.primo));
            int fabsecondo = (fabbisogno*9)/100;
            portate.add(generaPortata(secondo,fabsecondo, PortataEnum.secondo));
            int fabcontorno = (fabbisogno*4)/100;
            portate.add(generaPortata(contorno,fabcontorno, PortataEnum.contorno));
            int fabbevanda = (fabbisogno*2)/100;
            portate.add(generaPortata(bevanda, fabbevanda, PortataEnum.bevanda));
            int fabdolce = (fabbisogno*7)/100;
            portate.add(generaPortata(dolce, fabdolce, PortataEnum.dolce));
            return new PastoObject(portate, PastoEnum.pranzo);
        }

        private PastoObject generaCenaDispari(ArrayList<String> secondo, ArrayList<String> contorno, ArrayList<String> frutta, ArrayList<String> bevanda){
            ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
            int fabsecondo = (fabbisogno*15)/100;
            portate.add(generaPortata(secondo,fabsecondo, PortataEnum.secondo));
            int fabcontorno = (fabbisogno*10)/100;
            portate.add(generaPortata(contorno,fabcontorno, PortataEnum.contorno));
            int fabfrutta = (fabbisogno*3)/100;
            portate.add(generaPortata(frutta, fabfrutta, PortataEnum.frutta));
            int fabbevanda = (fabbisogno*2)/100;
            portate.add(generaPortata(bevanda, fabbevanda, PortataEnum.bevanda));
            return new PastoObject(portate, PastoEnum.cena);
        }

        private PastoObject generaCenaPari(ArrayList<String> secondo, ArrayList<String> primo, ArrayList<String> frutta, ArrayList<String> bevanda){
            ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
            int fabsecondo = (fabbisogno*15)/100;
            portate.add(generaPortata(secondo,fabsecondo, PortataEnum.secondo));
            int fabprimo = (fabbisogno*10)/100;
            portate.add(generaPortata(primo,fabprimo, PortataEnum.primo));
            int fabfrutta = (fabbisogno*3)/100;
            portate.add(generaPortata(frutta, fabfrutta, PortataEnum.frutta));
            int fabbevanda = (fabbisogno*2)/100;
            portate.add(generaPortata(bevanda, fabbevanda, PortataEnum.bevanda));
            return new PastoObject(portate, PastoEnum.cena);
        }

        private PastoObject generaSpuntino(ArrayList<String> snack, ArrayList<String> frutta, ArrayList<String> bevanda){
            ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
            int fabsnack = (fabbisogno*5)/100;
            portate.add(generaPortata(snack, fabsnack, PortataEnum.snack));
            int fabfrutta = (fabbisogno*3)/100;
            portate.add(generaPortata(frutta,fabfrutta, PortataEnum.frutta));
            int fabbevanda = (fabbisogno*2)/100;
            portate.add(generaPortata(bevanda,fabbevanda, PortataEnum.bevanda));
            return new PastoObject(portate, PastoEnum.spuntino);
        }

        private PortataObject generaPortata(ArrayList<String> portate, int calorie, PortataEnum tipoportata){
            int randomindex = randomPortata(portate.size());
            CiboObject cibo = new CiboModel().getCiboByName(portate.get(randomindex));
            int quantita = calcolaQuantita(calorie, cibo.getKilocal());
            return new PortataObject(cibo, tipoportata, quantita);
        }

        private int randomPortata(int size){
            return ThreadLocalRandom.current().nextInt(0, size);
        }

        private int calcolaQuantita(int calorie, int kilocal){
            return (calorie*100)/kilocal;
        }

        private void showNewProg () {
            IndexAlimentazioneView indexalim = alimentazione.getIndexalimentazione();
            ProgrammaAlimentareObject progalim = utente.getProgramma_alimentare();
            for (int i=1; i<=7; i++) {
                GiornoAlimView giornoview = indexalim.getGiorni(DayOfWeek.of(i));
                GiornoAlimObject giorno = progalim.getSettimanaalimentare(i-1);
                removePasti(giornoview, giorno.getTipo());
                showPasti(giorno,giornoview);
            }
        }
}
