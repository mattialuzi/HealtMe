package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static java.time.DayOfWeek.*;

/**
 * Created by lorenzobraconi on 11/01/17.
 */
public class IndexAlimentazioneView {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel lunediPanel;
    private JPanel martediPanel;
    private JPanel mercolediPanel;
    private JPanel giovediPanel;
    private JPanel venerdiPanel;
    private JPanel sabatoPanel;
    private JPanel domenicaPanel;
    private JLabel obiettivoLabel;
    private JLabel effettivoLabel;
    private JLabel rimanenteLabel;
    private JPanel rimanentePanel;
    private JPanel obiettivoPanel;
    private JPanel effettivoPanel;
    private JPanel menoPanel;
    private JPanel ugualePanel;
    private GiornoAlimView lunedi;
    private GiornoAlimView martedi;
    private GiornoAlimView mercoledi;
    private GiornoAlimView giovedi;
    private GiornoAlimView venerdi;
    private GiornoAlimView sabato;
    private GiornoAlimView domenica;
    private HashMap<DayOfWeek,GiornoAlimView> giorni = new HashMap<DayOfWeek,GiornoAlimView>();

    public IndexAlimentazioneView() {
        giorni.put(MONDAY,lunedi);
        giorni.put(TUESDAY,martedi);
        giorni.put(WEDNESDAY, mercoledi);
        giorni.put(THURSDAY,giovedi);
        giorni.put(FRIDAY,venerdi);
        giorni.put(SATURDAY,sabato);
        giorni.put(SUNDAY,domenica);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        lunedi = new GiornoAlimView();
        martedi = new GiornoAlimView();
        mercoledi = new GiornoAlimView();
        giovedi = new GiornoAlimView();
        venerdi = new GiornoAlimView();
        sabato = new GiornoAlimView();
        domenica = new GiornoAlimView();
        lunediPanel =  lunedi.getMainPanel();
        martediPanel = martedi.getMainPanel();
        mercolediPanel = mercoledi.getMainPanel();
        giovediPanel = giovedi.getMainPanel();
        venerdiPanel = venerdi.getMainPanel();
        sabatoPanel = sabato.getMainPanel();
        domenicaPanel = domenica.getMainPanel();
    }

    public GiornoAlimView getGiorni(DayOfWeek giornosettimana) {
            return giorni.get(giornosettimana);
    }

    public void setTodayTab (DayOfWeek today) {
        tabbedPane1.setSelectedComponent(giorni.get(today).getMainPanel());
    }

    public void showHideCaloriePanel(boolean prog){
        obiettivoPanel.setVisible(prog);
        menoPanel.setVisible(prog);
        ugualePanel.setVisible(prog);
        rimanentePanel.setVisible(prog);
    }

    public void setCalorieLabel(int effettive){
        effettivoLabel.setText(String.valueOf(effettive));
        if(obiettivoPanel.isVisible()){
            int obiettivo = Integer.parseInt(obiettivoLabel.getText());
            int rimanenti = obiettivo-effettive;
            rimanenteLabel.setText(rimanenti+" Kcal");
        }
    }

    public void setCalorieLabel(int effettive,int obiettivo){
        effettivoLabel.setText(String.valueOf(effettive));
        obiettivoLabel.setText(String.valueOf(obiettivo));
        int rimanenti = obiettivo-effettive;
        rimanenteLabel.setText(rimanenti+" Kcal");
    }
}


