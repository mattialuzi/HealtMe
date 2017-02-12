package Presenter;

import DAO.UtenteDAO;
import DAO.ProgressiDAO;
import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import View.Auth;
import View.Menu;
import View.Profilo.ProfiloView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;


/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloPresenter {

    private ProfiloView profilo;

    public ProfiloPresenter(Menu menu, UtenteObject utente) {

        this.profilo = menu.getProfiloview();
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
                int replay = JOptionPane.showConfirmDialog(null,"Modificando l'altezza anche il peso forma subirà variazioni.\n" +
                        " I programmi attuali creati da Health Me! non terranno conto della nuova modifica.\n" +
                        "Se vorrai un programma aggiornato potrai crearne uno nuovo dal menù laterale.","Attenzione",JOptionPane.YES_NO_OPTION);
                if (replay == JOptionPane.YES_OPTION) {
                    String nuovaAltezza = modificaInfoUtente(campo, altezza, utente.getUsername(), "virgola");
                    if (!nuovaAltezza.equals(altezza)) {
                        float nuovoPesoforma = profilo.calcoloPesoForma(Float.parseFloat(nuovaAltezza), utente.getSesso());
                        UtenteDAO utenteDAO = new UtenteDAO();
                        HashMap hashmap = new HashMap();
                        hashmap.put(campo,Float.toString(nuovoPesoforma));
                        utenteDAO.updateInfoUtente(utente.getUsername(),hashmap);
                        utente.setPeso_forma(nuovoPesoforma);
                        utente.setAltezza(Float.parseFloat(nuovaAltezza));
                        profilo.setInfoUtente(utente);
                    }
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
                    new ProgressiDAO().updateInfoProgressi(utente.getUsername(), LocalDate.now(),campo,nuovoPeso);
                }
            }
        });

        profilo.addLavoroModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lavoro = String.valueOf(utente.getLavoro());
                int replay = JOptionPane.showConfirmDialog(null,"Modificando il lavoro il programma alimentare "+
                        "attuale creato da Health Me! non terrà conto della nuova modifica.\n" +
                        "Se vorrai un programma aggiornato potrai crearne uno nuovo dal menù laterale.","Attenzione",JOptionPane.YES_NO_OPTION);
                if (replay == JOptionPane.YES_OPTION) {
                Object nuovoLavoro = JOptionPane.showInputDialog(null, "Modifica lavoro :" ,"Input",JOptionPane.INFORMATION_MESSAGE, null, LavoroEnum.values(),LavoroEnum.valueOf(lavoro) );
                if( nuovoLavoro!=null && !nuovoLavoro.toString().equals(lavoro)) {
                    modificaInfoUtenteEnum("lavoro", nuovoLavoro.toString(), utente.getUsername());
                    utente.setLavoro((LavoroEnum) nuovoLavoro);
                    profilo.setInfoUtente(utente);
                    }
                }
            }
        });

        profilo.addAttivitaModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String attivita = String.valueOf(utente.getLivello_attivita_fisica());
                int replay = JOptionPane.showConfirmDialog(null,"Modificando il livello di attività fisica " +
                        "i programmi attuali creati da Health Me! non terranno conto della nuova modifica.\n" +
                        "Se vorrai un programma aggiornato potrai crearne uno nuovo dal menù laterale.","Attenzione",JOptionPane.YES_NO_OPTION);
                if (replay == JOptionPane.YES_OPTION) {
                Object nuovaAttivita = JOptionPane.showInputDialog(null, "Modifica il Livello di Attività Fisica :" ,"Input",JOptionPane.INFORMATION_MESSAGE, null, LivelloAttivitaFisicaEnum.values(),LivelloAttivitaFisicaEnum.valueOf(attivita));
                if( nuovaAttivita!=null && !nuovaAttivita.toString().equals(attivita)) {
                    modificaInfoUtenteEnum("livello_attivita_fisica", nuovaAttivita.toString(), utente.getUsername());
                    utente.setLivello_attivita_fisica((LivelloAttivitaFisicaEnum) nuovaAttivita);
                    profilo.setInfoUtente(utente);
                    }
                }
            }
        });

        profilo.addAllergiaModButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String allergia = String.valueOf(utente.getAllergia());
                int replay = JOptionPane.showConfirmDialog(null,"Modificando l'allergia " +
                        "il programma alimentare attuale creato da Health Me! non terrà conto della nuova modifica.\n" +
                        "Se vorrai un programma aggiornato potrai crearne uno nuovo dal menù laterale.","Attenzione",JOptionPane.YES_NO_OPTION);
                if (replay == JOptionPane.YES_OPTION) {
                Object nuovaAllergia = JOptionPane.showInputDialog(null, "Modifica allergia :" ,"Input",JOptionPane.INFORMATION_MESSAGE, null, AllergiaEnum.values(),AllergiaEnum.valueOf(allergia) );
                if( nuovaAllergia!=null && !nuovaAllergia.toString().equals(allergia)) {
                    modificaInfoUtenteEnum("allergia", nuovaAllergia.toString(), utente.getUsername());
                    utente.setAllergia((AllergiaEnum) nuovaAllergia);
                    profilo.setInfoUtente(utente);
                    }
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
                    UtenteDAO utenteDAO = new UtenteDAO();
                    utenteDAO.eliminaUtente(utente.getUsername());
                    Auth view = new Auth();
                    new PublicPresenter(view);
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
            UtenteDAO tabella = new UtenteDAO();
            boolean validator = tabella.findUserByUsername(newvalue);
            if (validator) {
                JOptionPane.showMessageDialog(null, "Username già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                return info;
            }
        }
        HashMap<String,Object> campoutente = new HashMap<String,Object>();
        campoutente.put(campo, newvalue);
        UtenteDAO utenteDAO = new UtenteDAO();
        utenteDAO.updateInfoUtente(username, campoutente);
        return newvalue;

    }

    public void modificaInfoUtenteEnum(String campo, String info, String username){
            HashMap<String,Object> campoutente = new HashMap<String,Object>();
            campoutente.put(campo, info);
            UtenteDAO utenteDAO = new UtenteDAO();
            utenteDAO.updateInfoUtente(username, campoutente);
        }

}
