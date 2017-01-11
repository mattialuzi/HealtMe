package Controller;

import Helpers.Controller;
import View.Alimentazione.AlimentazioneView;
import View.Alimentazione.NewCiboView;
import View.Menu;

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
        cardLayout.show(variablePanel,"IndexAlimentazioneView");
        NewCiboView newcibo = alimentazione.getNewcibo();

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

    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }
}
