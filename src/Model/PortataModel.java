package Model;

import Model.Dbtable.Portata;
import Object.Enum.PortataEnum;
import Object.PortataObject;
import Object.CiboObject;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 19/01/17.
 */
public class PortataModel {

    protected Portata tabella;

    public PortataModel() {
        tabella = new Portata();
    }

    public ArrayList<PortataObject> getPortateById(int idpasto){
        tabella.select();
        tabella.where("id_pasto ='" + idpasto + "'");
        ResultSet rs = tabella.fetch();
        ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
        try{
            while(rs.next()){
                PortataObject portata = new PortataObject();
                portata.setId_pasto(rs.getInt("id_pasto"));
                CiboModel cibomodel = new CiboModel();
                CiboObject cibo = cibomodel.getCiboByName(rs.getString("nome"));
                portata.setCibo(cibo);
                portata.setTipo(PortataEnum.valueOf(rs.getString("tipo")));
                portata.setQuantita(rs.getInt("quantita"));
                portate.add(portata);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return portate;
    }
}
