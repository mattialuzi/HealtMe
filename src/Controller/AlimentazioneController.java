package Controller;

import View.Alimentazione.AlimentazioneView;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneController {

    private AlimentazioneView alimentazione;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;

    public AlimentazioneController(AlimentazioneView alimentazione) {

        this.alimentazione = alimentazione;
        variablePanel = alimentazione.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        cardLayout.show(variablePanel,"IndexAlimentazioneView");

    }
}
