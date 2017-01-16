package Helpers;

import javax.swing.*;

/**
 * Created by ALLDE on 29/12/2016.
 */
public class Controller {

    public Controller() {
    }

    public void MenuVisibility(JPanel menuPanel){
        if (menuPanel.isVisible()) {
            menuPanel.setVisible(false);
        } else
            menuPanel.setVisible(true);
    }

    public void SubMenuVisibility(JPanel subPanel){
        if (subPanel.isVisible()) {
            subPanel.setVisible(false);
        } else
            subPanel.setVisible(true);
    }

    public boolean checkIntero(String elemento){
        boolean ritorno = false;
        String regola = "[0-9]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }
}
