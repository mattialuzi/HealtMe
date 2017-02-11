package DAO;

import DAO.Dbtable.Pasto;
import Object.PortataObject;
import Object.Enum.PastoEnum;
import Object.PastoObject;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 19/01/17.
 */
public class PastoDAO {

    protected Pasto tabella;

    public PastoDAO() {
        tabella = new Pasto();
    }

    public void inserisciPasto(PastoObject nuovopasto){
        String dati= String.valueOf(nuovopasto.getId());
        dati = dati +  ", '" + String.valueOf(nuovopasto.getTipo()) +"'";
        tabella.insert(dati);
        int nuovoid = tabella.executeForKey();
        nuovopasto.setId(nuovoid);
    }

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
