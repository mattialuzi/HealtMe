package DAO;

import DAO.Dbtable.Giorno_allen_dinamico;
import DAO.Dbtable.Giorno_allen_eff;
import DAO.Dbtable.Giorno_allen_prog;
import Object.GiornoAllenEffettivoObject;
import Object.GiornoAllenProgObject;
import Object.GiornoAllenDinamicoObject;
import Object.SedutaObject;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

/**
 * La classe GiornoAllenDAO contiene i metodi per la gestione dei dati delle tabella "giorno_allen_effettivo","giorno_allen_progr","giorno_allen_dinamico" del database
 */

public class GiornoAllenDAO {
    protected Giorno_allen_eff effettivo;
    protected Giorno_allen_prog programmato;
    protected Giorno_allen_dinamico dinamico;


    public GiornoAllenDAO() {
        effettivo = new Giorno_allen_eff();
        programmato = new Giorno_allen_prog();
        dinamico = new Giorno_allen_dinamico();
    }

    /**
     * Metodo che recupera un giorno d'allenamento effettivo dalla tabella "giorno_allen_effettivo" del database in base a username e data
     * @param username Nome dell'username
     * @param data Data del giorno
     * @return Una variabile di tipo GiornoAllenEffettivoObject costruito in base alle informazioni del giorno recuperato
     */

