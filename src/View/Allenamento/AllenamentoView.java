package View.Allenamento;

import javax.swing.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AllenamentoView {
    private JPanel mainPanel;
    private JPanel indexAllenPanel;
    private JPanel newEsercizioPanel;
    private IndexAllenamentoView indexallenamento;
    private NewEsercizioView newesercizio;

    public AllenamentoView() {
        mainPanel.add(indexAllenPanel, "IndexAllenamentoView");
        mainPanel.add(newEsercizioPanel, "NewEsercizioView");
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void createUIComponents() {
        indexallenamento = new IndexAllenamentoView();
        indexAllenPanel = indexallenamento.getMainPanel();
        newesercizio = new NewEsercizioView();
        newEsercizioPanel = newesercizio.getMainPanel();
    }
}


