package View.Alimentazione;

import Helpers.ComboItem;
import Helpers.View;
import Object.Enum.*;
import Object.CiboObject;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by lorenzobraconi on 11/01/17.
 */
public class NewCiboView extends View {
    private JPanel mainPanel;
    private JButton azzeraCampiButton;
    private JButton aggiungiAlimentoButton;
    private JTextField nome;
    private JComboBox<Enumeration> gruppo;
    private JComboBox<Enumeration> allergia;
    private JTextField kcal;
    private JComboBox<Enumeration> portata;
    private JComboBox<Enumeration> compatibilita;
    private JComboBox idoneita;

    public NewCiboView() {
        gruppo.setModel(new DefaultComboBoxModel(GruppoEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
        portata.setModel(new DefaultComboBoxModel(PortataEnum.values()));
        compatibilita.setModel(new DefaultComboBoxModel(CompatibilitaEnum.values()));
        ComboItem[] items = new ComboItem[]{new ComboItem("pranzo_cena", "Pranzo e Cena"), new ComboItem("pranzo", "Pranzo"), new ComboItem("cena", "Cena")};
        idoneita.setModel(new DefaultComboBoxModel(items));
    }

    public void addAzzeraCampiButtonListener(ActionListener listener) {
        azzeraCampiButton.addActionListener(listener);
    }

    public void addAggiungiAlimentoButtonListener(ActionListener listener) {
        aggiungiAlimentoButton.addActionListener(listener);
    }

    public void addSetIdoneitaItemListener (ItemListener listener) {
        portata.addItemListener(listener);
    }

    public void azzeraCampi() {
        nome.setText("");
        kcal.setText("");
        gruppo.setSelectedIndex(0);
        portata.setSelectedIndex(0);
        allergia.setSelectedIndex(0);
        compatibilita.setSelectedIndex(0);
    }

    public boolean isValid() {
        boolean validator = true;
        if (!validate(nome.getText(), "eserciziocibo")) {
            JOptionPane.showMessageDialog(null, "Nome non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator = false;
        }
        if (!validate(kcal.getText(), "intero")) {
            JOptionPane.showMessageDialog(null, "Valore di kcal non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            kcal.requestFocus();
            validator = false;
        }
        return validator;
    }

    public CiboObject getNuovoCibo() {
        CiboObject nuovocibo = new CiboObject();
        nuovocibo.setNome(nome.getText());
        nuovocibo.setKilocal(Integer.parseInt(kcal.getText()));
        nuovocibo.setAllergia((AllergiaEnum) allergia.getSelectedItem());
        nuovocibo.setCompatibilita((CompatibilitaEnum) compatibilita.getSelectedItem());
        nuovocibo.setGruppo((GruppoEnum) gruppo.getSelectedItem());
        nuovocibo.setPortata((PortataEnum) portata.getSelectedItem());
        ComboItem combo = (ComboItem) idoneita.getSelectedItem();
        nuovocibo.setIdoneita(IdoneitaEnum.valueOf(combo.getValue()));
        return nuovocibo;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setIdoneita(PortataEnum tipoportata) {
        ComboItem[] items;
        if (tipoportata.equals(PortataEnum.secondo) || tipoportata.equals(PortataEnum.dolce) || tipoportata.equals(PortataEnum.contorno)) {
            items = new ComboItem[]{new ComboItem("pranzo_cena", "Pranzo e Cena")};
        }
        else {
            if (tipoportata.equals(PortataEnum.primo)) {
                items = new ComboItem[]{new ComboItem("pranzo_cena", "Pranzo e Cena"), new ComboItem("pranzo", "Pranzo"), new ComboItem("cena", "Cena")};
            }
            else {
                if (tipoportata.equals(PortataEnum.snack)) {
                    items = new ComboItem[]{new ComboItem("colazione_spuntino", "Colazione e Spuntino"), new ComboItem("colazione", "Colazione"), new ComboItem("spuntino", "Spuntino")};
                }
                else {
                    if (tipoportata.equals(PortataEnum.bevanda)) {
                        items = new ComboItem[] {new ComboItem("colazione_spuntino", "Colazione e Spuntino"), new ComboItem("colazione", "Colazione"), new ComboItem("spuntino", "Spuntino")};
                    }
                    else {
                        items = new ComboItem[] {new ComboItem("tutti", "Tutti i Pasti") }; //tipoportata = PortataEnum.frutta
                    }
                }
            }
        }
        idoneita.setModel(new DefaultComboBoxModel(items));
    }







}






















