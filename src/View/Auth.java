package View;

import View.Public.*;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 *  La classe Auth contiene attributi e metodi associati al file XML Auth.form
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
        URL iconURL = getClass().getResource("resources/pulseicon.png");
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());
        frame.setContentPane(mainPanel);
        frame.setMinimumSize(new Dimension(500,300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public static JFrame getFrame() {
        return frame;
    }

    /**  Metodo che crea componenti dell'User Interface  */

    private void createUIComponents() {
        indexview = new Index();
        loginview = new Login();
        registrazioneview = new Registrazione();
        indexPanel = indexview.getMainPanel();
        loginPanel = loginview.getMainPanel();
        registrazionePanel = registrazioneview.getMainPanel();
    }
}
