package Controller;

import Model.UtenteModel;
import Object.UtenteObject;
import View.Auth;
import View.Profilo.ProfiloView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloController {

    public ProfiloController(ProfiloView profilo, UtenteObject utente) {

        profilo.setInfoUtente(utente);

        profilo.addEliminaButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int replay = JOptionPane.showConfirmDialog(null, "Sei sicuro di voler eliminare il tuo Account e i relativi dati?", "Eliminazione Account", JOptionPane.YES_NO_OPTION);
                if (replay == JOptionPane.YES_OPTION){
                    UtenteModel utentemodel = new UtenteModel();
                    utentemodel.eliminaUtente(utente.getUsername());
                    Auth view = new Auth();
                    new PublicController(view);
                }
            }
        });
    }
}
