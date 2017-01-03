package View.Public;

import Controller.PublicController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Login {
    private JTextField username;
    private JPasswordField password;
    private JButton accediButton;
    private JPanel mainPanel;
    private JButton indietroButton;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Login(JFrame finestra) {

        accediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passText = new String(password.getPassword());
                new PublicController(finestra).completaloginAction(username.getText(), passText);
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PublicController(finestra).indexAction();
            }
        });
    }
}
