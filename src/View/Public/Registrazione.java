package View.Public;

import Controller.PublicController;
import Helpers.View;
import Object.*;
import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;

public class Registrazione extends View{

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

    public Registrazione(JFrame finestra) {

        // Setto i valori dei ComboBox prendendoli dalle enumerazioni
        lavoro.setModel(new DefaultComboBoxModel(LavoroEnum.values()));
        livelloattivitafisica.setModel(new DefaultComboBoxModel(LivelloAttivitaFisicaEnum.values()));
        allergia.setModel(new DefaultComboBoxModel(AllergiaEnum.values()));


        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PublicController(finestra).indexAction();
            }
        });
        completaRegistrazioneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UtenteObject utente = new UtenteObject();
                username.setText(deleteLastSpace(username.getText()));
                pass.setText(deleteLastSpace(pass.getText()));
                nome.setText(deleteLastSpace(nome.getText()));
                cognome.setText(deleteLastSpace(cognome.getText()));


                boolean validator = true;
                // manca il validatore per la presenza dello stesso username nel db
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
                    JOptionPane.showMessageDialog(null, "Eta non valida: l'età deve essere compresa tra 0 e 99", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    nome.requestFocus();
                    validator=false;
                }
                if(!validate(altezza.getText(),"intero")){
                    JOptionPane.showMessageDialog(null, "Altezza non valida: l'altezza va espressa in centimetri", "ERRORE", JOptionPane.ERROR_MESSAGE);
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

                if(validator) {
                    utente.setUsername(username.getText());
                    utente.setPassword(pass.getText());
                    utente.setNome(nome.getText());
                    utente.setCognome(cognome.getText());
                    utente.setEta(Integer.parseInt(eta.getText()));
                    utente.setEmail(email.getText());
                    utente.setAltezza(Integer.parseInt(altezza.getText()));
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
                    // Setto lavoro, livello attivita fisica e allergia dell'utente
                    LavoroEnum lav = (LavoroEnum) lavoro.getSelectedItem();
                    utente.setLavoro(lav);
                    LivelloAttivitaFisicaEnum livello = (LivelloAttivitaFisicaEnum) livelloattivitafisica.getSelectedItem();
                    utente.setLivello_attivita_fisica(livello);
                    AllergiaEnum all = (AllergiaEnum) allergia.getSelectedItem();
                    utente.setAllergia(all);
                    new PublicController(finestra).completaregistrazioneAction(utente);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
