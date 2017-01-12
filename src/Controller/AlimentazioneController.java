package Controller;

import Helpers.Controller;
import Model.CiboModel;
import View.Alimentazione.*;
import View.Menu;
import Object.CiboObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneController extends Controller {

    private AlimentazioneView alimentazione;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;

    public AlimentazioneController(Menu menu) {

        this.alimentazione = menu.getAlimentazioneview();
        variablePanel = alimentazione.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        showIndex();
        NewCiboView newcibo = alimentazione.getNewcibo();
        IndexAlimentazioneView indexalimentazione = alimentazione.getIndexalimentazione();
        FormCiboEffettivo dialog = new FormCiboEffettivo();

        menu.addNewProgAlimButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAlimPanel());
                cardLayout.show(variablePanel, "NewProgAlimView");
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

        indexalimentazione.addAggiungiCiboEffettivoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }
}
