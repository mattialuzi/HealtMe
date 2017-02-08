package Model;

import Model.Dbtable.Giorno_allen_dinamico;
import Model.Dbtable.Giorno_allen_eff;
import Model.Dbtable.Giorno_allen_prog;
import Object.Enum.StatusEnum;
import Object.GiornoAllenEffettivoObject;
import Object.SedutaObject;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lorenzobraconi on 07/02/17.
 */
public class GiornoAllenModel {
    protected Giorno_allen_eff effettivo;
    protected Giorno_allen_prog programmato;
    protected Giorno_allen_dinamico dinamico;


    public GiornoAllenModel() {
        effettivo = new Giorno_allen_eff();
        programmato = new Giorno_allen_prog();
        dinamico = new Giorno_allen_dinamico();
    }

    public GiornoAllenEffettivoObject getGiornoAllenEffettivo(String username, LocalDate data){
        effettivo.select();
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = effettivo.fetch();
        try{
            if(rs.isBeforeFirst()){
                rs.next();
                SedutaModel sedutamodel = new SedutaModel();
                SedutaObject seduta = sedutamodel.getSedutaById(rs.getInt(4));
                GiornoAllenEffettivoObject giorno = new GiornoAllenEffettivoObject(username, data, seduta);
                giorno.setCalorie(rs.getInt("cal_consumate"));
                return giorno;
            } else {
                GiornoAllenEffettivoObject nuovogiorno = new GiornoAllenEffettivoObject(username, data);
                inserisciGiornoAllenEff(nuovogiorno);
                return nuovogiorno;
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
            return null;
        }
    }

    public <V> void updateGiornoAllenEff(String username, LocalDate data, Map<String,V> map) {
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

    public void inserisciGiornoAllenEff(GiornoAllenEffettivoObject giornoeff){
        String dati= "'" + giornoeff.getUsername()+"'";
        dati = dati +  ", '" + String.valueOf(giornoeff.getData() +"'");
        dati = dati +  ", " + String.valueOf(giornoeff.getCalorie());
        dati = dati + ", " + giornoeff.getSeduta().getId();
        effettivo.insert(dati);
        effettivo.execute();
    }

}