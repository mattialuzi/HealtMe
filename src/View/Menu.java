package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import View.Alimentazione.*;
import View.Allenamento.AllenamentoView;
import View.Profilo.ProfiloView;
import View.Riepilogo.RiepilogoView;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class Menu {

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
    private JPanel optionPanel;
    private JPanel alimbuttonPanel;
    private JPanel profilobuttonPanel;
    private JPanel allenbuttonPanel;
    private JPanel riepilogobuttonPanel;
    private JButton newProgAlimButton;
    private JButton newCiboButton;
    private JPanel subMenuAlimPanel;
    private JButton nuovoProgrAllenButton;
    private JButton nuovoEsercizioButton;
    private JPanel subMenuAllenPanel;
    private JButton logoutButton;
    private JPanel logoutbuttonPanel;
    private AlimentazioneView alimentazione;
    private AllenamentoView allenamento;
    private ProfiloView profilo;
    private RiepilogoView riepilogo;

    public Menu(JFrame frame) {

        frame.setContentPane(mainPanel);
        //da finire il bottone
        try {
            Image img = ImageIO.read(getClass().getResource("resources/menu1.png"));
            Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH );
            menuButton.setIcon(new ImageIcon(newimg));
            menuButton.setBorder(null);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        alimentazione = new AlimentazioneView();
        allenamento = new AllenamentoView();
        profilo = new ProfiloView();
        riepilogo = new RiepilogoView();
        alimentazionePanel = alimentazione.getMainPanel();
        allenamentoPanel = allenamento.getMainPanel();
        profiloPanel = profilo.getMainPanel();
        riepilogoPanel = riepilogo.getMainPanel();
        variablePanel.add(alimentazionePanel, "AlimentazioneView");
        variablePanel.add(allenamentoPanel, "AllenamentoView");
        variablePanel.add(profiloPanel, "ProfiloView");
        variablePanel.add(riepilogoPanel, "RiepilogoView");
        frame.pack();
        frame.setLocationRelativeTo(null);

    }

    public void addMenuButtonListener(ActionListener listener){
        menuButton.addActionListener(listener);
    }

    public void addAlimentazioneButtonListener(ActionListener listener){ alimentazioneButton.addActionListener(listener); }

    public void addNewProgAlimButtonListener(ActionListener listener){ newProgAlimButton.addActionListener(listener); }

    public void addNewCiboButtonListener(ActionListener listener){ newCiboButton.addActionListener(listener); }

    public void addAllenamentoButtonListener(ActionListener listener){
        allenamentoButton.addActionListener(listener);
    }

    public void addProfiloButtonListener(ActionListener listener){
        profiloButton.addActionListener(listener);
    }

    public void addRiepilogoButtonListener(ActionListener listener){
        riepilogoButton.addActionListener(listener);
    }

    public void addLogoutButtonListener(ActionListener listener){
        logoutButton.addActionListener(listener);
    }

    public JPanel getVariablePanel() {
        return variablePanel;
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }

    public JPanel getSubMenuAlimPanel() {
        return subMenuAlimPanel;
    }

    public JPanel getSubMenuAllenPanel() {
        return subMenuAllenPanel;
    }

    public AlimentazioneView getAlimentazioneview() {
        return alimentazione;
    }

    public AllenamentoView getAllenamentoview() {
        return allenamento;
    }

    public ProfiloView getProfiloview() {
        return profilo;
    }
}
