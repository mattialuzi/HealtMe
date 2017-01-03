package Controller;

import Model.UtenteModel;
import Object.*;
import View.Public.*;
import javax.swing.*;
import javax.xml.ws.LogicalMessage;
import java.awt.*;

public class PublicController {

    protected JFrame finestra;

    public PublicController(JFrame finestra) {
        this.finestra = finestra;
    }

    public void indexAction(){
        Index view = new Index(finestra);
        render(view.getMainPanel());
    }

    public void loginAction() {
        Login view = new Login(finestra);
        render(view.getMainPanel());
    }

    public void registrazioneAction() {
        Registrazione view = new Registrazione(finestra);
        render(view.getMainPanel());
    }

    public void completaregistrazioneAction(UtenteObject utente) {
        UtenteModel tabella= new UtenteModel();
        boolean validator = tabella.getUserByUsername(utente.getUsername());
        if(validator){
            JOptionPane.showMessageDialog(null, "Username già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        else {
            tabella.inserisciUtente(utente);
            JOptionPane.showMessageDialog(null, "Registrazione completata con successo", "Benvenuto in Health Me!", JOptionPane.INFORMATION_MESSAGE);
            loginAction();
        }
    }

    public void render(JPanel view){            //andrebbe sull'Helpers Controller; da creare...
        finestra.getContentPane().removeAll();
        try {
            finestra.add(view);
            finestra.validate();
            //finestra.setLocation(300,20);
            finestra.pack();
            finestra.setMinimumSize(new Dimension(500,300));
            finestra.setLocationRelativeTo(null);
            finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            finestra.setVisible(true);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

}
