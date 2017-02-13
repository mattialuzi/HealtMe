package View.Alimentazione;

import javax.swing.*;

/**
 *  La classe NewProgAlimView contiene attributi e metodi associati al file XML NewProgAlimView.form
 */


public class NewProgAlimView {
    private JPanel mainPanel;
    private JPanel indexprogalimPanel;
    private JPanel progalimcombPanel;
    private IndexProgAlimView indexprogalimview;
    private ProgAlimCombView progalimcombview;

    public NewProgAlimView() {
        mainPanel.add(indexprogalimPanel,"IndexProgAlimView");
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

    /**
     *  Metodo che crea componenti dell'User Interface
     */

    private void createUIComponents() {
        indexprogalimview = new IndexProgAlimView();
        indexprogalimPanel = indexprogalimview.getMainPanel();
    }
}
