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
        String dati= utente.valueString(utente);
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
