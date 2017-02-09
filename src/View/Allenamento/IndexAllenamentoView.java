package View.Allenamento;

import View.Alimentazione.GiornoAlimView;

import javax.swing.*;
import java.time.DayOfWeek;
import java.util.HashMap;

import static java.time.DayOfWeek.*;

/**
 * Created by lorenzobraconi on 06/02/17.
 */
public class IndexAllenamentoView {
    private JPanel mainPanel;
    private JPanel rimanentePanel;
    private JLabel rimanenteLabel;
    private JPanel obiettivoPanel;
    private JLabel obiettivoLabel;
    private JPanel effettivoPanel;
    private JLabel effettivoLabel;
    private JPanel menoPanel;
    private JPanel ugualePanel;
    private JTabbedPane tabbedPane1;
    private JPanel lunediPanel;
    private JPanel martediPanel;
    private JPanel mercolediPanel;
    private JPanel giovediPanel;
    private JPanel venerdiPanel;
    private JPanel sabatoPanel;
    private JPanel domenicaPanel;
    private GiornoAllenView lunedi;
    private GiornoAllenView martedi;
    private GiornoAllenView mercoledi;
    private GiornoAllenView giovedi;
    private GiornoAllenView venerdi;
    private GiornoAllenView sabato;
    private GiornoAllenView domenica;
    private HashMap<DayOfWeek,GiornoAllenView> giorni = new HashMap<DayOfWeek,GiornoAllenView>();

    public IndexAllenamentoView() {
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
        lunedi = new GiornoAllenView();
        martedi = new GiornoAllenView();
        mercoledi = new GiornoAllenView();
        giovedi = new GiornoAllenView();
        venerdi = new GiornoAllenView();
        sabato = new GiornoAllenView();
        domenica = new GiornoAllenView();
        lunediPanel =  lunedi.getMainPanel();
        martediPanel = martedi.getMainPanel();
        mercolediPanel = mercoledi.getMainPanel();
        giovediPanel = giovedi.getMainPanel();
        venerdiPanel = venerdi.getMainPanel();
        sabatoPanel = sabato.getMainPanel();
        domenicaPanel = domenica.getMainPanel();
    }

    public GiornoAllenView getGiorni(DayOfWeek giornosettimana) {
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
