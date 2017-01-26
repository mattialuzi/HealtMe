package View.Alimentazione;

import javax.swing.*;

/**
 * Created by Mattia on 25/01/2017.
 */
public class ProgAlimManView {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
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

    public ProgAlimManView() {

    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

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


