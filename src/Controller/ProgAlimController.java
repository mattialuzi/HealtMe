package Controller;


import Model.CiboModel;
import Model.ProgrammaAlimentareModel;
import Model.UtenteModel;
import Object.Enum.PortataEnum;
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

/**
 * Created by lorenzobraconi on 25/01/17.
 */
public class ProgAlimController extends BaseAlimController {

    private UtenteObject utente;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private NewProgAlimView progalim;
    private ProgAlimManView progalimman;
    private GiornoAlimForm giornoselezionato;
    private AlimentazioneView alimentazione;

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
                cardLayout.show(mainPanel, "ProgAlimCombView");
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
                giornosettimana.setFabbisogno(fabbisogno);
            }
            utente.setProgramma_alimentare(nuovoprogmanuale);
            utente.setProg_alim_comb(false);

            new ProgrammaAlimentareModel().inserisciProgManuale(nuovoprogmanuale);

            UtenteModel utentemodel = new UtenteModel();
            HashMap<String, Object> campo = new HashMap<String, Object>();
            campo.put("programma_alimentare", utente.getProgramma_alimentare().getId());
            campo.put("prog_alim_comb", 0);
            utentemodel.updateInfoUtente(utente.getUsername(), campo);
        }

        private int calcolaCalorie(PortataObject portata){
            return portata.getQuantita()*(portata.getCibo().getKilocal())/100;
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
