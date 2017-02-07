package Controller;

import View.Auth;
import View.Menu;
import Object.UtenteObject;
import Helpers.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class MenuController extends Controller {
    private Menu menu;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private UtenteObject utente;

    public MenuController(Menu menu, UtenteObject utente)
    {
        this.menu=menu;
        this.utente=utente;
        variablePanel = menu.getVariablePanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        cardLayout.show(variablePanel,"AlimentazioneView");
        ProfiloController profiloController = new ProfiloController(menu, utente);
        AlimentazioneController alimentazioneController = new AlimentazioneController(menu, utente);
        AllenamentoController allenamentoController = new AllenamentoController(menu, utente);
        RiepilogoController riepilogoController = new RiepilogoController(menu,utente);
        //Auth.getFrame().pack();

        menu.addMenuButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuVisibility(menu.getMenuPanel());
            }
        });

        menu.addAlimentazioneButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "AlimentazioneView");
                alimentazioneController.showIndex();
                JPanel subMenu = menu.getSubMenuAlimPanel();
                SubMenuVisibility(subMenu);
            }
        });

        menu.addAllenamentoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "AllenamentoView");
                allenamentoController.showIndex();
                JPanel subMenu = menu.getSubMenuAllenPanel();
                SubMenuVisibility(subMenu);
            }
        });

        menu.addProfiloButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "ProfiloView");
                MenuVisibility(menu.getMenuPanel());
            }
        });

        menu.addRiepilogoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "RiepilogoView");
                MenuVisibility(menu.getMenuPanel());
            }
        });

        menu.addLogoutButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int replay = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler uscire da Health Me?", "Avviso", JOptionPane.YES_NO_OPTION);
                if (replay == JOptionPane.YES_OPTION){
                    Auth view = new Auth();
                    new PublicController(view);
                }
            }
        });

    }

}

