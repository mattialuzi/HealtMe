package Model;

import Model.Dbtable.Giorno_allen_dinamico;
import Model.Dbtable.Giorno_allen_eff;
import Model.Dbtable.Giorno_allen_prog;
import Object.GiornoAllenEffettivoObject;
import Object.GiornoAllenProgObject;
import Object.GiornoAllenDinamicoObject;
import Object.SedutaObject;

import java.sql.ResultSet;
import java.time.LocalDate;
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

    public void inserisciGiornoAllenProg(GiornoAllenProgObject giornoprog){
        String dati = "'" + giornoprog.getId_giorno()+"'";
        dati = dati + ", '" + giornoprog.getCalorie() + "'";
        dati = dati + ", " + giornoprog.getSeduta().getId();
        programmato.insert(dati);
        int idgiorno = programmato.executeForKey();
        giornoprog.setId_giorno(idgiorno);
    }

    public void inserisciGiornoAllenDinamico(GiornoAllenDinamicoObject giornodinamico){
        String dati = "'" + giornodinamico.getId_programma()+"'" ;
        dati = dati + ", '" + giornodinamico.getData() + "'";
        dati = dati + ", '" + giornodinamico.getCalorie() + "'";
        dati = dati + ", " + giornodinamico.getSeduta().getId();
        dinamico.insert(dati);
        dinamico.execute();
    }

    public GiornoAllenProgObject getGiornoProgrammatoComb(int idgiorno, int idprogramma, LocalDate data){
        dinamico.select();
        dinamico.where("id_programma='" + idprogramma + "' and data='" + data+"'");
        ResultSet rs = dinamico.fetch();
        try {
            if (rs.isBeforeFirst()) {
                rs.next();
                SedutaModel sedutamodel = new SedutaModel();
                SedutaObject seduta = sedutamodel.getSedutaById(rs.getInt(4));
                GiornoAllenDinamicoObject giornodinamico = new GiornoAllenDinamicoObject(idprogramma, data, rs.getInt("calorie_da_consumare"), seduta);
                return giornodinamico;
            } else {
                return getGiornoProgrammatoMan(idgiorno);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
            return null;
        }
    }

    public GiornoAllenProgObject getGiornoProgrammatoMan(int idgiorno){
        programmato.select();
        programmato.where("id_giorno='" + idgiorno + "'");
        ResultSet rs = programmato.fetch();
        try{
            rs.next();
            SedutaModel sedutamodel = new SedutaModel();
            SedutaObject seduta = sedutamodel.getSedutaById(rs.getInt(3));
            GiornoAllenProgObject giornoprog = new GiornoAllenProgObject(seduta);
            giornoprog.setId_giorno(idgiorno);
            giornoprog.setCalorie(rs.getInt("calorie_da_consumare"));
            return giornoprog;
        }
        catch (Exception e){
            System.out.println("C'è un errore" +e );
            return null;
        }
    }

    public void updateGiornoAllenDinamico(int idsedutanuova,int idsedutavecchia){
        String dati = "seduta=" + idsedutanuova;
        dinamico.update(dati);
        dinamico.where("seduta=" + idsedutavecchia);
        dinamico.execute();
    }

    public void updateQuantitaGiornoAllenDinamico(int idprogramma, LocalDate data, int quantita){
        String dati = "quantita=" + quantita;
        dinamico.update(dati);
        dinamico.where("idprogramma=" + idprogramma + " and data='" + data + "'");
        dinamico.execute();
    }
}
