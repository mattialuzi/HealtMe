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


    private static JFrame frame = new JFrame("Health Me!");
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JButton menuButton;
    private JPanel buttonPanel;
    private JPanel sectionPanel;
    private JPanel variablePanel;
    private JPanel alimentazionePanel;
    private JPanel allenamentoPanel;
    private JPanel profiloPanel;
    private JPanel riepilogoPanel;
    private JButton profiloButton;
    private JButton riepilogoButton;
    private JButton allenamentoButton;
    private JButton alimentazioneButton;
    private AlimentazioneView alimentazione;

    public Menu() {

        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        alimentazione = new AlimentazioneView();
        alimentazionePanel = alimentazione.getMainPanel();

        variablePanel.add(alimentazionePanel, "AlimentazioneView");
        variablePanel.add(allenamentoPanel, "AllenamentoView");
        variablePanel.add(profiloPanel, "ProfiloView");
        variablePanel.add(riepilogoPanel, "RiepilogoView");
    }

    public void addMenuButtonListener(ActionListener listener){
        menuButton.addActionListener(listener);
    }

    public static void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JPanel getVariablePanel() {
        return variablePanel;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public AlimentazioneView getAlimentazioneview() {
        return alimentazione;
    }
}
