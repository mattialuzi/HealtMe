package View.Alimentazione;

import Object.Enum.PastoEnum;

import javax.swing.*;
import java.awt.event.*;

public class FormCiboEffettivo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox pasto;
    private JComboBox portata;
    private JTextField textField1;

    public FormCiboEffettivo() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

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

        pasto.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String pastoscelto = pasto.getSelectedItem().toString();
                if (pastoscelto == "colazione" || pastoscelto=="spuntino"){
                    portata.setModel(new DefaultComboBoxModel(new String[]{"snack","bevanda","frutta"}));
                } else {
                    portata.setModel(new DefaultComboBoxModel(new String[]{"primo","secondo","contorno","dolce","frutta","bevanda"}));
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
