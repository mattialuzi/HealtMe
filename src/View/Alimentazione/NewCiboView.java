package View.Alimentazione;

import Helpers.Item;
import Helpers.View;
import Object.Enum.*;
import Object.CiboObject;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Enumeration;

/**
 * La classe NewCiboView contiene attributi e metodi associati al file XML NewCiboView.form
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
        Item[] items = new Item[]{new Item("pranzo_cena", "Pranzo e Cena"), new Item("pranzo", "Pranzo"), new Item("cena", "Cena")};
        idoneita.setModel(new DefaultComboBoxModel(items));
    }

    /**
     * Metodo che azzera i campi della form di inserimento di un nuovo cibo
     */

    public void azzeraCampi() {
        nome.setText("");
        kcal.setText("");
        gruppo.setSelectedIndex(0);
        portata.setSelectedIndex(0);
        allergia.setSelectedIndex(0);
        compatibilita.setSelectedIndex(0);
    }

    /**
     * Metodo che verifica se i valori inseriti rispettano il corretto pattern
     * @return
     */

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

    /**
     * Metodo che crea un nuovo CiboObject settando i valori
     * @return Variabile di tipo CiboObject
     */

    public CiboObject getNuovoCibo() {
        CiboObject nuovocibo = new CiboObject();
        nuovocibo.setNome(nome.getText());
        nuovocibo.setKilocal(Integer.parseInt(kcal.getText()));
        nuovocibo.setAllergia((AllergiaEnum) allergia.getSelectedItem());
        nuovocibo.setCompatibilita((CompatibilitaEnum) compatibilita.getSelectedItem());
        nuovocibo.setGruppo((GruppoEnum) gruppo.getSelectedItem());
        nuovocibo.setPortata((PortataEnum) portata.getSelectedItem());
        Item combo = (Item) idoneita.getSelectedItem();
        nuovocibo.setIdoneita(IdoneitaEnum.valueOf(combo.getValue()));
        return nuovocibo;
    }

    /**
     * Metodo che setta l'idoneità di un cibo in base al tipo di portata
     * @param tipoportata Tipo di portata
     */

    public void setIdoneita(PortataEnum tipoportata) {
        Item[] items;
        if (tipoportata.equals(PortataEnum.secondo) || tipoportata.equals(PortataEnum.dolce) || tipoportata.equals(PortataEnum.contorno)) {
            items = new Item[]{new Item("pranzo_cena", "Pranzo e Cena")};
        }
        else {
            if (tipoportata.equals(PortataEnum.primo)) {
                items = new Item[]{new Item("pranzo_cena", "Pranzo e Cena"), new Item("pranzo", "Pranzo"), new Item("cena", "Cena")};
            }
            else {
                if (tipoportata.equals(PortataEnum.snack)) {
                    items = new Item[]{new Item("colazione_spuntino", "Colazione e Spuntino"), new Item("colazione", "Colazione"), new Item("spuntino", "Spuntino")};
                }
                else {
                    if (tipoportata.equals(PortataEnum.bevanda)) {
                        items = new Item[] {new Item("colazione_spuntino", "Colazione e Spuntino"), new Item("colazione", "Colazione"), new Item("spuntino", "Spuntino")};
                    }
                    else {
                        items = new Item[] {new Item("tutti", "Tutti i Pasti") }; //tipoportata = PortataEnum.frutta
                    }
                }
            }
        }
        idoneita.setModel(new DefaultComboBoxModel(items));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    /** Listener associati ad elementi di cui è composto il file XML NewCiboView.form */

    public void addAzzeraCampiButtonListener(ActionListener listener) {
        azzeraCampiButton.addActionListener(listener);
    }

    public void addAggiungiAlimentoButtonListener(ActionListener listener) {
        aggiungiAlimentoButton.addActionListener(listener);
    }

    public void addSetIdoneitaItemListener (ItemListener listener) {
        portata.addItemListener(listener);
    }
}






















