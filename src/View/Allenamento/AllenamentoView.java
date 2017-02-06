package View.Allenamento;

import javax.swing.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AllenamentoView {
    private JPanel mainPanel;
    private JPanel indexAllenPanel;
    private IndexAllenamentoView indexallenamento;

    public AllenamentoView() {
        mainPanel.add(indexAllenPanel, "IndexAllenamentoView");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        indexallenamento = new IndexAllenamentoView();
        indexAllenPanel = indexallenamento.getMainPanel();
    }
}


