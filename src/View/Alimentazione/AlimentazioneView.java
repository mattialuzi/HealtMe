package View.Alimentazione;

import javax.swing.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneView {
    private JPanel mainPanel;
    private JPanel indexAlimPanel;
    private JPanel newProgAlimPanel;
    private JPanel newCiboPanel;
    private IndexAlimentazioneView indexalimentazione;
    private NewProgAlimView newprogalim;
    private NewCiboView newcibo;

    public AlimentazioneView() {

        indexalimentazione = new IndexAlimentazioneView();
        newprogalim = new NewProgAlimView();
        newcibo = new NewCiboView();
        indexAlimPanel = indexalimentazione.getMainPanel();
        newProgAlimPanel = newprogalim.getMainPanel();
        newCiboPanel = newcibo.getMainPanel();
        mainPanel.add(indexAlimPanel, "IndexAlimentazioneView");
        mainPanel.add(newProgAlimPanel, "NewProgAlimView");
        mainPanel.add(newCiboPanel, "NewCiboView");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public IndexAlimentazioneView getIndexalimentazione() {
        return indexalimentazione;
    }

    public NewProgAlimView getNewprogalim() {
        return newprogalim;
    }

    public NewCiboView getNewcibo() {
        return newcibo;
    }
}
