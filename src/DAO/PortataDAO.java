package DAO;

import DAO.Dbtable.Portata;
import Object.Enum.PortataEnum;
import Object.PortataObject;
import Object.CiboObject;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 19/01/17.
 */
public class PortataDAO {

    protected Portata tabella;

    public PortataDAO() {
        tabella = new Portata();
    }

    public ArrayList<PortataObject> getPortateById(int idpasto){
        tabella.select();
        tabella.where("id_pasto ='" + idpasto + "'");
        tabella.order("tipo");
        ResultSet rs = tabella.fetch();
        ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
        try{
            while(rs.next()){
                CiboDAO ciboDAO = new CiboDAO();
                CiboObject cibo = ciboDAO.getCiboByName(rs.getString("cibo"));
                PortataObject portata = new PortataObject(cibo);
                portata.setId_pasto(rs.getInt("id_pasto"));
                portata.setTipo(PortataEnum.valueOf(rs.getString("tipo")));
                portata.setQuantita(rs.getInt("quantita"));
                portate.add(portata);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return portate;
    }

    public void inserisciPortata (PortataObject portata) {
        String dati= ""+portata.getId_pasto()+"";
        dati=dati+",'"+String.valueOf(portata.getCibo().getNome())+"'";
        dati=dati+",'"+String.valueOf(portata.getTipo())+"'";
        dati=dati+", "+String.valueOf(portata.getQuantita())+"";
        tabella.insert(dati);
        tabella.execute();
    }

    public  void updatePortata (int id_pasto, String cibo, int quantita) {
        String dati= "quantita = "+quantita;
        tabella.update(dati);
        tabella.where("id_pasto='" + id_pasto + "' AND cibo='" + cibo + "'");
        tabella.execute();
    }

    public  void updatePortata (int id_pasto, String vecchiocibo, String nuovocibo, int quantita) {
        String dati= "quantita = " + quantita + " , cibo = '" + nuovocibo + "'";
        tabella.update(dati);
        tabella.where("id_pasto='" + id_pasto + "' AND cibo='" + vecchiocibo + "'");
        tabella.execute();
    }

    public void eliminaPortata (int id_pasto, String cibo) {
        tabella.delete();
        tabella.where("id_pasto='" + id_pasto + "' AND cibo='" + cibo + "'");
        tabella.execute();
    }
}