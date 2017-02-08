package View.Riepilogo;

import javax.swing.*;

/**
 * Created by lorenzobraconi on 05/01/17.
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

    private void createUIComponents() {
        //progressi = new ProgressiView();
        storia = new StoriaView();
        //progressiPanel = progressi.getMainPanel();
        storiaPanel = storia.getMainPanel();
    }

    public void addProgressiView (ProgressiView Progressi) {
        progressiPanel.removeAll();
        progressiPanel.add(Progressi.getMainPanel());
    }
}

