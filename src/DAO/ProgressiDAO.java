package DAO;

import DAO.Dbtable.Progressi;
import java.sql.ResultSet;
import java.time.LocalDate;
import Object.ProgressiObject;

/**
 * La classe ProgressiDAO contiene i metodi per la gestione dei dati della tabella "progressi" del database.
 */

public class ProgressiDAO {
    protected Progressi tabella;

    public ProgressiDAO() {
        tabella = new Progressi();
    }

    /**
     * Metodo che inserisce un nuovo progresso in base a username e data,se non ne è già presente uno
     * @param username Username del progresso
     * @param data Data del progresso
     * @param peso Peso da inserire nel progresso
     * @param fabbisogno Fabbisogno da inserire nel progresso
     * @param caloriedaconsumare calorie da consumare da inserire nel progresso
     */

    public void controllaProgresso(String username, LocalDate data,double peso,int fabbisogno, int caloriedaconsumare){
        tabella.select();
        tabella.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = tabella.fetch();
        try{
            if(!rs.isBeforeFirst()){
                String dati = "'"+username+"'";
                dati +=  ",'"+String.valueOf(data)+"'";
                dati += ","+peso+"";
                dati += ",0";
                dati += ",0";
                dati += ","+fabbisogno;
                dati += ","+caloriedaconsumare;
                tabella.insert(dati);
                tabella.execute();
            }
        } catch(Exception e){
            System.out.println("C'è un errore"+e);
        }
    }

    /**
     * Metodo che modifica il valore di campo del progresso
     * @param username Username del progresso da modificare
     * @param data Data del progresso da modificare
     * @param campo Campo del progresso da modificare
     * @param nuovovalore Nuovo valore del campo del progresso da modificare
     */

    public void updateInfoProgressi(String username,LocalDate data,String campo,String nuovovalore){
        String dati = campo + "=" + nuovovalore;
        tabella.update(dati);
        tabella.where("username='" + username + "' and data='" + data+"'");
        tabella.execute();
    }

    /**
     * Metodo che recupera i valori dei progressi in base all'username
     * @param username Username del progresso che si vuole recuperare
     * @return Una variabile di tipo ProgressiObject i cui attributi sono settati in base ai dati del progresso recuperato
     */

    public ProgressiObject getValoreProgressi(String username){
        tabella.select();
        tabella.where("username='" +username+"'");
        tabella.order("data");
        ResultSet rs = tabella.fetch();
        ProgressiObject progressi = new ProgressiObject(username);
        try{
            while(rs.next()){
                progressi.setDate(rs.getDate("data").toLocalDate());
                progressi.setPesi(rs.getDouble("peso"));
                progressi.setFabbisogno(rs.getDouble("fabbisogno"));
                progressi.setCalorie_assunte(rs.getDouble("calorie_assunte"));
                progressi.setCalorie_da_consumare(rs.getDouble("calorie_da_consumare"));
                progressi.setCalorie_consumate(rs.getDouble("calorie_consumate"));
            }
        }
        catch (Exception e){
            System.out.println("C'è un errore"+e);
        }
        return progressi;
    }
}
