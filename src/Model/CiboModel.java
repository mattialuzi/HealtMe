package Model;

import Model.Dbtable.Cibo;
import Object.CiboObject;

import java.sql.ResultSet;

/**
 * Created by lorenzobraconi on 12/01/17.
 */
public class CiboModel {

    protected Cibo tabella;

    public CiboModel() {
        tabella = new Cibo();
    }

    public boolean findCiboByName(String nome){
        boolean success=false;
        tabella.select();
        tabella.where("nome='" + nome + "'");
        int n = tabella.count(tabella.fetch());
        if (n==1)
            success=true;
        else
            success=false;
        return success;
    }

    public void inserisciCibo(CiboObject cibo){
        String dati= "'"+cibo.getNome()+"'";
        dati=dati+",'"+String.valueOf(cibo.getGruppo())+"'";
        dati=dati+","+cibo.getKilocal()+"";
        dati=dati+",'"+String.valueOf(cibo.getAllergia())+"'";
        dati=dati+",'"+String.valueOf(cibo.getPortata())+"'";
        dati=dati+",'"+String.valueOf(cibo.getCompatibilita())+"'";
        tabella.insert(dati);
        tabella.execute();
    }

    public ResultSet getCibiByPortata(String portata){
        tabella.select("nome");
        tabella.where("portata='" + portata + "'");
        ResultSet cibo = tabella.fetch();
        return cibo;
    }
}
