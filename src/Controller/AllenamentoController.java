package Controller;

import Model.EsercizioModel;
import Object.UtenteObject;
import Object.EsercizioObject;
import View.Allenamento.AllenamentoView;
import View.Allenamento.NewEsercizioView;
import View.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AllenamentoController extends BaseAllenController{

    private UtenteObject utente;
    private AllenamentoView allenamento;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;

    public AllenamentoController(Menu menu, UtenteObject utente) {

        this.utente = utente;
        this.allenamento = menu.getAllenamentoview();
        variablePanel = allenamento.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        NewEsercizioView newesercizio = allenamento.getNewesercizio();

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
    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAllenamentoView");
    }

}
