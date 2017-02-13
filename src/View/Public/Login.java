package View.Public;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Login {

    private JTextField username;
    private JPasswordField password;
    private JButton accediButton;
    private JPanel mainPanel;
    private JButton indietroButton;

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

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
