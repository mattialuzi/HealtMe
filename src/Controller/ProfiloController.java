package Controller;

import Model.UtenteModel;
import Object.UtenteObject;
import View.Auth;
import View.Profilo.ProfiloView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloController {

    public ProfiloController(ProfiloView profilo, UtenteObject utente) {

        profilo.setInfoUtente(utente);
        profilo.addNomeModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = utente.getNome();
                HashMap campoutente = new HashMap();
                campoutente.put("nome", nome);
                String nuovoNome = modificaInfoUtente(campoutente, utente.getUsername());
                utente.setNome(nuovoNome);
                profilo.setInfoUtente(utente);
            }
        });

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

    public String modificaInfoUtente(HashMap info, String username ){
        Set keys = info.keySet();
        Iterator i= keys.iterator();
        String key = (String) i.next();
        String value = (String) info.get(key);
        String dati="";
        String name = JOptionPane.showInputDialog(null,
                "Modifica il tuo " + key + ": ", value);
        dati=dati + key+"='" + name+ "'";
        UtenteModel utentemodel = new UtenteModel();
        utentemodel.updateInfoUtente(username, dati);
        return name;
    }
}
