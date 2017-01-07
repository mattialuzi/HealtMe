package View.Public;



import Controller.PublicController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index {

    private JButton loginButton;
    private JButton registratiButton;
    private JPanel mainPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Index() {

    }

    public void addLoginButtonListener(ActionListener listener){
        loginButton.addActionListener(listener);
    }

    public void addRegistratiButtonListener(ActionListener listener){
        registratiButton.addActionListener(listener);
    }
}
