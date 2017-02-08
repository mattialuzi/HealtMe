package Model;

import Model.Dbtable.Progressi;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import Object.ProgressiObject;
import java.util.Map;

/**
 * Created by ALLDE on 06/02/2017.
 */
public class ProgressiModel {
    protected Progressi tabella;

    public ProgressiModel() {
        tabella = new Progressi();
    }

    //aggiungere attributo caloriedaconsumare
    public void controllaProgresso(String username, LocalDate data,double peso,int fabbisogno){
        tabella.select();
        tabella.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = tabella.fetch();
        try{
            if(!rs.isBeforeFirst()){
                String dati = "'"+username+"'";
                dati +=  ",'"+String.valueOf(data)+"'";
                dati += ","+peso+"";
                dati += ",0";
                dati += ",0";
                dati += ","+fabbisogno+"";
                dati += ",0";//sostituire con caloriedaconsumare
                tabella.insert(dati);
                tabella.execute();
            }
        } catch(Exception e){
            System.out.println("C'è un errore"+e);
        }
    }

    public void updateInfoProgressi(String username,LocalDate data,String campo,String nuovovalore){
        String dati = campo + "=" + nuovovalore;
        tabella.update(dati);
        tabella.where("username='" + username + "' and data='" + data+"'");
        tabella.execute();
    }

    public ProgressiObject getValoreProgressi(String username){
        tabella.select();
        tabella.where("username='" +username+"'");
        tabella.order("data");
        ResultSet rs = tabella.fetch();
        ProgressiObject progressi = new ProgressiObject(username);
        try{
            while(rs.next()){
                progressi.setDate(rs.getDate("data").toLocalDate());
                progressi.setPesi(rs.getDouble("peso"));
                progressi.setFabbisogno(rs.getDouble("fabbisogno"));
                progressi.setCalorie_assunte(rs.getDouble("calorie_assunte"));
                progressi.setCalorie_da_consumare(rs.getDouble("calorie_da_consumare"));
                progressi.setCalorie_consumate(rs.getDouble("calorie_consumate"));
            }
        }
        catch (Exception e){
            System.out.println("C'è un errore"+e);
        }
        return progressi;
    }
}
