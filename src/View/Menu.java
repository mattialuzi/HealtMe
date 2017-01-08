package View;

import javax.swing.*;
import View.Alimentazione.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class Menu {


    private static JFrame frame = new JFrame("Benvenuto in Healt Me!");
    private JPanel mainPanel;
    private JPanel alimentazionePanel;
    private JPanel allenamentoPanel;
    private JPanel profiloPanel;
    private JPanel riepilogoPanel;
    private AlimentazioneView alimentazione;

    public Menu() {
        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(500,300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        alimentazione = new AlimentazioneView();
        alimentazionePanel = alimentazione.getMainPanel();

        mainPanel.add(alimentazionePanel,"AlimentazioneView");
        mainPanel.add(allenamentoPanel, "AllenamentoView");
        mainPanel.add(profiloPanel,"ProfiloView");
        mainPanel.add(riepilogoPanel,"RiepilogoView");

    }

    public static void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public AlimentazioneView getAlimentazioneview() {
        return alimentazione;
    }
}
