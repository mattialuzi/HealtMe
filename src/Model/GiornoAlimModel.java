package Model;

import Model.Dbtable.Giorno_alim_eff;

import java.util.*;
import java.sql.ResultSet;
import java.time.LocalDate;

import Model.Dbtable.Giorno_alim_prog;
import Object.Enum.StatusEnum;
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
        ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
        try{
            if(rs.isBeforeFirst()){
                rs.next();
                PastoModel pastomodel = new PastoModel();
                for(int i =4; i<8; i++){
                    PastoObject pasto = pastomodel.getPastoById(rs.getInt(i));
                    pasti.add(pasto);
                }
                GiornoAlimEffettivoObject giorno = new GiornoAlimEffettivoObject(username, data, pasti, StatusEnum.valueOf(rs.getString("status")));
                giorno.setCalorie(rs.getInt("cal_assunte"));
                return giorno;
            } else {
                GiornoAlimEffettivoObject nuovogiorno = new GiornoAlimEffettivoObject(username, data, StatusEnum.colazione);
                inserisciGiornoAlimEff(nuovogiorno);
                return nuovogiorno;
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
            return null;
        }
    }

    public <V> void updateGiornoAlimEff(String username, LocalDate data, Map<String,V> map) {
        String dati = "";
        Iterator<Map.Entry<String,V>> iterator = map.entrySet().iterator();
        Map.Entry entry = iterator.next();
        dati += entry.getKey()+ "='" + entry.getValue() + "'";
        while (iterator.hasNext()) {
            entry = iterator.next();
           dati += ","+entry.getKey()+ "='" + entry.getValue() + "'";
        }
        effettivo.update(dati);
        effettivo.where("username='" + username + "' AND data='" + data + "'");
        effettivo.execute();
    }

    public void inserisciGiornoAlimEff(GiornoAlimEffettivoObject giornoeff){
        String dati= "'" + giornoeff.getUsername()+"'";
        dati = dati +  ", '" + String.valueOf(giornoeff.getData() +"'");
        dati = dati +  ", " + String.valueOf(giornoeff.getCalorie());
        dati = dati + ", " + giornoeff.getPasti(0).getId();
        dati = dati + ", " + giornoeff.getPasti(1).getId();
        dati = dati + ", " + giornoeff.getPasti(2).getId();
        dati = dati + ", " + giornoeff.getPasti(3).getId();
        dati = dati + ",'" + giornoeff.getStatus() + "'";
        effettivo.insert(dati);
        effettivo.execute();
    }

    public void inserisciGiornoAlimProg(GiornoAlimProgObject giornoprog){
        String dati= "'" + giornoprog.getId_giorno()+"'";
        dati = dati +  ", '" + giornoprog.getCalorie() +"'";
        dati = dati + ", " + giornoprog.getPasti(0).getId();
        dati = dati + ", " + giornoprog.getPasti(1).getId();
        dati = dati + ", " + giornoprog.getPasti(2).getId();
        dati = dati + ", " + giornoprog.getPasti(3).getId();
        programmato.insert(dati);
        int idgiorno = programmato.executeForKey();
        giornoprog.setId_giorno(idgiorno);

    }

    public GiornoAlimProgObject getGiornoProgrammato(int idgiorno){
        programmato.select();
        programmato.where("id_giorno='" + idgiorno + "'");
        ResultSet rs = programmato.fetch();
        ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
        try{
            rs.next();
            PastoModel pastomodel = new PastoModel();
            for(int i=3;i<7;i++){
                PastoObject pasto = pastomodel.getPastoById(rs.getInt(i));
                pasti.add(pasto);
            }
            GiornoAlimProgObject giornoprog = new GiornoAlimProgObject(pasti);
            giornoprog.setId_giorno(idgiorno);
            giornoprog.setCalorie(rs.getInt("fabbisogno"));
            return giornoprog;
        }
        catch (Exception e){
            System.out.println("C'è un errore" +e );
            return null;
        }
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
