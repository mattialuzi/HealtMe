package Controller;

import Model.Dbtable.Utente;
import Model.UtenteModel;
import View.Public.Index;
import View.User.Alimentazione;
import View.User.Allenamento;
import View.User.Profilo;
import Object.*;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

/**
 * Created by lorenzobraconi on 03/01/17.
 */
public class UserController {

    protected JFrame finestra;
    protected String username;

    public UserController(JFrame finestra, String username) {
        this.finestra = finestra;
        this.username = username;
    }

    public void allenamentoAction(JPanel variable){
        Allenamento view = new Allenamento(finestra);
        renderprova(view.getAllenPanel(), variable);
    }

    public void profiloAction(JPanel variable){
        UtenteModel user = new UtenteModel();
        ResultSet utente = user.getUserByUsername(username);
        Profilo view = new Profilo(finestra, utente);
        renderprova(view.getMainPanel(), variable);
    }

    public void renderprova(JPanel view, JPanel variable){            //andrebbe sull'Helpers Controller; da creare...
        finestra.getContentPane().remove(variable);
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
