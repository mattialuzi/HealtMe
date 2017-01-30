package View.Alimentazione;

import javax.swing.*;

/**
 * Created by lorenzobraconi on 11/01/17.
 */
public class NewProgAlimView {
    private JPanel mainPanel;
    private JPanel indexprogalimPanel;
    private JPanel progalimcombPanel;
    //private JPanel progalimmanPanel;
    private IndexProgAlimView indexprogalimview;
    private ProgAlimCombView progalimcombview;
    //private ProgAlimManView progalimmanview;

    public NewProgAlimView() {
        mainPanel.add(indexprogalimPanel,"IndexProgAlimView");
        //mainPanel.add(progalimcombPanel, "ProgAlimCombView");
        //mainPanel.add(progalimmanPanel,"ProgAlimManView");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public IndexProgAlimView getIndexprogalimview() {
        return indexprogalimview;
    }

    public ProgAlimCombView getProgalimcombview() {
        return progalimcombview;
    }

    /*public ProgAlimManView getProgalimmanview() {
        return progalimmanview;
    }*/

    private void createUIComponents() {
        indexprogalimview = new IndexProgAlimView();
        //progalimcombview = new ProgAlimCombView();
        //progalimmanview = new ProgAlimManView();
        indexprogalimPanel = indexprogalimview.getMainPanel();
        //progalimcombPanel = progalimcombview.getMainPanel();
        //progalimmanPanel = progalimmanview.getMainPanel();
    }
}
