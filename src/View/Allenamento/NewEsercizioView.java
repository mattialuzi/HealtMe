package View.Allenamento;

import Helpers.View;
import Object.Enum.CategoriaEnum;
import Object.Enum.IntensitaEnum;
import Object.Enum.UnitaMisuraEnum;
import Object.EsercizioObject;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class NewEsercizioView extends View {

    private JPanel mainPanel;
    private JButton azzeraCampiButton;
    private JButton aggiungiEsercizioButton;
    private JTextField nome;
    private JTextField consumocalorico;
    private JComboBox<Enumeration> categoria;
    private JComboBox<Enumeration> intensita;
    private JComboBox<Enumeration> unitadimisura;

    public NewEsercizioView() {
        categoria.setModel(new DefaultComboBoxModel(CategoriaEnum.values()));
        intensita.setModel(new DefaultComboBoxModel(IntensitaEnum.values()));
        unitadimisura.setModel(new DefaultComboBoxModel(UnitaMisuraEnum.values()));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addAzzeraCampiButtonListener(ActionListener listener) {
        azzeraCampiButton.addActionListener(listener);
    }

    public void addAggiungiEsercizioButtonListener(ActionListener listener) {
        aggiungiEsercizioButton.addActionListener(listener);
    }

    public void azzeraCampi() {
        nome.setText("");
        consumocalorico.setText("");
        categoria.setSelectedIndex(0);
        intensita.setSelectedIndex(0);
        unitadimisura.setSelectedIndex(0);
    }

    public boolean isValid() {
        boolean validator = true;
        if (!validate(nome.getText(), "eserciziocibo")) {
            JOptionPane.showMessageDialog(null, "Nome non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator = false;
        }
        if (!validate(consumocalorico.getText(), "intero")) {
            JOptionPane.showMessageDialog(null, "Valore di consumo calorico non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            consumocalorico.requestFocus();
            validator = false;
        }
        return validator;
    }

    public EsercizioObject getNuovoEsercizio() {
        EsercizioObject nuovoesercizio = new EsercizioObject();
        nuovoesercizio.setTipologia(nome.getText());
        nuovoesercizio.setConsumo_calorico(Integer.parseInt(consumocalorico.getText()));
        nuovoesercizio.setCategoria((CategoriaEnum) categoria.getSelectedItem());
        nuovoesercizio.setIntensita((IntensitaEnum) intensita.getSelectedItem());
        nuovoesercizio.setUnita_misura((UnitaMisuraEnum) unitadimisura.getSelectedItem());
        return nuovoesercizio;
    }
}
