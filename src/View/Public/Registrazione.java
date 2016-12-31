package View.Public;

import Controller.PublicController;
import Object.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class Registrazione {

    private JPanel mainPanel;
    private JTextField username;
    private JTextField pass;
    private JTextField nome;
    private JTextField cognome;
    private JTextField eta;
    private JTextField email;
    private JTextField altezza;
    private JComboBox lavoro;
    private JComboBox livelloattivitafisica;
    private JComboBox allergia;
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
                //manca l'eliminazione degli spazi dai campi di testo
                utente.setUsername(username.getText());
                utente.setPassword(pass.getText());
                utente.setNome(nome.getText());
                utente.setCognome(cognome.getText());
                utente.setEta(Integer.parseInt(eta.getText()));
                utente.setEmail(email.getText());
                utente.setAltezza(Integer.parseInt(altezza.getText()));
                Enumeration elements = sessoGroup.getElements();
                while (elements.hasMoreElements()) {
                    JRadioButton button = (JRadioButton)elements.nextElement();
                    if (button.isSelected()){
                        if (button.getText()=="Uomo")
                        utente.setSesso(1);
                        else
                            utente.setSesso(0);
                    }
                }
                //mancano lavoro, livello attività fisica e allergia

                utente.setPeso(Integer.parseInt(peso.getText()));
                new PublicController(finestra).completaregistrazioneAction(utente);

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
