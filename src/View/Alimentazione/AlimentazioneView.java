package View.Alimentazione;

import javax.swing.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneView {
    private JPanel mainPanel;
    private JPanel indexAlimPanel;
    private JPanel newCiboPanel;
    private IndexAlimentazioneView indexalimentazione;
    private NewCiboView newcibo;

    public AlimentazioneView() {
        mainPanel.add(indexAlimPanel, "IndexAlimentazioneView");
        mainPanel.add(newCiboPanel, "NewCiboView");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public IndexAlimentazioneView getIndexalimentazione() {
        return indexalimentazione;
    }

    public NewCiboView getNewcibo() {
        return newcibo;
    }

    private void createUIComponents() {
        indexalimentazione = new IndexAlimentazioneView();
        newcibo = new NewCiboView();
        indexAlimPanel = indexalimentazione.getMainPanel();
        newCiboPanel = newcibo.getMainPanel();
    }
}
