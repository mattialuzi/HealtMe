package View;

import View.Public.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class Auth {

    private static JFrame frame = new JFrame("Health Me!");
    private JPanel mainPanel;
    private JPanel indexPanel;
    private JPanel loginPanel;
    private JPanel registrazionePanel;
    private Index indexview;
    private Login loginview;
    private Registrazione registrazioneview;

    public Auth() {

        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(500,300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        indexview = new Index();
        loginview = new Login();
        registrazioneview = new Registrazione();
        indexPanel = indexview.getMainPanel();
        loginPanel = loginview.getMainPanel();
        registrazionePanel = registrazioneview.getMainPanel();

        mainPanel.add(indexPanel,"Index");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(registrazionePanel,"Registrazione");
    }

    public static void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    public Index getIndexview() {
        return indexview;
    }

    public Login getLoginview() { return loginview; }

    public Registrazione getRegistrazioneview() { return registrazioneview; }
}
