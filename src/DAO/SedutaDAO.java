package DAO;

import DAO.Dbtable.Seduta;
import Object.SedutaObject;
import Object.AttivitaObject;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * La classe SedutaDAO contiene i metodi per la gestione dei dati della tabella "seduta" del database.
 */

public class SedutaDAO {

    protected Seduta tabella;

    public SedutaDAO() {
        tabella = new Seduta();
    }

    /**
     * Metodo che inserisce una nuova seduta a partire da una SedutaObject
     * @param nuovaseduta Variabile di tipo SedutaObject il cui valore degli attributi costituiscono i dati da inserire
     */

    public void inserisciSeduta(SedutaObject nuovaseduta){
        String dati= String.valueOf(nuovaseduta.getId());
        tabella.insert(dati);
        int nuovoid = tabella.executeForKey();
        nuovaseduta.setId(nuovoid);
    }

    /**
     * Metodo che recupera una seduta in base al suo id
     * @param idseduta id della seduta che si vuole recuperare
     * @return Variabile di tipo SedutaObject i cui attributi sono settati in base ai dati dell'esercizio recuperato
     */

    public SedutaObject getSedutaById(int idseduta) {
        tabella.select();
        tabella.where("id ='" + idseduta + "'");
        ResultSet rs = tabella.fetch();
        SedutaObject seduta = new SedutaObject();
        try {
            if(rs.isBeforeFirst()) {
                rs.next();
                seduta.setId(rs.getInt("id"));
                AttivitaDAO attivitaDAO = new AttivitaDAO();
                ArrayList<AttivitaObject> attivita = attivitaDAO.getAttivitaById(rs.getInt("id"));
                seduta.setAttivita(attivita);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return seduta;
    }
}
