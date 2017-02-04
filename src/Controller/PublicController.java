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

    //La vista Auth è quella in cui ci sono le carte della vista pubblica
    private Auth view;
    //Instanzio il nuovo cardLAyout
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel;

    //Passo al costruttore del controller la vista con le carte
    public PublicController(Auth view) {

        this.view = view;
        //prendo il pannello che racchiude le 3 carte e lo chiamo mainPanel
        mainPanel = view.getMainPanel();
        cardLayout = (CardLayout)mainPanel.getLayout();
        //il costruttore Public deve mostrare la carta index del contenitore mainPanel
        cardLayout.show(mainPanel, "Index");
        //Setto la vista visibile
        Auth.setVisible(true);
        //Ottengo le altre carte...Auth ha un metodo get che permette di ottenre le carte.
        Index indexview = view.getIndexview();
        Login loginview = view.getLoginview();
        Registrazione registrazioneview = view.getRegistrazioneview();

        //Listener al bottone di login di Indexview che permette di vedere la carte dove si inseriscono le credenziali
        indexview.addLoginButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"Login");
            }
        });
        //Listener al bottone di registrazione di Indexview che permette di vedere la carte dove è possibile registrarsi
        indexview.addRegistratiButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel,"Registrazione");
            }
        });
        //Listener al bottone di azzera di Registrazioneview che permette di azzerare tutti i campi. Utilizza la funzione azzeraCampi della vista registrazione
        registrazioneview.addAzzeraButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazioneview.azzeraCampi();
            }
        });
        //Listener al bottone di Indietro di Refistrazioneview che permette di azzerare i campi e tornare alla carta Indexview
        registrazioneview.addIndietroButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrazioneview.azzeraCampi();
                cardLayout.show(mainPanel, "Index");
            }
        });

        //Codice del listener che si attiva quando premo il bottone di Conferma Registrazione della vista registrazione
        registrazioneview.addCompletaRegistrazioneButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //si prendono i valori inseriti nelle JTextfiel e si eliminano gli spazi
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
