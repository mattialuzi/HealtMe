package DAO;

import DAO.Dbtable.Attivita;
import Object.AttivitaObject;
import Object.EsercizioObject;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 07/02/17.
 */
public class AttivitaDAO {

    protected Attivita tabella;

    public AttivitaDAO() {
        tabella = new Attivita();
    }

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

    public void inserisciAttivita (AttivitaObject attivita) {
        String dati= ""+attivita.getId_seduta()+"";
        dati=dati+",'"+String.valueOf(attivita.getEsercizio().getTipologia())+"'";
        dati=dati+", "+String.valueOf(attivita.getQuantita())+"";
        tabella.insert(dati);
        tabella.execute();
    }

    public void eliminaAttivita (int id_seduta, String esercizio) {
        tabella.delete();
        tabella.where("id_seduta='" + id_seduta + "' AND esercizio='" + esercizio + "'");
        tabella.execute();
    }

    public void updateAttivita (int id_seduta, String esercizio, double quantita ) {
        String dati = "quantita= "+quantita;
        tabella.update(dati);
        tabella.where("id_seduta='"+id_seduta+"' and esercizio='"+esercizio+"'");
        tabella.execute();
    }
}