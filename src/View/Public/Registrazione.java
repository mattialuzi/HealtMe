package View.Public;

import Presenter.PublicPresenter;
import Helpers.View;
import Object.*;
import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class Registrazione extends View {

    private PublicPresenter controller;
    private JPanel mainPanel;
    private JTextField username;
    private JTextField pass;
    private JTextField nome;
    private JTextField cognome;
    private JTextField eta;
    private JTextField email;
    private JTextField altezza;
    private JComboBox<Enumeration> lavoro;
    private JComboBox<Enumeration> livelloattivitafisica;
    private JComboBox<Enumeration> allergia;
    private JButton indietroButton;
    private JButton azzeraButton;
    private JButton completaRegistrazioneButton;
    private JTextField peso;
    private JRadioButton uomoRadioButton;
    private JRadioButton donnaRadioButton;
    private ButtonGroup sessoGroup;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Registrazione() {

        lavoro.setModel(new DefaultComboBoxModel(LavoroEnum.values()));
        livelloattivitafisica.setModel(new DefaultComboBoxModel(LivelloAttivitaFisicaEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
    }

    public void addIndietroButtonListener(ActionListener listener) {
        indietroButton.addActionListener(listener);
    }

    public void addAzzeraButtonListener(ActionListener listener) {
        azzeraButton.addActionListener(listener);
    }

    public void addCompletaRegistrazioneButtonListener(ActionListener listener){
        completaRegistrazioneButton.addActionListener(listener);
    }

    public void azzeraCampi(){
        username.setText("");
        pass.setText("");
        nome.setText("");
        cognome.setText("");
        eta.setText("");
        email.setText("");
        altezza.setText("");
        peso.setText("");
        lavoro.setModel(new DefaultComboBoxModel(LavoroEnum.values()));
        livelloattivitafisica.setModel(new DefaultComboBoxModel(LivelloAttivitaFisicaEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));
        uomoRadioButton.setSelected(true);
    }

    public void eliminaSpazi(){

        username.setText(deleteLastSpace(username.getText()));
        pass.setText(deleteLastSpace(pass.getText()));
        nome.setText(deleteLastSpace(nome.getText()));
        cognome.setText(deleteLastSpace(cognome.getText()));
        email.setText(deleteLastSpace(email.getText()));
    }

    public boolean isValid(){

        boolean validator= true;
        if(!validate(username.getText(),"testo")){
            JOptionPane.showMessageDialog(null, "Username non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(pass.getText(),"password")){
            JOptionPane.showMessageDialog(null, "Passord non valida: la password deve contenere almeno 6 caratteri e può contenere alcuni caratteri speciali($ , _ , !)", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(nome.getText(),"testo")){
            JOptionPane.showMessageDialog(null, "Nome non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(cognome.getText(),"testo")){
            JOptionPane.showMessageDialog(null, "Cognome non valido", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(eta.getText(),"eta")){
            JOptionPane.showMessageDialog(null, "Eta non valida: l'età deve essere compresa tra 10 e 99", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(altezza.getText(),"virgola")){
            JOptionPane.showMessageDialog(null, "Altezza non valida: l'altezza va espressa in metri (es: 1.70) ", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(peso.getText(),"virgola")){
            JOptionPane.showMessageDialog(null, "Peso non valido: inserire un numero con la virgola (es: 70.0)", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        if(!validate(email.getText(),"email")){
            JOptionPane.showMessageDialog(null, "Email non valida", "ERRORE", JOptionPane.ERROR_MESSAGE);
            nome.requestFocus();
            validator=false;
        }
        return validator;
    }

    public UtenteObject getNuovoUtente(){
        UtenteObject utente = new UtenteObject();
        utente.setUsername(username.getText());
        utente.setPassword(pass.getText());
        utente.setNome(nome.getText());
        utente.setCognome(cognome.getText());
        utente.setEta(Integer.parseInt(eta.getText()));
        utente.setEmail(email.getText());
        float altezzauser = Float.parseFloat(altezza.getText());
        utente.setAltezza(altezzauser);
        utente.setPeso(Float.parseFloat(peso.getText()));
        Enumeration elements = sessoGroup.getElements();
        while (elements.hasMoreElements()) {
            JRadioButton button = (JRadioButton) elements.nextElement();
            if (button.isSelected()) {
                if (button.getText() == "Uomo")
                    utente.setSesso(1);
                else
                    utente.setSesso(0);
            }
        }
        LavoroEnum lav = (LavoroEnum) lavoro.getSelectedItem();
        utente.setLavoro(lav);
        LivelloAttivitaFisicaEnum livello = (LivelloAttivitaFisicaEnum) livelloattivitafisica.getSelectedItem();
        utente.setLivello_attivita_fisica(livello);
        AllergiaEnum all = (AllergiaEnum) allergia.getSelectedItem();
        utente.setAllergia(all);
        utente.setPeso_forma(calcoloPesoForma(altezzauser,utente.getSesso()));
        return utente;
    }

}



