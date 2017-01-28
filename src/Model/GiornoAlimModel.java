package Model;

import Model.Dbtable.Giorno_alim_eff;

import java.util.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Model.Dbtable.Giorno_alim_prog;
import Object.GiornoAlimEffettivoObject;
import Object.GiornoAlimProgObject;
import Object.PastoObject;

/**
 * Created by ALLDE on 19/01/2017.
 */
public class GiornoAlimModel {
    protected Giorno_alim_eff effettivo;
    protected Giorno_alim_prog programmato;


    public GiornoAlimModel() {
        effettivo = new Giorno_alim_eff();
        programmato = new Giorno_alim_prog();
    }

    public GiornoAlimEffettivoObject getGiornoAlimEffettivo(String username,LocalDate data){
        effettivo.select();
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = effettivo.fetch();
        GiornoAlimEffettivoObject giorno = new GiornoAlimEffettivoObject(username, data);
        try{
            if(rs.isBeforeFirst()){
                rs.next();
                giorno.setCal_assunte(rs.getInt("cal_assunte"));
                PastoModel pastomodel = new PastoModel();
                PastoObject colazione = pastomodel.getPastoById(rs.getInt("colazione"));
                giorno.setPasti(0,colazione);
                PastoObject pranzo = pastomodel.getPastoById(rs.getInt("pranzo"));
                giorno.setPasti(1,pranzo);
                PastoObject cena = pastomodel.getPastoById(rs.getInt("cena"));
                giorno.setPasti(2,cena);
                PastoObject spuntino = pastomodel.getPastoById(rs.getInt("spuntino"));
                giorno.setPasti(3,spuntino);
            } else {
                inserisciGiornoAlimEff(giorno);
            }
        } catch (Exception e){
            System.out.println("Errore: " + e);
        }
        return giorno;
    }

    public <V> void updateGiornoAlimEff(String username, LocalDate data, Map<String,V> map) {
        String dati = "";
        Iterator<Map.Entry<String,V>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
           dati += entry.getKey()+ "='" + entry.getValue() + "'";
        }
        effettivo.update(dati);
        effettivo.where("username='" + username + "' AND data='" + data + "'");
        effettivo.execute();
    }

    public void inserisciGiornoAlimEff(GiornoAlimEffettivoObject giornoeff){
        String dati= "'" + giornoeff.getUsername()+"'";
        dati = dati +  ", '" + String.valueOf(giornoeff.getData() +"'");
        dati = dati +  ", " + String.valueOf(giornoeff.getCal_assunte());
        dati = dati + ", " + giornoeff.getPasti(0).getId();
        dati = dati + ", " + giornoeff.getPasti(3).getId();
        dati = dati + ", " + giornoeff.getPasti(1).getId();
        dati = dati + ", " + giornoeff.getPasti(2).getId();
        effettivo.insert(dati);
        effettivo.execute();
    }

    public void inserisciGiornoAlimProg(GiornoAlimProgObject giornoprog){
        String dati= "'" + giornoprog.getId_giorno()+"'";
        dati = dati +  ", '" + giornoprog.getFabbisogno() +"'";
        dati = dati + ", " + giornoprog.getPasti(0).getId();
        dati = dati + ", " + giornoprog.getPasti(3).getId();
        dati = dati + ", " + giornoprog.getPasti(1).getId();
        dati = dati + ", " + giornoprog.getPasti(2).getId();
        programmato.insert(dati);
        programmato.execute();
    }

    /*public int findPastoInserito(String pasto, LocalDate data, String username){
        effettivo.select(pasto);
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet pastoeff = effettivo.fetch();
        int idpasto = 0;
        try{
            while(pastoeff.next()) {
                idpasto = pastoeff.getInt(pasto);
            }
        }
        catch (Exception e)
        {
            System.out.println("Errore "+e);
        }
        return idpasto;
    }*/
}
