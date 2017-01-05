package Controller;

import Model.UtenteModel;
import Object.*;
import View.Auth;
import View.Public.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PublicController {

    //protected JFrame finestra;
    private Auth view;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;


    public PublicController(Auth view) {

        this.view = view;
        view.setVisible(true);
        mainPanel = view.getMainPanel();
        cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel, "Index");
        Index indexview = view.getIndexview();
        indexview.addLoginButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"Login");
            }
        });
        indexview.addRegistratiButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"Registrazione");
            }
        });


    }

   /* public void indexAction(){
        Index view = new Index(this);
        render(view.getMainPanel());
    }

    public void loginAction() {
        Login view = new Login(this);
        render(view.getMainPanel());
    }

    public void completaloginAction(String username, String password) {
        UtenteModel utente = new UtenteModel();
        boolean validator = utente.getUserByCredential(username, password);
        if(!validator){
            JOptionPane.showMessageDialog(null, "Username o Password errati", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        else {
            Homepage view = new Homepage(finestra, username);
            render(view.getMainPanel());
        }
    }

    public void registrazioneAction() {
        Registrazione view = new Registrazione(this);
        render(view.getMainPanel());
    }

    public void completaregistrazioneAction(UtenteObject utente) {
        UtenteModel tabella= new UtenteModel();
        boolean validator = tabella.findUserByUsername(utente.getUsername());
        if(validator){
            JOptionPane.showMessageDialog(null, "Username già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
        }
        else {
            tabella.inserisciUtente(utente);
            JOptionPane.showMessageDialog(null, "Registrazione completata con successo", "Benvenuto in Health Me!", JOptionPane.INFORMATION_MESSAGE);
            loginAction();
        }
    }

    public float calcoloPesoForma(float altezza, int sesso){
        float peso_forma;
        //per indicare un numero float si mette una f alla fine
        if(sesso == 1) {
            peso_forma = 22.1f * altezza * altezza;
        }
        else {
            peso_forma = 20.6f * altezza * altezza;
        }
        return peso_forma;
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
    }*/


}
