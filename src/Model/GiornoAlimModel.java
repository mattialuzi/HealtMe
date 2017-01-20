package Model;

import Model.Dbtable.Giorno_alim_eff;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import Object.GiornoAlimEffettivoObject;
import Object.PastoObject;

/**
 * Created by ALLDE on 19/01/2017.
 */
public class GiornoAlimModel {
    protected Giorno_alim_eff effettivo;


    public GiornoAlimModel() { effettivo = new Giorno_alim_eff(); }

    public GiornoAlimEffettivoObject getGiornoAlimEffettivo(String username,LocalDate data){
        effettivo.select();
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = effettivo.fetch();
        GiornoAlimEffettivoObject giorno = new GiornoAlimEffettivoObject(username, Date.valueOf(data));
        try{
            if(rs.isBeforeFirst()){
                rs.next();
                giorno.setCal_assunte(rs.getInt("cal_assunte"));
                PastoModel pastomodel = new PastoModel();
                PastoObject colazione = pastomodel.getPastoById(rs.getInt("colazione"));
                giorno.setColazione(colazione);
                PastoObject spuntino = pastomodel.getPastoById(rs.getInt("spuntino"));
                giorno.setSpuntino(spuntino);
                PastoObject pranzo = pastomodel.getPastoById(rs.getInt("pranzo"));
                giorno.setPranzo(pranzo);
                PastoObject cena = pastomodel.getPastoById(rs.getInt("cena"));
                giorno.setCena(cena);
            } else {
                inserisciGiornoAlimEff(giorno);
            }
        } catch (Exception e){
            System.out.println("Errore: " + e);
        }
        return giorno;
    }

   //update id pasto

    public void inserisciGiornoAlimEff(GiornoAlimEffettivoObject giornoeff){
        String dati= "'" + giornoeff.getUsername()+"'";
        dati = dati +  ", '" + String.valueOf(giornoeff.getData() +"'");
        dati = dati +  ", " + String.valueOf(giornoeff.getCal_assunte());
        dati = dati + ", " + giornoeff.getColazione().getId();
        dati = dati + ", " + giornoeff.getSpuntino().getId();
        dati = dati + ", " + giornoeff.getPranzo().getId();
        dati = dati + ", " + giornoeff.getCena().getId();
        effettivo.insert(dati);
        effettivo.execute();
    }

    public int findPastoInserito(String pasto, LocalDate data, String username){
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
    }
}
