package Controller;

import Object.UtenteObject;
import View.Allenamento.AllenamentoView;
import View.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AllenamentoController extends BaseAllenController{

    private UtenteObject utente;
    private AllenamentoView allenamento;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;

    public AllenamentoController(Menu menu, UtenteObject utente) {

        this.utente = utente;
        this.allenamento = menu.getAllenamentoview();
        variablePanel = allenamento.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();

        menu.addNewEsercizioButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAllenPanel());
                cardLayout.show(variablePanel, "NewEsercizioView");
            }
        });
    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAllenamentoView");
    }

}
