package View.Allenamento;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Created by lorenzobraconi on 07/02/17.
 */
public class FormEsercizioEffettivo extends JDialog{
    private JPanel contentPane;
    private JComboBox intensita;
    private JTextField nomeEsercizio;
    private JScrollPane scrollPane;
    private JList listaEsercizi;
    private JLabel quantitaLabel;
    private JTextField quantita;
    private JLabel misuraLabel;
    private JButton buttonOK;
    private JButton buttonCancel;

    public FormEsercizioEffettivo(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void addSetUnitaItemListener(ItemListener listener) { intensita.addItemListener(listener);}

    public void addSearchKeyListener(KeyListener listener) { nomeEsercizio.addKeyListener(listener);}

    public void addSetEsercizioListSelectionListener(ListSelectionListener listener) { listaEsercizi.addListSelectionListener(listener);}

    public void addQuantitaKeyListener(KeyListener listener) { quantita.addKeyListener(listener);}

    public void addAttivitaEffettivaButtonListener(ActionListener listener) { buttonOK.addActionListener(listener); }

    public void removeAttivitaEffettivaButtonListener () {
        ActionListener[] oldlistener = buttonOK.getActionListeners();
        buttonOK.removeActionListener(oldlistener[0]);
    }

    public void onCancel() {
        buttonOK.setEnabled(false);
        quantita.setText("");
        listaEsercizi.removeAll();
        nomeEsercizio.setEnabled(false);
        nomeEsercizio.setText("");
        scrollPane.setVisible(false);
        dispose();
    }

    public JComboBox getIntensita() {
        return intensita;
    }

    public JTextField getNomeEsercizio() {
        return nomeEsercizio;
    }

    public JList getListaEsercizi() {
        return listaEsercizi;
    }

    public JTextField getQuantita() {
        return quantita;
    }

    public JLabel getMisuraLabel() {
        return misuraLabel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }
}
