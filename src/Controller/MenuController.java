package Controller;

import View.Alimentazione.AlimentazioneView;
import View.Menu;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class MenuController {
    private Menu menu;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;

    public MenuController(Menu menu)
    {
        this.menu=menu;
        menu.setVisible(true);
        mainPanel = menu.getMainPanel();
        cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel,"AlimentazioneView");

        AlimentazioneView alimentazione = menu.getAlimentazioneview();


    }

}
