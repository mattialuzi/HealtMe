package View.Public;


import Controller.PublicController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Index {

    private PublicController controller;
    private JButton loginButton;
    private JButton registratiButton;
    private JPanel mainPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Index(PublicController controller) {

        this.controller = controller;
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.loginAction();
            }
        });
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.registrazioneAction();
            }
        });
    }
}
