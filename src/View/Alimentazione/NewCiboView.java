package View.Alimentazione;

import Helpers.View;
import Object.Enum.AllergiaEnum;
import Object.Enum.CompatibilitaEnum;
import Object.Enum.GruppoEnum;
import Object.Enum.PortataEnum;
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

    public NewCiboView() {

        gruppo.setModel(new DefaultComboBoxModel(GruppoEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
        portata.setModel(new DefaultComboBoxModel(PortataEnum.values()));
        compatibilita.setModel(new DefaultComboBoxModel(CompatibilitaEnum.values()));
    }

    public void addAzzeraCampiButtonListener(ActionListener listener) { azzeraCampiButton.addActionListener(listener); }

    public void addAggiungiAlimentoButtonListener(ActionListener listener){ aggiungiAlimentoButton.addActionListener(listener);}

    public void azzeraCampi(){
        nome.setText("");
        kcal.setText("");
        gruppo.setModel(new DefaultComboBoxModel(GruppoEnum.values()));
        portata.setModel(new DefaultComboBoxModel(PortataEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
        compatibilita.setModel(new DefaultComboBoxModel(CompatibilitaEnum.values()));
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
        return nuovocibo;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
