package View.User;

import Controller.PublicController;
import Controller.UserController;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by lorenzobraconi on 03/01/17.
 */
public class Homepage {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JPanel variablePanel;
    private JMenu menu;


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Homepage(JFrame finestra) {
        JMenuItem alim = menu.add (new JMenuItem("Alimentazione"));
        JMenuItem allen= menu.add (new JMenuItem("Allenamento"));
        menu.add (new JMenuItem("Profilo"));
        allen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserController(finestra).allenamentoAction(variablePanel);
            }
        });

    }


}
