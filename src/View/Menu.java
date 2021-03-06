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
 *  La classe Menu contiene attributi e metodi associati al file XML Menu.form
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
    private JButton newProgAllenButton;
    private JButton newEsercizioButton;
    private JPanel subMenuAllenPanel;
    private JButton logoutButton;
    private JPanel logoutbuttonPanel;
    private JLabel menulabel;
    private AlimentazioneView alimentazione;
    private AllenamentoView allenamento;
    private ProfiloView profilo;
    private RiepilogoView riepilogo;

    public Menu() {
        JFrame frame = Auth.getFrame();
        frame.getContentPane().removeAll();
        frame.setContentPane(mainPanel);
        try {
            Image img = ImageIO.read(getClass().getResource("resources/menu1.png"));
            Image icon = ImageIO.read(getClass().getResource("resources/pulseicon.png"));
            Image newimgicon = icon.getScaledInstance( 28, 28,  java.awt.Image.SCALE_SMOOTH );
            Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH );
            menuButton.setIcon(new ImageIcon(newimg));
            menulabel.setIcon(new ImageIcon(newimgicon));
            menuButton.setBorder(null);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        variablePanel.add(alimentazionePanel, "AlimentazioneView");
        variablePanel.add(allenamentoPanel, "AllenamentoView");
        variablePanel.add(profiloPanel, "ProfiloView");
        variablePanel.add(riepilogoPanel, "RiepilogoView");

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

    public RiepilogoView getRiepilogoview() {
        return riepilogo;
    }

    /** Listener associati ad elementi di cui è composto il file XML Menu.form */

    public void addMenuButtonListener(ActionListener listener){
        menuButton.addActionListener(listener);
    }

    public void addAlimentazioneButtonListener(ActionListener listener){ alimentazioneButton.addActionListener(listener); }

    public void addNewProgAlimButtonListener(ActionListener listener){ newProgAlimButton.addActionListener(listener); }

    public void addNewCiboButtonListener(ActionListener listener){ newCiboButton.addActionListener(listener); }

    public void addAllenamentoButtonListener(ActionListener listener){
        allenamentoButton.addActionListener(listener);
    }

    public void addNewProgAllenButtonListener(ActionListener listener) { newProgAllenButton.addActionListener(listener);}

    public void addNewEsercizioButtonListener(ActionListener listener) { newEsercizioButton.addActionListener(listener);}

    public void addProfiloButtonListener(ActionListener listener){
        profiloButton.addActionListener(listener);
    }

    public void addRiepilogoButtonListener(ActionListener listener){
        riepilogoButton.addActionListener(listener);
    }

    public void addLogoutButtonListener(ActionListener listener){
        logoutButton.addActionListener(listener);
    }

    /** Metodo che crea componenti dell'User Interface */

    private void createUIComponents() {
        alimentazione = new AlimentazioneView();
        allenamento = new AllenamentoView();
        profilo = new ProfiloView();
        riepilogo = new RiepilogoView();
        alimentazionePanel = alimentazione.getMainPanel();
        allenamentoPanel = allenamento.getMainPanel();
        profiloPanel = profilo.getMainPanel();
        riepilogoPanel = riepilogo.getMainPanel();
    }
}
