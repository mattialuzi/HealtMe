package Model;

import Model.Dbtable.Esercizio;
import Object.Enum.CategoriaEnum;
import Object.Enum.IntensitaEnum;
import Object.Enum.UnitaMisuraEnum;
import Object.EsercizioObject;

import java.sql.ResultSet;

/**
 * Created by ALLDE on 07/02/2017.
 */
public class EsercizioModel {
    protected Esercizio tabella;

    public EsercizioModel() {
        tabella = new Esercizio();
    }

    public EsercizioObject getEsercizioByTipologia(String tipologia){
        tabella.select();
        tabella.where("tipologia ='" + tipologia + "'");
        ResultSet rs = tabella.fetch();
        EsercizioObject esercizio = new EsercizioObject();
        try{
            rs.next();
            esercizio.setTipologia(rs.getString("tipologia"));
            esercizio.setCategoria(CategoriaEnum.valueOf(rs.getString("categoria")));
            esercizio.setIntensita(IntensitaEnum.valueOf(rs.getString("intensita")));
            esercizio.setUnita_misura(UnitaMisuraEnum.valueOf(rs.getString("unita_misura")));
            esercizio.setConsumo_calorico(rs.getInt("consumo_calorico"));
        } catch(Exception e){
            System.out.println("Errore: "+e);
        }
        return esercizio;
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

    public void inserisciEsercizio(EsercizioObject esercizio){
        String dati= "'"+esercizio.getTipologia()+"'";
        dati=dati+",'"+String.valueOf(esercizio.getCategoria())+"'";
        dati=dati+",'"+String.valueOf(esercizio.getIntensita())+"'";
        dati=dati+",'"+String.valueOf(esercizio.getUnita_misura())+"'";
        dati=dati+","+esercizio.getConsumo_calorico()+"";
        tabella.insert(dati);
        tabella.execute();
    }

    public ResultSet getEserciziByUnita(String unita){
        tabella.select("tipologia");
        tabella.where("unita_misura='" + unita + "'");
        ResultSet esercizi = tabella.fetch();
        return esercizi;
    }
}
