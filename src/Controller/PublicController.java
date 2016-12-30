package Controller;

import View.Public.*;
import javax.swing.*;
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

    public void render(JPanel view){            //andrebbe sull'Helpers Controller; da creare...
        finestra.getContentPane().removeAll();
        try {
            finestra.add(view);
            finestra.validate();
            finestra.setLocation(300,20);
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
