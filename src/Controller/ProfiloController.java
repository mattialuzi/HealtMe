package Controller;

import Model.UtenteModel;
import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import View.Auth;
import View.Profilo.ProfiloView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloController {

    private ProfiloView profilo;

    public ProfiloController(ProfiloView profilo, UtenteObject utente) {

        this.profilo = profilo;
        profilo.setInfoUtente(utente);

        profilo.addUsernameModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String  username = utente.getUsername();
                String campo = "username";
                String nuovoUsername = modificaInfoUtente(campo, username, utente.getUsername(), "testo");
                if(!nuovoUsername.equals(username)) {
                    utente.setUsername(nuovoUsername);
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addNomeModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = utente.getNome();
                String campo = "nome";
                String nuovoNome = modificaInfoUtente(campo,nome, utente.getUsername(), "testo");
                if(!nuovoNome.equals(nome)) {
                    utente.setNome(nuovoNome);
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addCognomeModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cognome = utente.getCognome();
                String campo = "cognome";
                String nuovoCognome = modificaInfoUtente(campo,cognome, utente.getUsername(), "testo");
                if(!nuovoCognome.equals(cognome)) {
                    utente.setCognome(nuovoCognome);
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addEtaModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eta = String.valueOf(utente.getEta());
                String campo = "eta";
                String nuovaEta = modificaInfoUtente(campo,eta, utente.getUsername(), "eta");
                if(!nuovaEta.equals(eta)) {
                    utente.setEta(Integer.parseInt(nuovaEta));
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addAltezzaModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String altezza = String.valueOf(utente.getAltezza());
                String campo = "altezza";
                String nuovaAltezza = modificaInfoUtente(campo,altezza, utente.getUsername(), "virgola");
                if(!nuovaAltezza.equals(altezza)) {
                    utente.setAltezza(Float.parseFloat(nuovaAltezza));
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addPesoModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String peso = String.valueOf(utente.getPeso());
                String campo = "peso";
                String nuovoPeso = modificaInfoUtente(campo,peso, utente.getUsername(), "virgola");
                if(!nuovoPeso.equals(peso)) {
                    utente.setPeso(Float.parseFloat(nuovoPeso));
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addLavoroModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lavoro = String.valueOf(utente.getLavoro());
                Object nuovoLavoro = JOptionPane.showInputDialog(null, "Modifica lavoro :" ,"Input",JOptionPane.INFORMATION_MESSAGE, null, LavoroEnum.values(),LavoroEnum.valueOf(lavoro) );
                if( nuovoLavoro!=null && !nuovoLavoro.toString().equals(lavoro)) {
                    modificaInfoUtenteEnum("lavoro", nuovoLavoro.toString(), utente.getUsername());
                    utente.setLavoro((LavoroEnum) nuovoLavoro);
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addAttivitaModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attivita = String.valueOf(utente.getLivello_attivita_fisica());
                Object nuovaAttivita = JOptionPane.showInputDialog(null, "Modifica il Livello di Attività Fisica :" ,"Input",JOptionPane.INFORMATION_MESSAGE, null, LivelloAttivitaFisicaEnum.values(),LivelloAttivitaFisicaEnum.valueOf(attivita));
                if( nuovaAttivita!=null && !nuovaAttivita.toString().equals(attivita)) {
                    modificaInfoUtenteEnum("livello_attivita_fisica", nuovaAttivita.toString(), utente.getUsername());
                    utente.setLivello_attivita_fisica((LivelloAttivitaFisicaEnum) nuovaAttivita);
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addAllergiaModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String allergia = String.valueOf(utente.getAllergia());
                Object nuovaAllergia = JOptionPane.showInputDialog(null, "Modifica allergia :" ,"Input",JOptionPane.INFORMATION_MESSAGE, null, AllergiaEnum.values(),AllergiaEnum.valueOf(allergia) );
                if( nuovaAllergia!=null && !nuovaAllergia.toString().equals(allergia)) {
                    modificaInfoUtenteEnum("allergia", nuovaAllergia.toString(), utente.getUsername());
                    utente.setAllergia((AllergiaEnum) nuovaAllergia);
                    profilo.setInfoUtente(utente);
                }
            }
        });

        profilo.addEmailModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = utente.getEmail();
                String campo = "email";
                String nuovaEmail = modificaInfoUtente(campo,email, utente.getUsername(), "email");
                if(!nuovaEmail.equals(email)) {
                    utente.setEmail(nuovaEmail);
                    profilo.setInfoUtente(utente);
                }
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

    public String modificaInfoUtente(String campo, String info, String username, String tipocampo ) {

        String newvalue = JOptionPane.showInputDialog(null, "Modifica " + campo + ": ", info);
        if(newvalue == null){
            return info;
        } else if (("".equals(newvalue)) || !(profilo.validate(newvalue, tipocampo))) {
            JOptionPane.showMessageDialog(null, "Valore nullo o non valido", "Errore", JOptionPane.ERROR_MESSAGE);
            return info;
        } else if((campo.equals("username")) && (newvalue.equals(info))){
            return info;
        } else if(campo.equals("username")) {
            UtenteModel tabella = new UtenteModel();
            boolean validator = tabella.findUserByUsername(newvalue);
            if (validator) {
                JOptionPane.showMessageDialog(null, "Username già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                return info;
            }
        }
        HashMap campoutente = new HashMap();
        campoutente.put(campo, newvalue);
        UtenteModel utentemodel = new UtenteModel();
        utentemodel.updateInfoUtente(username, campoutente);
        return newvalue;

    }

    public void modificaInfoUtenteEnum(String campo, String info, String username){
            HashMap campoutente = new HashMap();
            campoutente.put(campo, info);
            UtenteModel utentemodel = new UtenteModel();
            utentemodel.updateInfoUtente(username, campoutente);
        }

}
