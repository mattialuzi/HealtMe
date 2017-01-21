package View.Alimentazione;

import Object.Enum.PastoEnum;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class FormCiboEffettivo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox portata;
    private JTextField quantita;
    private JTextField nomeAlimento;
    private JList listaAlimenti;
    private JScrollPane scrollPane;
    private JLabel misuraLabel;
    private JLabel quantitaLabel;

    public FormCiboEffettivo() {

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

    public void addSetPortataItemListener(ItemListener listener) { portata.addItemListener(listener);}

    public void addSearchKeyListener(KeyListener listener) { nomeAlimento.addKeyListener(listener);}

    public void addSetCiboListSelectionListener(ListSelectionListener listener) { listaAlimenti.addListSelectionListener(listener);}

    public void addQuantitaKeyListener(KeyListener listener) { quantita.addKeyListener(listener);}

    public void addPortataEffettivaButtonListener(ActionListener listener) { buttonOK.addActionListener(listener); }

    public void onCancel() {
        buttonOK.setEnabled(false);
        quantita.setText("");
        listaAlimenti.removeAll();
        dispose();
    }

    public JComboBox getPortata() {
        return portata;
    }

    public JTextField getNomeAlimento() {
        return nomeAlimento;
    }

    public JList getListaAlimenti() {
        return listaAlimenti;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JLabel getMisuraLabel() {
        return misuraLabel;
    }

    public JTextField getQuantita() {
        return quantita;
    }

    public JButton getButtonOK() {
        return buttonOK;
    }
}
