package Controller;

import Helpers.Controller;
import View.Alimentazione.IndexProgAlimView;
import View.Alimentazione.NewProgAlimView;
import Object.UtenteObject;
import View.Alimentazione.ProgAlimCombView;
import View.Alimentazione.ProgAlimManView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 25/01/17.
 */
public class ProgAlimController extends Controller {

    private NewProgAlimView progalim;
    private UtenteObject utente;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;

    public ProgAlimController(NewProgAlimView progalim, UtenteObject utente) {

        this.progalim = progalim;
        this.utente = utente;
        mainPanel = progalim.getMainPanel();
        cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel, "IndexProgAlimView");
        IndexProgAlimView indexprog = progalim.getIndexprogalimview();
        ProgAlimCombView progcomb = progalim.getProgalimcombview();
        ProgAlimManView progman = progalim.getProgalimmanview();

        indexprog.addNewProgManButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "View.Alimentazione.GiornoAlimForm");
            }
        });

        indexprog.addNewProgCombButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "ProgAlimCombView");
            }
        });

    }
}