    public GiornoAllenEffettivoObject getGiornoAllenEffettivo(String username, LocalDate data){
        effettivo.select();
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = effettivo.fetch();
        try{
            if(rs.isBeforeFirst()){
                rs.next();
                SedutaDAO sedutaDAO = new SedutaDAO();
                SedutaObject seduta = sedutaDAO.getSedutaById(rs.getInt(4));
                GiornoAllenEffettivoObject giorno = new GiornoAllenEffettivoObject(username, data, seduta);
                giorno.setCalorie(rs.getInt("cal_consumate"));
                giorno.setCompletato(rs.getBoolean("completato"));
                return giorno;
            } else {
                GiornoAllenEffettivoObject nuovogiorno = new GiornoAllenEffettivoObject(username, data);
                inserisciGiornoAllenEff(nuovogiorno);
                return nuovogiorno;
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
            return null;
        }
    }

    /**
     * Metodo che modifica un giorno d'allenamento effettivo della tabella "giorno_allen_effettivo" del database
     * @param username Nome dell'username
     * @param data Data del giorno
     * @param map Mappa la cui chiave è di tipo String ed il valore è un tipo generico
     * @param <V> Tipo generico
     */

    public <V> void updateGiornoAllenEff(String username, LocalDate data, Map<String,V> map) {
        String dati = "";
        Iterator<Map.Entry<String,V>> iterator = map.entrySet().iterator();
        Map.Entry entry = iterator.next();
        dati += entry.getKey()+ "='" + entry.getValue() + "'";
        while (iterator.hasNext()) {
            entry = iterator.next();
            dati += ","+entry.getKey()+ "='" + entry.getValue() + "'";
        }
        effettivo.update(dati);
        effettivo.where("username='" + username + "' AND data='" + data + "'");
        effettivo.execute();
    }

    /**
     * Metodo che inserisce un giorno d'allenamento effettivo nella tabella "giorno_allen_effettivo" del database in base ad un GiornoAllenEffettivoObject
     * @param giornoeff Variabile di tipo GiornoAllenEffettivoObject il cui valore degli attributi costituiscono le informazioni da inserire
     */

    public void inserisciGiornoAllenEff(GiornoAllenEffettivoObject giornoeff){
        String dati= "'" + giornoeff.getUsername()+"'";
        dati = dati +  ", '" + String.valueOf(giornoeff.getData() +"'");
        dati = dati +  ", " + String.valueOf(giornoeff.getCalorie());
        dati = dati + ", " + giornoeff.getSeduta().getId();
        dati = dati + "," + giornoeff.isCompletato();
        effettivo.insert(dati);
        effettivo.execute();
    }

    /**
     * Metodo che inserisce un giorno d'allenamento programmato nella tabella "giorno_allen_progr" del database in base ad un GiornoAllenProgObject
     * @param giornoprog Variabile di tipo GiornoAllenProgObject il cui valore degli attributi costituiscono le informazioni da inserire
     */

    public void inserisciGiornoAllenProg(GiornoAllenProgObject giornoprog){
        String dati = "'" + giornoprog.getId_giorno()+"'";
        dati = dati + ", '" + giornoprog.getCalorie() + "'";
        dati = dati + ", " + giornoprog.getSeduta().getId();
        programmato.insert(dati);
        int idgiorno = programmato.executeForKey();
        giornoprog.setId_giorno(idgiorno);
    }

    /**
     * Metodo che inserisce un giorno d'allenamento dinamico nella tabella "giorno_allen_dinamico" del database in base ad un GiornoAllenDinamicoObject
     * @param giornodinamico Variabile di tipo GiornoAllenDinamicoObject il cui valore degli attributi costituiscono le informazioni da inserire
     */

    public void inserisciGiornoAllenDinamico(GiornoAllenDinamicoObject giornodinamico){
        String dati = "'" + giornodinamico.getId_programma()+"'" ;
        dati = dati + ", '" + giornodinamico.getData() + "'";
        dati = dati + ", '" + giornodinamico.getCalorie() + "'";
        dati = dati + ", " + giornodinamico.getSeduta().getId();
        dinamico.insert(dati);
        dinamico.execute();
    }

    /**
     * Metodo che recupera un giorno d'allenamento della tabella "giorno_allen_dinamico" del database in base al codice del giorno,codice del programma e data
     * @param idgiorno Codice del giorno
     * @param idprogramma Codice del programma
     * @param data Data del giorno
     * @return Variabile di tipo GiornoAllenProgObject recuperata
     */

    public GiornoAllenProgObject getGiornoProgrammatoComb(int idgiorno, int idprogramma, LocalDate data){
        dinamico.select();
        dinamico.where("id_programma='" + idprogramma + "' and data='" + data+"'");
        ResultSet rs = dinamico.fetch();
        try {
            if (rs.isBeforeFirst()) {
                rs.next();
                SedutaDAO sedutaDAO = new SedutaDAO();
                SedutaObject seduta = sedutaDAO.getSedutaById(rs.getInt(4));
                GiornoAllenDinamicoObject giornodinamico = new GiornoAllenDinamicoObject(idprogramma, data, rs.getInt("calorie_da_consumare"), seduta);
                return giornodinamico;
            } else {
                return getGiornoProgrammatoMan(idgiorno);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
            return null;
        }
    }

    /**
     * Metodo che recupera un giorno d'allenamento della tabella "giorno_allen_prog" del database in base al codice del giorno
     * @param idgiorno Codice del giorno
     * @return Variabile di tipo GiornoAllenProgObject recuperata
     */

    public GiornoAllenProgObject getGiornoProgrammatoMan(int idgiorno){
        programmato.select();
        programmato.where("id_giorno='" + idgiorno + "'");
        ResultSet rs = programmato.fetch();
        try{
            rs.next();
            SedutaDAO sedutaDAO = new SedutaDAO();
            SedutaObject seduta = sedutaDAO.getSedutaById(rs.getInt(3));
            GiornoAllenProgObject giornoprog = new GiornoAllenProgObject(seduta);
            giornoprog.setId_giorno(idgiorno);
            giornoprog.setCalorie(rs.getInt("calorie_da_consumare"));
            return giornoprog;
        }
        catch (Exception e){
            System.out.println("C'è un errore" +e );
            return null;
        }
    }

    /**
     * Metodo che modifica il codice della seduta di un giorno alimentare dinamico della tabella "giorno_allen_dinamico" del database
     * @param idsedutanuova Nuovo valore di idseduta
     * @param idsedutavecchia Vecchio valore di idseduta
     */

    public void updateGiornoAllenDinamico(int idsedutanuova,int idsedutavecchia){
        String dati = "seduta=" + idsedutanuova;
        dinamico.update(dati);
        dinamico.where("seduta=" + idsedutavecchia);
        dinamico.execute();
    }

    /**
     * Metodo che modifica il valore della calorie da consumare di un giorno d'allenamento dinamico della tabella "giorno_allen_dinamico" del database
     * @param idprogramma Codice del programma
     * @param data Data del giorno
     * @param calorie Nuovo valore di calorie da consumare
     */

    public void updateCalorieGiornoAllenDinamico(int idprogramma, LocalDate data, int calorie){
        String dati = "calorie_da_consumare=" + calorie;
        dinamico.update(dati);
        dinamico.where("id_programma=" + idprogramma + " and data='" + data + "'");
        dinamico.execute();
    }
}
