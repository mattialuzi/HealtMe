package View.Public;

import Controller.PublicController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Login {

    private PublicController controller;
    private JTextField username;
    private JPasswordField password;
    private JButton accediButton;
    private JPanel mainPanel;
    private JButton indietroButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Login() {


    }

    public void addAccediButtonListener(ActionListener listener) {accediButton.addActionListener(listener); }

    public void addIndietroButtonListener(ActionListener listener) {indietroButton.addActionListener(listener); }

    public String getUsername() {
        return this.username.getText();
    }

    public String getPassword() {
       return this.password.getText();
    }

    public void azzeraCampi(){
        username.setText("");
        password.setText("");
    }

       /* indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.indexAction();
            }
        }); */

}
