package View.Alimentazione;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *  La classe ProgAlimManView contiene attributi e metodi associati al file XML ProgAlimManView.form
 */

public class ProgAlimManView {
    private JPanel mainPanel;
    private JTabbedPane settimanaPane;
    private JButton annullaProgrammaButton;
    private JButton confermaProgrammaButton;
    private JPanel lunediPanel;
    private JPanel martediPanel;
    private JPanel mercolediPanel;
    private JPanel giovediPanel;
    private JPanel venerdiPanel;
    private JPanel sabatoPanel;
    private JPanel domenicaPanel;
    private GiornoAlimForm lunedimanuale;
    private GiornoAlimForm martedimanuale;
    private GiornoAlimForm mercoledimanuale;
    private GiornoAlimForm giovedimanuale;
    private GiornoAlimForm venerdimanuale;
    private GiornoAlimForm sabatomanuale;
    private GiornoAlimForm domenicamanuale;
    private ArrayList<GiornoAlimForm> giornimanuali;

    public ProgAlimManView() {
        giornimanuali = new ArrayList<GiornoAlimForm>(7);
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

    /**  Listener associati ad elementi di cui è composto il file XML ProgAlimManView.form */

    public void addTabbedSelectionListener (ChangeListener listener) {
        settimanaPane.addChangeListener(listener);
    }

    public void addAnnullaProgrammaButtonListener(ActionListener listener) {
        annullaProgrammaButton.addActionListener(listener);
    }

    public void addConfermaProgrammaButtonListener(ActionListener listener){
        confermaProgrammaButton.addActionListener(listener);
    }

    /**
     *  Metodo che prende un elemento dell'HasHMap in base alla chiave
     * @param panel Valore della chiave
     * @return La variabile GiornoAlimForm selezionata
     */

    public GiornoAlimForm getTabView (int panel) {
        return giornimanuali.get(panel);
    }


    /**
     * Metodo che crea componenti dell'User Interface
     */

    private void createUIComponents() {
        lunedimanuale = new GiornoAlimForm("Cosa vuoi mangiare il lunedi");
        martedimanuale = new GiornoAlimForm("Cosa vuoi mangiare il martedi");
        mercoledimanuale = new GiornoAlimForm("Cosa vuoi mangiare il mercoledi");
        giovedimanuale = new GiornoAlimForm("Cosa vuoi mangiare il giovedi");
        venerdimanuale = new GiornoAlimForm("Cosa vuoi mangiare il venerdi");
        sabatomanuale = new GiornoAlimForm("Cosa vuoi mangiare il sabato");
        domenicamanuale = new GiornoAlimForm("Cosa vuoi mangiare la domenica");
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


