package Model;

import Model.Dbtable.Cibo;
import Object.CiboObject;

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
        String dati= cibo.valueString(cibo);
        tabella.insert(dati);
        tabella.execute();
    }
}
