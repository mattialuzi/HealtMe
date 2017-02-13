package View.Profilo;

import javax.imageio.ImageIO;
import javax.swing.*;
import Helpers.View;
import Object.UtenteObject;

import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloView extends View {
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
        try {
            Image img = ImageIO.read(getClass().getResource("../resources/editicon.png"));
            Image newimg = img.getScaledInstance( 25, 25,  java.awt.Image.SCALE_SMOOTH );
            usernameModButton.setIcon(new ImageIcon(newimg));
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void setInfoUtente(UtenteObject utente){
        usernameLabel.setText(utente.getUsername());
        nomeLabel.setText(utente.getNome());
        cognomeLabel.setText(utente.getCognome());
        etaLabel.setText(String.valueOf(utente.getEta()));
        altezzaLabel.setText(String.valueOf(Math.round((utente.getAltezza()*100d))/100d));
        pesoLabel.setText(String.valueOf(utente.getPeso()));
        pesoformaLabel.setText(String.valueOf(Math.round((utente.getPeso_forma()*100d))/100d));
        emailLabel.setText(utente.getEmail());
        lavoroLabel.setText(String.valueOf(utente.getLavoro()));
        attivitafisicaLabel.setText(String.valueOf(utente.getLivello_attivita_fisica()));
        allergiaLabel.setText(String.valueOf(utente.getAllergia()));
        if(utente.getSesso()==1){
            sessoLabel.setText("Uomo");
        } else sessoLabel.setText("Donna");
    }

    public void addUsernameModButtonListener(ActionListener listener){ usernameModButton.addActionListener(listener); }

    public void addNomeModButtonListener(ActionListener listener){
        nomeModButton.addActionListener(listener);
    }

    public void addCognomeModButtonListener(ActionListener listener){
        cognomeModButton.addActionListener(listener);
    }

    public void addEtaModButtonListener(ActionListener listener){
        etaModButton.addActionListener(listener);
    }

    public void addAltezzaModButtonListener(ActionListener listener){
        altezzaModButton.addActionListener(listener);
    }

    public void addPesoModButtonListener(ActionListener listener){
        pesoModButton.addActionListener(listener);
    }

    public void addLavoroModButtonListener(ActionListener listener){
        lavoroModButton.addActionListener(listener);
    }

    public void addAttivitaModButtonListener(ActionListener listener){
        attivitaModButton.addActionListener(listener);
    }

    public void addAllergiaModButtonListener(ActionListener listener){
        allergiaModButton.addActionListener(listener);
    }

    public void addEmailModButtonListener(ActionListener listener){
        emailModButton.addActionListener(listener);
    }

    public void addEliminaButtonListener(ActionListener listener){
        eliminaAccountButton.addActionListener(listener);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
