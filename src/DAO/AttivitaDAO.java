package DAO;

import DAO.Dbtable.Attivita;
import Object.AttivitaObject;
import Object.EsercizioObject;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * La classe AttivitaDAO contiene i metodi per la gestione dei dati della tabella "attivita" del database.
 */

public class AttivitaDAO {

    protected Attivita tabella;

    public AttivitaDAO() {
        tabella = new Attivita();
    }

    /**
     * Metodo che recupera tutte le attivita di una seduta di allenamento in base al codice
     * @param idseduta Codice della seduta
     * @return ArrayList di AttivitaObject
     */

    public ArrayList<AttivitaObject> getAttivitaById(int idseduta){
        tabella.select();
        tabella.where("id_seduta ='" + idseduta + "'");
        ResultSet rs = tabella.fetch();
        ArrayList<AttivitaObject> listaattivita = new ArrayList<AttivitaObject>();
        try{
            while(rs.next()){
                EsercizioDAO esercizioDAO = new EsercizioDAO();
                EsercizioObject esercizio = esercizioDAO.getEsercizioByTipologia(rs.getString("esercizio"));
                AttivitaObject attivita = new AttivitaObject(esercizio);
                attivita.setId_seduta(rs.getInt("id_seduta"));
                attivita.setQuantita(rs.getInt("quantita"));
                listaattivita.add(attivita);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return listaattivita;
    }

    /**
     * Metodo che inserisce una nuova attività a partire da una AttivitaObject
     * @param attivita variabile di tipo AttivitaObject i cui attributi costituiscono i dati per inserire una nuova attivita nel database
     */

    public void inserisciAttivita (AttivitaObject attivita) {
        String dati= ""+attivita.getId_seduta()+"";
        dati=dati+",'"+String.valueOf(attivita.getEsercizio().getTipologia())+"'";
        dati=dati+", "+String.valueOf(attivita.getQuantita())+"";
        tabella.insert(dati);
        tabella.execute();
    }

    /**
     * Metodo che elimina una attività in base alla chiave primaria della tabella del database
     * @param id_seduta Codice della seduta
     * @param esercizio Nome dell'esercizio
     */

    public void eliminaAttivita (int id_seduta, String esercizio) {
        tabella.delete();
        tabella.where("id_seduta='" + id_seduta + "' AND esercizio='" + esercizio + "'");
        tabella.execute();
    }

    /**
     * Metodo che modifica la quantità di una attività
     * @param id_seduta Codice della seduta
     * @param esercizio Nome dell'esercizio
     * @param quantita  Nuova quantita dell'esercizio
     */

    public void updateAttivita (int id_seduta, String esercizio, double quantita ) {
        String dati = "quantita= "+quantita;
        tabella.update(dati);
        tabella.where("id_seduta='"+id_seduta+"' and esercizio='"+esercizio+"'");
        tabella.execute();
    }
}
