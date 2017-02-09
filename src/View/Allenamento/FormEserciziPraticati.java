package View.Allenamento;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

/**
 * Created by ALLDE on 07/02/2017.
 */
public class FormEserciziPraticati extends JDialog {
    private JPanel contentPane;
    private JComboBox Intensita;
    private JTextField nomeEsercizio;
    private JList listaEsercizi;
    private JButton buttonCancel;
    private JButton buttonOK;
    private JScrollPane scrollPane;

    public FormEserciziPraticati() {
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

    public void addSetUnitaItemListener(ItemListener listener) { Intensita.addItemListener(listener);}

    public void addSearchKeyListener(KeyListener listener) { nomeEsercizio.addKeyListener(listener);}

    public void addSetEsercizioListSelectionListener(ListSelectionListener listener) { listaEsercizi.addListSelectionListener(listener);}

    public void addEsercizioEffettivoButtonListener(ActionListener listener) { buttonOK.addActionListener(listener); }

    public void onCancel() {
        buttonOK.setEnabled(false);
        nomeEsercizio.setText("");
        listaEsercizi.removeAll();
        nomeEsercizio.setEnabled(false);
        scrollPane.setVisible(false);
        dispose();
    }

    public JComboBox getIntensita() {
        return Intensita;
    }

    public JTextField getNomeEsercizio() {
        return nomeEsercizio;
    }

    public JList getListaEsercizi() {
        return listaEsercizi;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }
}
