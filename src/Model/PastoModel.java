package Model;

import Model.Dbtable.Pasto;
import Object.PastoObject;

/**
 * Created by lorenzobraconi on 19/01/17.
 */
public class PastoModel {

    protected Pasto tabella;

    public PastoModel() {
        tabella = new Pasto();
    }

    public int inserisciPasto(PastoObject nuovopasto){
        String dati= String.valueOf(nuovopasto.getId());
        dati = dati +  ", '" + String.valueOf(nuovopasto.getTipo()) +"'";
        tabella.insert(dati);
        return tabella.executeForKey();
    }
}
