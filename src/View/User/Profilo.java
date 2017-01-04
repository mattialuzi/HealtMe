package View.User;

import javax.swing.*;
import Object.*;

import java.sql.ResultSet;

/**
 * Created by lorenzobraconi on 04/01/17.
 */
public class Profilo {

    private JPanel mainPanel;
    private JLabel nome;
    private JLabel cognome;
    private JLabel username;
    private JLabel Nome;
    private JLabel Cognome;
    private JLabel Username;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Profilo(JFrame finestra, ResultSet utente) {

        try {
            while (utente.next()) {
                String io = utente.getString("username");
                nome.setText(io);
                nome.paintImmediately(nome.getVisibleRect());
                String pass=utente.getString("password");
                cognome.setText(pass);
            }
        }
        catch (Exception e){
            System.out.println("C'è un errore all'interno del datisociobyid " + e);
        }


    }


}
