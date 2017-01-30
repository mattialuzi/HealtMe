package Controller;

import Model.UtenteModel;
import Object.*;
import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import View.Auth;
import View.Public.*;
import View.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

public class PublicController {

    private Auth view;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;

    public PublicController(Auth view) {

        this.view = view;
        mainPanel = view.getMainPanel();
        cardLayout = (CardLayout)mainPanel.getLayout();
        cardLayout.show(mainPanel, "Index");
        Auth.setVisible(true);
        Index indexview = view.getIndexview();
        Login loginview = view.getLoginview();
        Registrazione registrazioneview = view.getRegistrazioneview();

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
        registrazioneview.addAzzeraButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazioneview.azzeraCampi();
            }
        });
        registrazioneview.addIndietroButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazioneview.azzeraCampi();
                cardLayout.show(mainPanel, "Index");
            }
        });
        registrazioneview.addCompletaRegistrazioneButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazioneview.eliminaSpazi();
                if (registrazioneview.isValid()){
                    UtenteObject nuovoutente = registrazioneview.getNuovoUtente();
                    UtenteModel tabella= new UtenteModel();
                    boolean validator = tabella.findUserByUsername(nuovoutente.getUsername());
                    if(validator){
                        JOptionPane.showMessageDialog(null, "Username già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        tabella.inserisciUtente(nuovoutente);
                        JOptionPane.showMessageDialog(null, "Registrazione completata con successo", "Benvenuto in Health Me!", JOptionPane.INFORMATION_MESSAGE);
                        registrazioneview.azzeraCampi();
                        cardLayout.show(mainPanel, "Login");
                    }
                }
            }
        });
        loginview.addAccediButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                completaloginAction();
            }
        });
        loginview.addIndietroButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginview.azzeraCampi();
                cardLayout.show(mainPanel,"Index");
            }
        });
    }

    public void completaloginAction() {
        String username = view.getLoginview().getUsername();
        String password = view.getLoginview().getPassword();
        UtenteModel utentemodel = new UtenteModel();
        boolean validator = utentemodel.findUserByCredential(username, password);
        if (!validator) {
            JOptionPane.showMessageDialog(null, "Username o Password errati", "Errore", JOptionPane.ERROR_MESSAGE);
        } else {
            UtenteObject utente = utentemodel.getUserByUsername(username);
            Menu welcome = new Menu();
            new MenuController(welcome, utente);
            Auth.getFrame().pack();
        }
    }


}
