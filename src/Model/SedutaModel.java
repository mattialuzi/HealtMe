package Model;

import Model.Dbtable.Seduta;
import Object.SedutaObject;
import Object.AttivitaObject;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 07/02/17.
 */
public class SedutaModel {

    protected Seduta tabella;

    public SedutaModel() {
        tabella = new Seduta();
    }

    public void inserisciSeduta(SedutaObject nuovaseduta){
        String dati= String.valueOf(nuovaseduta.getId());
        tabella.insert(dati);
        int nuovoid = tabella.executeForKey();
        nuovaseduta.setId(nuovoid);
    }

    public SedutaObject getSedutaById(int idseduta) {
        tabella.select();
        tabella.where("id ='" + idseduta + "'");
        ResultSet rs = tabella.fetch();
        SedutaObject seduta = new SedutaObject();
        try {
            if(rs.isBeforeFirst()) {
                rs.next();
                seduta.setId(rs.getInt("id"));
                AttivitaModel attivitamodel = new AttivitaModel();
                ArrayList<AttivitaObject> attivita = attivitamodel.getAttivitaById(rs.getInt("id"));
                seduta.setAttivita(attivita);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return seduta;
    }
}
