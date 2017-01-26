package Controller;

import Helpers.Controller;
import View.Alimentazione.*;
import Object.UtenteObject;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 25/01/17.
 */
public class ProgAlimController extends Controller {

    private UtenteObject utente;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private NewProgAlimView progalim;
    private ProgAlimManView progalimman;
    private GiornoAlimForm giornoselezionato;

    public ProgAlimController(AlimentazioneView alimentazione, UtenteObject utente) {

        this.utente = utente;
        progalim = new NewProgAlimView();
        mainPanel = progalim.getMainPanel();
        JPanel alimMainPanel =  alimentazione.getMainPanel();
        alimMainPanel.add(mainPanel,"NewProgAlimView");
        CardLayout alimCardLayout = (CardLayout)alimMainPanel.getLayout();
        alimCardLayout.show(alimMainPanel, "NewProgAlimView");;
        cardLayout = (CardLayout)mainPanel.getLayout();
        IndexProgAlimView indexprog = progalim.getIndexprogalimview();
        ProgAlimCombView progcomb = progalim.getProgalimcombview();
        //ProgAlimManView progman = progalim.getProgalimmanview();

        indexprog.addNewProgManButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progalimman = new ProgAlimManView();
                mainPanel.add(progalimman.getMainPanel(),"ProgAlimManView");
                cardLayout.show(mainPanel, "ProgAlimManView");
                giornoselezionato = progalimman.getTabView(0);

                progalimman.addTabbedSelectionListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        JTabbedPane pane = (JTabbedPane) e.getSource();
                        giornoselezionato = progalimman.getTabView(pane.getSelectedIndex());
                    }
                });
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
