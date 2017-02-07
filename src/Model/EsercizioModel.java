package Model;

import Model.Dbtable.Esercizio;
import Object.EsercizioObject;

/**
 * Created by ALLDE on 07/02/2017.
 */
public class EsercizioModel {
    protected Esercizio tabella;

    public EsercizioModel() {
        tabella = new Esercizio();
    }

    public void inserisciEsercizio(EsercizioObject esercizio){
        String dati= "'"+esercizio.getTipologia()+"'";
        dati=dati+",'"+String.valueOf(esercizio.getCategoria())+"'";
        dati=dati+",'"+String.valueOf(esercizio.getIntensita())+"'";
        dati=dati+",'"+String.valueOf(esercizio.getUnita_misura())+"'";
        dati=dati+","+esercizio.getConsumo_calorico()+"";
        tabella.insert(dati);
        tabella.execute();
    }

    public boolean findEsercizioByName(String nome){
        boolean success=false;
        tabella.select();
        tabella.where("tipologia='" + nome + "'");
        int n = tabella.count(tabella.fetch());
        if (n==1)
            success=true;
        else
            success=false;
        return success;
    }
}
