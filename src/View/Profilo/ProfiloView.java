package View.Profilo;

import javax.swing.*;
import Object.UtenteObject;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloView {
    private JPanel mainPanel;
    private JButton eliminaAccountButton;
    private JLabel usernameLabel;
    private JLabel nomeLabel;
    private JLabel cognomeLabel;
    private JLabel sessoLabel;
    private JLabel etaLabel;
    private JLabel altezzaLabel;
    private JLabel pesoLabel;
    private JLabel lavoroLabel;
    private JLabel attivitafisicaLabel;
    private JLabel allergiaLabel;
    private JLabel pesoformaLabel;
    private JLabel emailLabel;
    private JButton usernameModButton;
    private JButton nomeModButton;
    private JButton cognomeModButton;
    private JButton etaModButton;
    private JButton altezzaModButton;
    private JButton pesoModButton;
    private JButton lavoroModButton;
    private JButton attivitaModButton;
    private JButton allergiaModButton;
    private JButton emailModButton;

    public ProfiloView() {

    }

    public void setInfoUtente(UtenteObject utente){
        usernameLabel.setText(utente.getUsername());
        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        etaLabel.setText(String.valueOf(utente.getEta()));
        altezzaLabel.setText(String.valueOf(utente.getAltezza()));
        pesoLabel.setText(String.valueOf(utente.getPeso()));
        pesoformaLabel.setText(String.valueOf(utente.getPeso_forma()));
        emailLabel.setText(utente.getEmail());
        lavoroLabel.setText(String.valueOf(utente.getLavoro()));
        attivitafisicaLabel.setText(String.valueOf(utente.getLivello_attivita_fisica()));
        allergiaLabel.setText(String.valueOf(utente.getAllergia()));
        if(utente.getSesso()==1){
            sessoLabel.setText("Uomo");
        } else sessoLabel.setText("Donna");
    }

    public void addNomeModButtonListener(ActionListener listener){
        nomeModButton.addActionListener(listener);
    }

    public void addEliminaButtonListener(ActionListener listener){
        eliminaAccountButton.addActionListener(listener);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
