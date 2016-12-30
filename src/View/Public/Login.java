package View.Public;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Login {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registratiButton;
    private JPanel mainPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Login(JFrame finestra) {

        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // da finire...
            }
        });
    }
}
