package Controller;

import View.Alimentazione.AlimentazioneView;
import View.Allenamento.AllenamentoView;
import View.Auth;
import View.Menu;
import Object.UtenteObject;
import View.Profilo.ProfiloView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class MenuController {
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
        AlimentazioneView alimentazione = menu.getAlimentazioneview();
        AllenamentoView allenamento = menu.getAllenamentoview();
        ProfiloView profilo = menu.getProfiloview();

        menu.addMenuButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuVisibility();
            }
        });

        menu.addAlimentazioneButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "AlimentazioneView");
                JPanel subMenu = menu.getSubMenuAlimPanel();
                SubMenuVisibility(subMenu);
                new AlimentazioneController(alimentazione);
            }
        });

        menu.addAllenamentoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "AllenamentoView");
                JPanel subMenu = menu.getSubMenuAllenPanel();
                SubMenuVisibility(subMenu);
            }
        });

        menu.addProfiloButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "ProfiloView");
                MenuVisibility();
                new ProfiloController(profilo, utente);
            }
        });

        menu.addRiepilogoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "RiepilogoView");
                MenuVisibility();
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

    public void MenuVisibility(){
        JPanel menuPanel = menu.getMenuPanel();
        if (menuPanel.isVisible()) {
            menuPanel.setVisible(false);
        } else
            menuPanel.setVisible(true);
    }

    public void SubMenuVisibility(JPanel subPanel){
        if (subPanel.isVisible()) {
            subPanel.setVisible(false);
        } else
            subPanel.setVisible(true);
    }

}

