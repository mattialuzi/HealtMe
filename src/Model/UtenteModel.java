package Model;


import Model.Dbtable.Utente;
import Object.UtenteObject;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class UtenteModel {
    protected Utente tabella;

    public UtenteModel() {
        tabella= new Utente();
    }

    public void inserisciUtente(UtenteObject utente){
        String dati= "'"+utente.getUsername()+"'";
        dati=dati+",'"+utente.getPassword()+"'";
        dati=dati+",'"+utente.getNome()+"'";
        dati=dati+",'"+utente.getCognome()+"'";
        dati=dati+","+String.valueOf(utente.getEta())+"";
        dati=dati+","+String.valueOf(utente.getSesso())+"";
        dati=dati+","+String.valueOf(utente.getAltezza())+"";
        dati=dati+","+String.valueOf(utente.getPeso())+"";
        dati=dati+",'"+String.valueOf(utente.getLavoro())+"'";
        dati=dati+",'"+String.valueOf(utente.getLivello_attivita_fisica())+"'";
        dati=dati+",'"+String.valueOf(utente.getAllergia())+"'";
        dati=dati+","+String.valueOf(utente.getPeso_forma())+"";
        dati=dati+",'"+utente.getEmail()+"'";
        dati=dati+",null";
        dati=dati+",null";
        dati=dati+",null";
        dati=dati+",null";
        tabella.insert(dati);
        tabella.execute();
    }

    public boolean findUserByUsername(String user){
        boolean success=false;
        tabella.select();
        tabella.where("username='" + user + "'");
        int n = tabella.count(tabella.fetch());
        if (n==1)
            success=true;
        else
            success=false;
        return success;
    }

    public boolean findUserByCredential(String user, String pass){
        boolean success = false;
        tabella.select();
        tabella.where("username='" + user + "' and password='" + pass+"'");
        int n = tabella.count(tabella.fetch());
        if(n==1)
            success = true;
        else
            success=false;
        return success;
    }

    public ResultSet getUserByUsername(String user){
        tabella.select();
        tabella.where("username='" + user + "'");
        ResultSet utente= tabella.fetch();
        return utente;
    }

    public void updateInfoUtente(String username, HashMap campoutente){
        Set keys = campoutente.keySet();
        Iterator i= keys.iterator();
        String key = (String) i.next();
        String value = (String) campoutente.get(key);
        String dati = key+"='" + value+ "'";
        tabella.update(dati);
        tabella.where("username='" + username + "'");
        tabella.execute();
    }

    public void eliminaUtente(String username){
        tabella.delete();
        tabella.where("username='" + username + "'");
        tabella.execute();
    }
}
