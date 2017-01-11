package View.Alimentazione;

import Object.Enum.AllergiaEnum;
import Object.Enum.CompatibilitaEnum;
import Object.Enum.GruppoEnum;
import Object.Enum.PortataEnum;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by lorenzobraconi on 11/01/17.
 */
public class NewCiboView {
    private JPanel mainPanel;
    private JButton azzeraCampiButton;
    private JButton aggiungiAlimentoButton;
    private JTextField nome;
    private JComboBox<Enumeration> gruppo;
    private JComboBox<Enumeration> allergia;
    private JTextField kcal;
    private JComboBox<Enumeration> portata;
    private JComboBox<Enumeration> compatibilita;

    public NewCiboView() {

        gruppo.setModel(new DefaultComboBoxModel(GruppoEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
        portata.setModel(new DefaultComboBoxModel(PortataEnum.values()));
        compatibilita.setModel(new DefaultComboBoxModel(CompatibilitaEnum.values()));
    }

    public void addAzzeraCampiButtonListener(ActionListener listener) { azzeraCampiButton.addActionListener(listener); }

    public void azzeraCampi(){
        nome.setText("");
        kcal.setText("");
        gruppo.setModel(new DefaultComboBoxModel(GruppoEnum.values()));
        portata.setModel(new DefaultComboBoxModel(PortataEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
        compatibilita.setModel(new DefaultComboBoxModel(CompatibilitaEnum.values()));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
