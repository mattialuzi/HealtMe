package View.Riepilogo;

import javax.swing.*;

/**
 * La classe RiepilogoView contiene attributi e metodi associati al file XML RiepilogoView.form
 */

public class RiepilogoView {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JPanel storiaPanel;
    private JPanel progressiPanel;
    private ProgressiView progressi;
    private StoriaView storia;

    public RiepilogoView() {
    }

    public JPanel getMainPanel() {
        return mainPanel;

    }

    public ProgressiView getProgressi() {
        return progressi;
    }

    public StoriaView getStoria() {
        return storia;
    }

    /**
     *  Metodo che crea componenti dell'User Interface
     */
    private void createUIComponents() {
        storia = new StoriaView();
        storiaPanel = storia.getMainPanel();
    }

    public void addProgressiView (ProgressiView Progressi) {
        progressiPanel.removeAll();
        progressiPanel.add(Progressi.getMainPanel());
    }
}

