package Model;

import Model.Dbtable.Giorno_alim_eff;

import java.sql.ResultSet;
import java.time.LocalDate;
import Object.GiornoAlimEffettivoObject;

/**
 * Created by ALLDE on 19/01/2017.
 */
public class GiornoAlimModel {
    protected Giorno_alim_eff effettivo;


    public GiornoAlimModel() { effettivo = new Giorno_alim_eff(); }

    public boolean findGiornoAlimEffByUser(String username,LocalDate data){
        boolean success=false;
        effettivo.select();
        effettivo.where("username='" + username + "' and data='" + data+"'");
        int n = effettivo.count(effettivo.fetch());
        if (n==1)
            success=true;
        else
            success=false;
        return success;
    }

    public void inserisciGiornoAlimEff(GiornoAlimEffettivoObject giornoeff){
        String dati= giornoeff.valueString(giornoeff);
        effettivo.insert(dati);
        effettivo.execute();
    }

    public int findPastoInserito(String pasto, LocalDate data, String username){
        effettivo.select(pasto);
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet pastoeff = effettivo.fetch();
        int idpasto = new Integer(null) ;
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
