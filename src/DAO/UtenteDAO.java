package DAO;


import DAO.Dbtable.Utente;
import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;

/**
 * La classe UtenteDAO contiene i metodi per la gestione dei dati della tabella "utente" del database.
 */

public class UtenteDAO {
    protected Utente tabella;

    public UtenteDAO() {
        tabella= new Utente();
    }

    /**
     * Metodo che inserisce un nuovo utente a partire da un UtenteObject
     * @param utente Variabile di tipo UtenteObject il cui valore degli attributi costituiscono i dati da inserire
     */

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
        dati=dati+",0";
        dati=dati+",0";
        tabella.insert(dati);
        tabella.execute();
    }

    /**
     * Metodo che verifica se un utente è presente nel database a partire dal suo username
     * @param user Username di cui si vuole verificare la presenza
     * @return true se l'username è presente, false se non presente
     */

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

    /**
     * Metodo che verifica se la coppia username e password è presente nel database
     * @param user Stringa del nome
     * @param pass Stringa della password
     * @return true se la coppia user-pass è presente, false se non è presente
     */

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

    /**
     * Metedo che recupera un utente in base all'username e ne restituisce un UtenteObject
     * @param user Username dell'utente che si intende recuperare
     * @return Variabile di tipo UtenteObject i cui attributi sono settati in base ai dati dell'utente recuperato
     */

    public UtenteObject getUserByUsername(String user){
        tabella.select();
        tabella.where("username='" + user + "'");
        ResultSet risultato= tabella.fetch();
        UtenteObject utentecorrente = new UtenteObject();
        try {
            risultato.next();
            utentecorrente.setUsername(risultato.getString("username"));
            utentecorrente.setPassword(risultato.getString("password"));
            utentecorrente.setNome(risultato.getString("nome"));
            utentecorrente.setCognome(risultato.getString("cognome"));
            utentecorrente.setEta(risultato.getInt("eta"));
            utentecorrente.setAltezza(risultato.getFloat("altezza"));
            utentecorrente.setPeso(risultato.getFloat("peso"));
            utentecorrente.setPeso_forma(risultato.getFloat("peso_forma"));
            utentecorrente.setAllergia(AllergiaEnum.valueOf(risultato.getString("allergia")));
            utentecorrente.setLavoro(LavoroEnum.valueOf(risultato.getString("lavoro")));
            utentecorrente.setLivello_attivita_fisica(LivelloAttivitaFisicaEnum.valueOf(risultato.getString("livello_attivita_fisica")));
            utentecorrente.setEmail(risultato.getString("email"));
            utentecorrente.setSesso(risultato.getInt("sesso"));
            utentecorrente.setProgramma_alimentare(new ProgrammaAlimentareDAO().getProgrammaAlimentare(risultato.getBoolean("prog_alim_comb"),risultato.getInt("programma_alimentare")));
            utentecorrente.setProg_alim_comb(risultato.getBoolean("prog_alim_comb"));
            utentecorrente.setProgramma_allenamento(new ProgrammaAllenamentoDAO().getProgrammaAllenamento(risultato.getBoolean("prog_allen_comb"),risultato.getInt("programma_allenamento")));
            utentecorrente.setProg_allen_comb(risultato.getBoolean("prog_allen_comb"));
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
        return utentecorrente;
    }

    /**
     * Metodo che modifica le informazioni di utente in base al suo username
     * @param username Username dell'utente di cui si vogliono modificare le informazioni
     * @param campoutente Map: la chiave è di tipo String e indica il campo del valore da modificare, il valore è di tipo Object con il nuovo valore
     */

    public void updateInfoUtente(String username, Map<String,Object> campoutente){
        String dati = "";
        Iterator<Map.Entry<String,Object>> iterator = campoutente.entrySet().iterator();
        Map.Entry entry = iterator.next();
        dati += entry.getKey()+ "='" + entry.getValue() + "'";
        while (iterator.hasNext()) {
            entry = iterator.next();
            dati += ", "+entry.getKey()+ "='" + entry.getValue() + "'";
        }
        tabella.update(dati);
        tabella.where("username='" + username + "'");
        tabella.execute();
    }

    /**
     * Metoco che elimina un utente in base al suo username
     * @param username Username dell'utente che si vuole eliminare dal database
     */

    public void eliminaUtente(String username){
        tabella.delete();
        tabella.where("username='" + username + "'");
        tabella.execute();
    }
}
