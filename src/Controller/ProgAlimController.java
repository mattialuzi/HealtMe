package Controller;


import View.Alimentazione.*;
import Object.UtenteObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public ProgAlimController(AlimentazioneView alimentazione, UtenteObject utente) {

        this.utente = utente;
        progalim = new NewProgAlimView();
        mainPanel = progalim.getMainPanel();
        JPanel alimMainPanel =  alimentazione.getMainPanel();
        alimMainPanel.add(mainPanel,"NewProgAlimView");
        CardLayout alimCardLayout = (CardLayout)alimMainPanel.getLayout();
        alimCardLayout.show(alimMainPanel, "NewProgAlimView");
        cardLayout = (CardLayout)mainPanel.getLayout();
        IndexProgAlimView indexprog = progalim.getIndexprogalimview();
        ProgAlimCombView progcomb = progalim.getProgalimcombview();
        //ProgAlimManView progman = progalim.getProgalimmanview();
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

                        }
                    });
                }

                dialog.addSetPortataItemListener(new SetPortataItemAction());

                dialog.addSearchKeyListener(new SearchKeyAction());

                dialog.addSetCiboListSelectionListener(new SetCiboListSelectionAction());

                dialog.addQuantitaKeyListener(new QuantitaKeyAction());


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
}
