package View.Allenamento;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ALLDE on 07/02/2017.
 */
public class ProgAllenManView {
    private JPanel mainPanel;
    private JTabbedPane settimanaPane;
    private JPanel lunediPanel;
    private JPanel martediPanel;
    private JPanel mercolediPanel;
    private JPanel giovediPanel;
    private JPanel venerdiPanel;
    private JPanel sabatoPanel;
    private JPanel domenicaPanel;
    private JButton annullaProgrammaButton;
    private JButton confermaProgrammaButton;
    private GiornoAllenForm lunedimanuale;
    private GiornoAllenForm martedimanuale;
    private GiornoAllenForm mercoledimanuale;
    private GiornoAllenForm giovedimanuale;
    private GiornoAllenForm venerdimanuale;
    private GiornoAllenForm sabatomanuale;
    private GiornoAllenForm domenicamanuale;
    private ArrayList<GiornoAllenForm> giornimanuali;

    public ProgAllenManView() {
        giornimanuali = new ArrayList<GiornoAllenForm>(7);
        giornimanuali.add(lunedimanuale);
        giornimanuali.add(martedimanuale);
        giornimanuali.add(mercoledimanuale);
        giornimanuali.add(giovedimanuale);
        giornimanuali.add(venerdimanuale);
        giornimanuali.add(sabatomanuale);
        giornimanuali.add(domenicamanuale);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addTabbedSelectionListener (ChangeListener listener) {
        settimanaPane.addChangeListener(listener);
    }

    public void addAnnullaProgrammaButtonListener(ActionListener listener) {
        annullaProgrammaButton.addActionListener(listener);
    }

    public void addConfermaProgrammaButtonListener(ActionListener listener){
        confermaProgrammaButton.addActionListener(listener);
    }

    public GiornoAllenForm getTabView (int panel) {
        return giornimanuali.get(panel);
    }

    private void createUIComponents() {
        lunedimanuale = new GiornoAllenForm("Quale attività vuoi svolgere il lunedi");
        martedimanuale = new GiornoAllenForm("Quale attività vuoi svolgere il martedi");
        mercoledimanuale = new GiornoAllenForm("Quale attività vuoi svolgere il mercoledi");
        giovedimanuale = new GiornoAllenForm("Quale attività vuoi svolgere il giovedi");
        venerdimanuale = new GiornoAllenForm("Quale attività vuoi svolgere il venerdi");
        sabatomanuale = new GiornoAllenForm("Quale attività vuoi svolgere il sabato");
        domenicamanuale = new GiornoAllenForm("Quale attività vuoi svolgeree la domenica");
        lunedimanuale.setButtonsVisible();
        martedimanuale.setButtonsVisible();
        mercoledimanuale.setButtonsVisible();
        giovedimanuale.setButtonsVisible();
        venerdimanuale.setButtonsVisible();
        sabatomanuale.setButtonsVisible();
        domenicamanuale.setButtonsVisible();
        lunediPanel = lunedimanuale.getMainPanel();
        martediPanel = martedimanuale.getMainPanel();
        mercolediPanel = mercoledimanuale.getMainPanel();
        giovediPanel = giovedimanuale.getMainPanel();
        venerdiPanel = venerdimanuale.getMainPanel();
        sabatoPanel = sabatomanuale.getMainPanel();
        domenicaPanel = domenicamanuale.getMainPanel();
    }
}
