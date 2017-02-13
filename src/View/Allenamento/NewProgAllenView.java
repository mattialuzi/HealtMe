package View.Allenamento;

import javax.swing.*;

public class NewProgAllenView {

    private JPanel mainPanel;
    private JPanel indexprogallenPanel;
    private IndexProgAllenView indexprogallenview;

    public NewProgAllenView() {
        mainPanel.add(indexprogallenPanel,"IndexProgAllenView");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public IndexProgAllenView getIndexprogallenview() {
        return indexprogallenview;
    }

    private void createUIComponents() {
        indexprogallenview = new IndexProgAllenView();
        indexprogallenPanel = indexprogallenview.getMainPanel();
    }
}
