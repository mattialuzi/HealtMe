package DAO;

import DAO.Dbtable.Cibo;
import Object.CiboObject;
import Object.Enum.*;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * La classe CiboDAO contiene i metodi per la gestione dei dati della tabella "cibo" del database
 */

public class CiboDAO {

    protected Cibo tabella;

    public CiboDAO() {
        tabella = new Cibo();
    }

    /**
     * Metodo che recupera un cibo in base al suo nome e ne restituisce un CiboObject
     * @param nome Nome del cibo che si intende recuperare
     * @return Variabile di tipo CiboObject i cui attributi sono settati in base ai dati del cibo recuperato
     */

    public CiboObject getCiboByName(String nome){
        tabella.select();
        tabella.where("nome ='" + nome + "'");
        ResultSet rs = tabella.fetch();
        CiboObject cibo = new CiboObject();
        try{
            rs.next();
            cibo.setNome(rs.getString("nome"));
            cibo.setCompatibilita(CompatibilitaEnum.valueOf(rs.getString("compatibilita")));
            cibo.setGruppo(GruppoEnum.valueOf(rs.getString("gruppo")));
            cibo.setAllergia(AllergiaEnum.valueOf(rs.getString("allergia")));
            cibo.setPortata(PortataEnum.valueOf(rs.getString("portata")));
            cibo.setKilocal(rs.getInt("kilocal"));
            cibo.setIdoneita(IdoneitaEnum.valueOf(rs.getString("idoneita")));
        } catch(Exception e){
            System.out.println("Errore: "+e);
        }
        return cibo;
    }

    /**
     * Metodo che verifica se un cibo è presente nella tabella "cibo" del database in base al suo nome
     * @param nome Nome del cibo di cui si vuole verificare la presenza nel database
     * @return True se il cibo è presente, falso se non presente
     */

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

    /**
     * Metodo che inserisce un nuovo cibo a partire da un CiboObject
     * @param cibo Variabile di tipo CiboObject il cui valore degli attributi costituiscono i dati da inserire
     */

    public void inserisciCibo(CiboObject cibo){
        String dati= "'"+cibo.getNome()+"'";
        dati=dati+",'"+String.valueOf(cibo.getGruppo())+"'";
        dati=dati+","+cibo.getKilocal()+"";
        dati=dati+",'"+String.valueOf(cibo.getAllergia())+"'";
        dati=dati+",'"+String.valueOf(cibo.getPortata())+"'";
        dati=dati+",'"+String.valueOf(cibo.getCompatibilita())+"'";
        dati=dati+",'"+String.valueOf(cibo.getIdoneita())+"'";
        tabella.insert(dati);
        tabella.execute();
    }

    /**
     * Metodo che recupera tutti i nomi dei cibi in base alla portata
     * @param portata Nome della portata di cui recuperare i cibi
     * @return Variabile di tipo ResultSet che contiene tutti i nomi dei cibi recuperati
     */

    public ResultSet getCibiByPortata(String portata){
        tabella.select("nome");
        tabella.where("portata='" + portata + "'");
        ResultSet cibi = tabella.fetch();
        return cibi;
    }

    /**
     * Metodo che recupera tutti i nomi dei cibi in base ad allergie,tipo di alimentazione,portata e idoneità
     * @param allergia Nome dell'allergia
     * @param tipoalimentazione Tipo di alimentazione
     * @param portata Nome della portata
     * @param idoneita Nomi delle idoneità
     * @return Arraylist di tipo String che contiene tutti i nomi dei cibi recuperati
     */

    public ArrayList<String> getCiboForUser (String allergia,String tipoalimentazione,String portata,String[] idoneita){
        tabella.select("nome");
        String dati = "portata='" + portata;
        if(!allergia.equals("nessuna")){
            dati += "' and allergia != '" + allergia;
        }
        dati += "' and (idoneita = '" + idoneita[0] + "'";
        int i=idoneita.length;
        for (int j=1; j<i; j++) {
            dati += " or idoneita = '" + idoneita[j] + "'";
        }
        dati += ") and ( compatibilita = 'vegana' ";
        if (tipoalimentazione.equals("onnivora")){
            dati += "or compatibilita = 'onnivora' or compatibilita = 'vegetariana'";
        }
        else if (tipoalimentazione.equals("vegetariana")) {
            dati += "or compatibilita = 'vegetariana'";
        }
        dati += ")";
        tabella.where(dati);
        ResultSet rs = tabella.fetch();
        ArrayList<String> cibi = new ArrayList<String>();
        try{
            while(rs.next()){
                cibi.add(rs.getString("nome"));
            }
        } catch(Exception e){
            System.out.println("Errore: "+e);
        }
        return cibi;
    }
}
