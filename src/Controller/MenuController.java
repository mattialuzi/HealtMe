package Controller;

import View.Alimentazione.AlimentazioneView;
import View.Menu;

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

    public MenuController(Menu menu)
    {
        this.menu=menu;
        menu.setVisible(true);
        variablePanel = menu.getVariablePanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        cardLayout.show(variablePanel,"AlimentazioneView");

        AlimentazioneView alimentazione = menu.getAlimentazioneview();

        menu.addMenuButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel menuPanel = menu.getMenuPanel();
                if (menuPanel.isVisible()) {
                    menuPanel.setVisible(false);
                } else
                    menuPanel.setVisible(true);
            }
        });

    }
}

