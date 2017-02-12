package Presenter;

import View.Auth;
import View.Menu;
import Object.UtenteObject;
import Helpers.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class MenuPresenter extends Presenter {
    private Menu menu;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;
    private UtenteObject utente;

    public MenuPresenter(Menu menu, UtenteObject utente)
    {
        this.menu=menu;
        this.utente=utente;
        variablePanel = menu.getVariablePanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        cardLayout.show(variablePanel,"AlimentazioneView");
        ProfiloPresenter profiloPresenter = new ProfiloPresenter(menu, utente);
        AlimentazionePresenter alimentazionePresenter = new AlimentazionePresenter(menu, utente);
        AllenamentoPresenter allenamentoPresenter = new AllenamentoPresenter(menu, utente);
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
                alimentazionePresenter.showIndex();
                JPanel subMenu = menu.getSubMenuAlimPanel();
                SubMenuVisibility(subMenu);
            }
        });

        menu.addAllenamentoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(variablePanel, "AllenamentoView");
                allenamentoPresenter.showIndex();
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
                new RiepilogoPresenter(menu,utente);
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
                    new PublicPresenter(view);
                }
            }
        });

    }

}

