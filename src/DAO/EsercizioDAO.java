package DAO;

import DAO.Dbtable.Esercizio;
import Object.Enum.CategoriaEnum;
import Object.Enum.IntensitaEnum;
import Object.Enum.UnitaMisuraEnum;
import Object.EsercizioObject;
import java.sql.ResultSet;

/**
 * La classe EsercizioDAO contiene i metodi per la gestione dei dati della tabella "esercizio" del database.
 */

public class EsercizioDAO {
    protected Esercizio tabella;

    public EsercizioDAO() {
        tabella = new Esercizio();
    }

    /**
     * Metodo che recupera un esercizio in base al suo nome e ne restituisce un EsercizioObject
     * @param tipologia Nome dell'esercizio da recuperare
     * @return Variabile di tipo EsercizioObject i cui attributi sono settati in base ai dati dell'esercizio recuperato
     */

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

    /**
     * Metodo che verifica se un esercizio è presente in base al suo nome
     * @param nome Nome dell'esercizio di cui si vuole verificare la presenza nel database
     * @return True se l'esercizio è presente, falso se non presente
     */

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

    /**
     * Metodo che inserisce un esercizio a partire da un EsercizioObject
     * @param esercizio Variabile di tipo EsercizioObject il cui valore degli attributi costituiscono i dati da inserire
     */

    public void inserisciEsercizio(EsercizioObject esercizio){
        String dati= "'"+esercizio.getTipologia()+"'";
        dati=dati+",'"+String.valueOf(esercizio.getCategoria())+"'";
        dati=dati+",'"+String.valueOf(esercizio.getIntensita())+"'";
        dati=dati+",'"+String.valueOf(esercizio.getUnita_misura())+"'";
        dati=dati+","+esercizio.getConsumo_calorico()+"";
        tabella.insert(dati);
        tabella.execute();
    }

    /**
     * Metodo che recupera un esercizio in base all'intensità
     * @param intensita Nome dell'intensità
     * @return Variabile di tipo ResultSet che contiene tutti gli esercizi recuperati
     */

    public ResultSet getEserciziByIntensita(String intensita){
        tabella.select();
        tabella.where("intensita='" + intensita + "'");
        return tabella.fetch();

    }
}
