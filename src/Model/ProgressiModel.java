package Model;

import Model.Dbtable.Progressi;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
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
    public void controllaProgresso(String username, LocalDate data,float peso,int fabbisogno){
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

    public HashMap<LocalDate,Float> getValoreProgressi(String username,String campo){
        tabella.select();
        tabella.where("username='" +username+"'");
        ResultSet rs = tabella.fetch();
        HashMap<LocalDate,Float> mappa = new HashMap<LocalDate, Float>();
        try{
            while(rs.next()){
                mappa.put(rs.getDate("data").toLocalDate(),rs.getFloat(campo));
            }
        }
        catch (Exception e){
            System.out.println("C'è un errore"+e);
        }
        return mappa;
    }
}
