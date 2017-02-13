package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import static java.time.DayOfWeek.*;

/**
 * La classe IndexAlimentazioneView contiene attributi e metodi associati al file XML IndexAlimentazioneView.form
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

    /**
     * Metodo che crea componenti dell'User Interface
     */

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

    /**
     *  Metodo che prende un elemento dell'HasHMap in base alla chiave
     * @param giornosettimana  Valore della chiave
     * @return La variabile GiornoAlimView selezionata
     */

    public GiornoAlimView getGiorni(DayOfWeek giornosettimana) {
            return giorni.get(giornosettimana);
    }

    /**
     * Metodo che imposta il tab al giorno corrente della settimana
     * @param today Nome del giorno
     */

    public void setTodayTab (DayOfWeek today) {
        tabbedPane1.setSelectedComponent(giorni.get(today).getMainPanel());
    }

    /**
     * Metodo che imposta la visibilità del panel Calorie
     * @param prog Indica se il programma è combinato
     */

    public void showHideCaloriePanel(boolean prog){
        obiettivoPanel.setVisible(prog);
        menoPanel.setVisible(prog);
        ugualePanel.setVisible(prog);
        rimanentePanel.setVisible(prog);
    }

    /**
     * Setta Il valorie delle calorie nei label
     * @param effettive Valore della calorie effettive
     */

    public void setCalorieLabel(int effettive){
        effettivoLabel.setText(String.valueOf(effettive));
        if(obiettivoPanel.isVisible()){
            int obiettivo = Integer.parseInt(obiettivoLabel.getText());
            int rimanenti = obiettivo-effettive;
            rimanenteLabel.setText(rimanenti+" Kcal");
        }
    }

    /**
     * Setta Il valorie delle calorie nei label
     * @param effettive Valore della calorie effettive
     * @param obiettivo Valore della calorie obiettivo
     */

    public void setCalorieLabel(int effettive,int obiettivo){
        effettivoLabel.setText(String.valueOf(effettive));
        obiettivoLabel.setText(String.valueOf(obiettivo));
        int rimanenti = obiettivo-effettive;
        rimanenteLabel.setText(rimanenti+" Kcal");
    }
}


