package Controller;

import View.Public.Index;
import View.User.Alimentazione;
import View.User.Allenamento;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lorenzobraconi on 03/01/17.
 */
public class UserController {

    protected JFrame finestra;

    public UserController(JFrame finestra) {
        this.finestra = finestra;
    }

    public void allenamentoAction(JPanel variable){
        Allenamento view = new Allenamento(finestra);
        renderprova(view.getAllenPanel(), variable);
    }

    public void renderprova(JPanel view, JPanel variable){            //andrebbe sull'Helpers Controller; da creare...
        variable.removeAll();
        try {
            variable.add(view);
            variable.validate();
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
