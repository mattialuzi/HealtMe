package View.Alimentazione;

import Helpers.View;
import Object.Enum.*;
import Object.CiboObject;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by lorenzobraconi on 11/01/17.
 */
public class NewCiboView extends View{
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
        ComboItem[] items = new ComboItem[] {new ComboItem("tutti", "Tutti i pasti"), new ComboItem("pranzo_cena","Pranzo e Cena"), new ComboItem("colazione_spuntino","Colazione e Spuntino"), new ComboItem("pranzo","Pranzo"), new ComboItem("cena","Cena"), new ComboItem("colazione","Colazione"), new ComboItem("spuntino","Spuntino")};
        idoneita.setModel(new DefaultComboBoxModel(items));
    }

    public void addAzzeraCampiButtonListener(ActionListener listener) { azzeraCampiButton.addActionListener(listener); }

    public void addAggiungiAlimentoButtonListener(ActionListener listener){ aggiungiAlimentoButton.addActionListener(listener);}

    public void azzeraCampi(){
        nome.setText("");
        kcal.setText("");
        gruppo.setSelectedIndex(0);
        portata.setSelectedIndex(0);
        allergia.setSelectedIndex(0);
        compatibilita.setSelectedIndex(0);
        idoneita.setSelectedIndex(0);
    }

    public boolean isValid(){
        boolean validator= true;
        if(!validate(nome.getText(),"cibo")){
            JOptionPane.showMessageDialog(null, "Nome non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(kcal.getText(),"intero")){
            JOptionPane.showMessageDialog(null, "Valore di kcal non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            kcal.requestFocus();
            validator=false;
        }
        return validator;
    }

    public CiboObject getNuovoCibo(){
        CiboObject nuovocibo = new CiboObject();
        nuovocibo.setNome(nome.getText());
        nuovocibo.setKilocal(Integer.parseInt(kcal.getText()));
        nuovocibo.setAllergia((AllergiaEnum) allergia.getSelectedItem());
        nuovocibo.setCompatibilita((CompatibilitaEnum) compatibilita.getSelectedItem());
        nuovocibo.setGruppo((GruppoEnum) gruppo.getSelectedItem());
        nuovocibo.setPortata((PortataEnum) portata.getSelectedItem());
        ComboItem combo = (ComboItem)idoneita.getSelectedItem();
        nuovocibo.setIdoneita(IdoneitaEnum.valueOf(combo.getValue()));
        return nuovocibo;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private class ComboItem {
        private String value;
        private String label;

        public ComboItem(String value, String label) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return label;
        }

        @Override
        public String toString(){
            return label;
        }
    }
}






















