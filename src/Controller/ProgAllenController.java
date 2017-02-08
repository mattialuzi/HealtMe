package Controller;

import Model.EsercizioModel;
import Model.ProgressiModel;
import Model.UtenteModel;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import Object.ProgAllenCombObject;
import Object.GiornoAllenProgObject;
import Object.EsercizioObject;
import Object.AttivitaObject;
import View.Allenamento.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lorenzobraconi on 06/02/17.
 */
public class ProgAllenController extends BaseAllenController {

    private UtenteObject utente;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AllenamentoView allenamento;
    private NewProgAllenView progallen;
    private ProgAllenManView progallenman;
    private ProgAllenCombView progallencomb;
    private GiornoAllenForm giornoselezionato;
    private FormEserciziPraticati dialogpraticati;
    //private ArrayList<String> esercizipraticati = new ArrayList<>();
    private GiornoAllenView giornocorrenteview;


    public ProgAllenController(AllenamentoView allenamento ,UtenteObject utente) {
        this.allenamento = allenamento;
        this.utente = utente;
        progallen = new NewProgAllenView();
        mainPanel = progallen.getMainPanel();
        JPanel allenMainPanel = allenamento.getMainPanel();
        allenMainPanel.add(mainPanel, "NewProgAllenView");
        cardLayout = (CardLayout) mainPanel.getLayout();
        CardLayout allenCardLayout = (CardLayout) allenMainPanel.getLayout();
        allenCardLayout.show(allenMainPanel, "NewProgAllenView");
        cardLayout = (CardLayout) mainPanel.getLayout();
        IndexProgAllenView indexprog = progallen.getIndexprogallenview();
        dialog = new FormEsercizioEffettivo();
        dialogpraticati = new FormEserciziPraticati();
        giornocorrenteview = allenamento.getIndexallenamento().getGiorni(LocalDate.now().getDayOfWeek());

        indexprog.addNewProgManButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progallenman = new ProgAllenManView();
                mainPanel.add(progallenman.getMainPanel(), "ProgAllenManView");
                cardLayout.show(mainPanel, "ProgAllenManView");
                giornoselezionato = progallenman.getTabView(0);


                for (int i = 0; i < 7; i++) {
                    GiornoAllenForm giorno = progallenman.getTabView(i);
                    giorno.addListenersAndShowButtons(new ListenersAndShowButtonsAction());
                    giorno.addTableSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if (e.getValueIsAdjusting()) {
                                JButton bottonescelto = giorno.getRemoveSeduta();
                                bottonescelto.setEnabled(true);
                            }
                        }
                    });

                    giorno.addListenersForRemoveButtons(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JTable tabella = giorno.getSedutaEffTable();
                            ((DefaultTableModel)tabella.getModel()).removeRow(tabella.getSelectedRow());
                            JButton bottone = (JButton)e.getSource();
                            bottone.setEnabled(false);
                        }
                    });
                }

                dialog.addSetUnitaItemListener(new SetUnitaItemAction());

                dialog.addSearchKeyListener(new SearchKeyAction());

                dialog.addSetEsercizioListSelectionListener(new SetEsercizioListSelectionAction());

                dialog.addQuantitaKeyListener(new QuantitaKeyAction());

                progallenman.addAnnullaProgrammaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.removeAttivitaEffettivaButtonListener();
                        cardLayout.show(mainPanel, "IndexProgAllenView");
                    }
                });

                progallenman.addConfermaProgrammaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //aggiungiProgrammaManuale();
                        //showNewProg();
                        //giornocorrenteview.visibilityConfermaAndAddButtons(utente.isProg_alim_comb(), status);
                        allenCardLayout.show(allenMainPanel, "IndexAllenamentoView");
                    }
                });
            }
        });

        indexprog.addNewProgCombButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progallencomb = new ProgAllenCombView();
                mainPanel.add(progallencomb.getMainPanel(), "ProgAllenCombView");
                cardLayout.show(mainPanel, "ProgAllenCombView");

                JList listaesercizi = progallencomb.getListaEsercizi();
                listaesercizi.setModel(new DefaultListModel());
                DefaultListModel listamodel =(DefaultListModel)listaesercizi.getModel();
                listaesercizi.setModel(listamodel);

                progallencomb.addPlusButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialogpraticati.setLocationRelativeTo(null);
                        dialogpraticati.getUnitadimisuraBox().setModel(new DefaultComboBoxModel(new String[]{"--scegli esercizio per svolgimento--","minuti","kilometri","ripetizioni"}));
                        dialogpraticati.setTitle("Inserisci esercizio praticato");
                        dialogpraticati.pack();
                        dialogpraticati.setVisible(true);
                    }
                });

                progallencomb.addMinusButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedIndex = listaesercizi.getSelectedIndex();
                        if (selectedIndex != -1) {
                            listamodel.remove(selectedIndex);
                        }
                    }
                });

                progallencomb.addIndietroButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        cardLayout.show(mainPanel, "IndexProgAllenView");
                    }
                });

                progallencomb.addGeneraProgrammaButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        generaProgramma();
                        //showNewProg();
                        //giornocorrenteview.visibilityConfermaAndAddButtons(utente.isProg_alim_comb());
                        allenCardLayout.show(allenMainPanel,"IndexAllenamentoView");
                    }
                });

                dialogpraticati.addEsercizioEffettivoButtonListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String esercizio = dialogpraticati.getNomeEsercizio().getText();
                        if (aggiornaEsercizioPraticato(listamodel,esercizio)) {
                        listamodel.addElement(esercizio);
                        }
                        dialogpraticati.onCancel();
                    }
                });

                dialogpraticati.addSetUnitaItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange()== ItemEvent.SELECTED) {
                            showEserciziPraticati();
                            dialogpraticati.getScrollPane().setVisible(true);
                            dialogpraticati.pack();
                        }
                    }
                });
                dialogpraticati.addSearchKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }
                    @Override
                    public void keyPressed(KeyEvent e) {
                    }
                    @Override
                    public void keyReleased(KeyEvent e) {
                        filtraEserciziPraticati();
                    }
                });

                dialogpraticati.addSetEsercizioListSelectionListener(new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting()) {
                            dialogpraticati.getNomeEsercizio().setText(dialogpraticati.getListaEsercizi().getSelectedValue().toString());
                        }
                        if (!dialogpraticati.getListaEsercizi().isSelectionEmpty())
                            dialogpraticati.getButtonOK().setEnabled(true);
                        else dialogpraticati.getButtonOK().setEnabled(false);
                    }
                });


            }
        });
    }


    public boolean aggiornaEsercizioPraticato(DefaultListModel listamodel,String esercizio){
        boolean exit = true;
        if(listamodel.contains(esercizio)) {
            JOptionPane.showMessageDialog(null,"Esercizio Praticato già inserito");
            exit = false;
        }
        return exit;
    }

    public void showEserciziPraticati(){
        String unitascelta = dialogpraticati.getUnitadimisuraBox().getSelectedItem().toString();
        JTextField nomeEsercizio = dialogpraticati.getNomeEsercizio();
        dialogpraticati.getUnitadimisuraBox().removeItem("--scegli esercizio per svolgimento--");
        nomeEsercizio.setEnabled(true);
        nomeEsercizio.setText("");
        EsercizioModel eserciziomodel = new EsercizioModel();
        esercizi = eserciziomodel.getEserciziByUnita(unitascelta);
        JList lista = dialogpraticati.getListaEsercizi();
        DefaultListModel listmodel = new DefaultListModel();
        try {
            while(esercizi.next()){
                listmodel.addElement(esercizi.getString("tipologia"));
            }
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
        lista.setModel(listmodel);
    }

    public void filtraEserciziPraticati(){
        String input = dialogpraticati.getNomeEsercizio().getText();
        DefaultListModel listafiltrata = new DefaultListModel();
        try {
            esercizi.beforeFirst();
            while(esercizi.next()){
                if(esercizi.getString("tipologia").contains(input)){
                    listafiltrata.addElement(esercizi.getString("tipologia"));
                }
            }
            dialogpraticati.getListaEsercizi().setModel(listafiltrata);
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
    }

    //inizio

    private void generaProgramma () {
        ArrayList<String> esercizipraticati = getEserciziPraticati();
        int disponibilita = getDisponibilita();
        LivelloAttivitaFisicaEnum livello = utente.getLivello_attivita_fisica();

        HashMap <LivelloAttivitaFisicaEnum, Integer> livellomap = new HashMap<>();
        livellomap.put(LivelloAttivitaFisicaEnum.assente,160);
        livellomap.put(LivelloAttivitaFisicaEnum.leggero, 160);
        livellomap.put(LivelloAttivitaFisicaEnum.moderato, 230);
        livellomap.put(LivelloAttivitaFisicaEnum.intenso, 500 );
        int caloriegiornaliere = livellomap.get(livello);

        float pesofactor = 0.07f;
        double differenza = utente.getPeso() - utente.getPeso_forma();
        if (Math.abs(differenza) > 10)
            caloriegiornaliere -= differenza*pesofactor;

        ProgAllenCombObject nuovoprogcombinato = new ProgAllenCombObject();
        EsercizioModel eserciziomodel = new EsercizioModel();
        int j= 5;
        int k= 0;
        for (int i=0; k < disponibilita; i=(i+2)%j) {
            int randomindex = randomize(esercizipraticati.size());
            GiornoAllenProgObject giorno = nuovoprogcombinato.getSettimanaallenamento(i);
            EsercizioObject nuovoesercizio = eserciziomodel.getEsercizioByTipologia(esercizipraticati.get(randomindex));
            AttivitaObject nuovaattivita = new AttivitaObject(nuovoesercizio);
            nuovaattivita.setQuantita(caloriegiornaliere/nuovoesercizio.getConsumo_calorico());
            giorno.getSeduta().addAttivita(nuovaattivita);
            if (i==1) j++;
            k++;
        }
        utente.setProgramma_allenamento(nuovoprogcombinato);
        utente.setProg_allen_comb(true);
        //new ProgrammaAllenModel().inserisciProgrammaCombinato(nuovoprogcombinato);
        UtenteModel utentemodel = new UtenteModel();
        HashMap<String, Object> campo = new HashMap<String, Object>();
        campo.put("programma_alimentare", utente.getProgramma_allenamento().getId());
        campo.put("prog_allen_comb", 1);
        utentemodel.updateInfoUtente(utente.getUsername(), campo);
        new ProgressiModel().updateInfoProgressi(utente.getUsername(), LocalDate.now(),"calorie_da_consumare",String.valueOf(nuovoprogcombinato.getCalorie_da_consumare()));





    }

    private ArrayList<String> getEserciziPraticati() {
        ArrayList<String> esercizipraticati = new ArrayList<>();
        ListModel listmodel = progallencomb.getListaEsercizi().getModel();
        int size = listmodel.getSize();
        for (int i=0; i<size; i++) {
            esercizipraticati.add(String.valueOf(listmodel.getElementAt(i)));
        }
        return esercizipraticati;
    }

    private int getDisponibilita() {
        return Integer.parseInt(String.valueOf(progallencomb.getComboDisponibilita().getModel().getSelectedItem()));
    }
}
