package Controller;

import Model.EsercizioModel;
import Model.GiornoAllenModel;
import Object.UtenteObject;
import Object.EsercizioObject;
import View.Allenamento.*;
import View.Menu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;

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

        menu.addNewProgAllenButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProgAllenController(allenamento,utente);
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

        giornocorrenteview.addListenersForRemoveButtons(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JTable tabellascelta = giornocorrenteview.getTableFromButton(e.getActionCommand());
                //removePortata(tabellascelta, e.getActionCommand());
                //((DefaultTableModel)tabellascelta.getModel()).removeRow(tabellascelta.getSelectedRow());
                JButton bottone = (JButton)e.getSource();
                bottone.setEnabled(false);
            }
        });

        dialog.addSetUnitaItemListener(new SetUnitaItemAction());

        dialog.addSearchKeyListener(new SearchKeyAction());

        dialog.addSetEsercizioListSelectionListener(new SetEsercizioListSelectionAction());

        dialog.addQuantitaKeyListener(new QuantitaKeyAction());

        dialog.addEsercizioEffettivoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable tabella = giornocorrenteview.getTable();
                //aggiungiEsercizioEffettivo((DefaultTableModel)tabella.getModel());
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
    }

}
