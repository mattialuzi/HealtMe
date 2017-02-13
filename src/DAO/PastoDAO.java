package DAO;

import DAO.Dbtable.Pasto;
import Object.PortataObject;
import Object.Enum.PastoEnum;
import Object.PastoObject;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * La classe PastoDAO contiene i metodi per la gestione dei dati della tabella "pasto" del database.
 */

public class PastoDAO {

    protected Pasto tabella;

    public PastoDAO() {
        tabella = new Pasto();
    }

    /**
     * Metodo che inserisce un nuovo pasto a partire da un PastoObject
     * @param nuovopasto Variabile di tipo PastoObject il cui valore degli attributi costituiscono i dati da inserire
     */

    public void inserisciPasto(PastoObject nuovopasto){
        String dati= String.valueOf(nuovopasto.getId());
        dati = dati +  ", '" + String.valueOf(nuovopasto.getTipo()) +"'";
        tabella.insert(dati);
        int nuovoid = tabella.executeForKey();
        nuovopasto.setId(nuovoid);
    }

    /**
     * Metodo che recupere un pasto in base al codice
     * @param idpasto Codice del pasto
     * @return Variabile di tipo PastoObject i cui attributi sono settati in base ai dati del pasto recuperato
     */

    public PastoObject getPastoById(int idpasto) {
        tabella.select();
        tabella.where("id ='" + idpasto + "'");
        ResultSet rs = tabella.fetch();
        PastoObject pasto = new PastoObject();
        try {
            if(rs.isBeforeFirst()) {
                rs.next();
                pasto.setId(rs.getInt("id"));
                pasto.setTipo(PastoEnum.valueOf(rs.getString("tipo")));
                PortataDAO portataDAO = new PortataDAO();
                ArrayList<PortataObject> portate = portataDAO.getPortateById(rs.getInt("id"));
                pasto.setPortate(portate);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return pasto;
    }
}
