package Controller;

import Object.UtenteObject;
import View.Allenamento.AllenamentoView;
import View.Allenamento.IndexProgAllenView;
import View.Allenamento.NewProgAllenView;
import View.Allenamento.ProgAllenCombView;
import View.Allenamento.ProgAllenManView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 06/02/17.
 */
public class ProgAllenController extends BaseAllenController {

    private UtenteObject utente;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AllenamentoView allenamento;
    private NewProgAllenView progallen;
    private ProgAllenManView progallenman;
    private ProgAllenCombView progallencomb;

    public ProgAllenController(AllenamentoView allenamento ,UtenteObject utente) {
        this.allenamento = allenamento;
        this.utente = utente;
        progallen = new NewProgAllenView();
        mainPanel = progallen.getMainPanel();
        JPanel allenMainPanel = allenamento.getMainPanel();
        allenMainPanel.add(mainPanel,"NewProgAllenView");
        cardLayout = (CardLayout)mainPanel.getLayout();
        CardLayout allenCardLayout = (CardLayout)allenMainPanel.getLayout();
        allenCardLayout.show(allenMainPanel, "NewProgAllenView");
        cardLayout = (CardLayout)mainPanel.getLayout();
        IndexProgAllenView indexprog = progallen.getIndexprogallenview();

        indexprog.addNewProgManButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progallenman = new ProgAllenManView();
                mainPanel.add(progallenman.getMainPanel(),"ProgAllenManView");
                cardLayout.show(mainPanel, "ProgAllenManView");
            }
        });

        indexprog.addNewProgCombButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                progallencomb = new ProgAllenCombView();
                mainPanel.add(progallencomb.getMainPanel(),"ProgAllenCombView");
                cardLayout.show(mainPanel, "ProgAllenCombView");
            }
        });
    }
}
