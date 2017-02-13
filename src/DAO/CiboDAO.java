package DAO;

import DAO.Dbtable.Cibo;
import Object.CiboObject;
import Object.Enum.*;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 12/01/17.
 */
public class CiboDAO {

    protected Cibo tabella;

    public CiboDAO() {
        tabella = new Cibo();
    }

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
        dati=dati+",'"+String.valueOf(cibo.getIdoneita())+"'";
        tabella.insert(dati);
        tabella.execute();
    }

    public ResultSet getCibiByPortata(String portata){
        tabella.select("nome");
        tabella.where("portata='" + portata + "'");
        ResultSet cibi = tabella.fetch();
        return cibi;
    }

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