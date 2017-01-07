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

        public void addAccediButton(ActionListener listener) {accediButton.addActionListener(listener); }

       /* indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.indexAction();
            }
        }); */

}
