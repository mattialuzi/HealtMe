package View.Alimentazione;

import javax.swing.*;
import java.awt.event.ActionListener;

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
    private JButton aggiungiCiboEffettivo;

    public IndexAlimentazioneView() {
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        lunediPanel =  new GiornoAlimView().getMainPanel();
        martediPanel = new GiornoAlimView().getMainPanel();
        mercolediPanel = new GiornoAlimView().getMainPanel();
        giovediPanel = new GiornoAlimView().getMainPanel();
        venerdiPanel = new GiornoAlimView().getMainPanel();
        sabatoPanel = new GiornoAlimView().getMainPanel();
        domenicaPanel = new GiornoAlimView().getMainPanel();
    }

    public void addAggiungiCiboEffettivoButtonListener(ActionListener listener) { aggiungiCiboEffettivo.addActionListener(listener); }
}


