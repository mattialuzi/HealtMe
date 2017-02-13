package DAO;

import DAO.Dbtable.Giorno_alim_dinamico;
import DAO.Dbtable.Giorno_alim_eff;
import java.util.*;
import java.sql.ResultSet;
import java.time.LocalDate;
import DAO.Dbtable.Giorno_alim_prog;
import Object.Enum.PastoEnum;
import Object.Enum.StatusEnum;
import Object.GiornoAlimEffettivoObject;
import Object.GiornoAlimProgObject;
import Object.GiornoAlimDinamicoObject;
import Object.PastoObject;

/**
 * La classe GiornoAlimDAO contiene i metodi per la gestione dei dati delle tabella "giorno_alim_effettivo","giorno_alim_prog","giorno_alim_dinamico" del database
 */

public class GiornoAlimDAO {
    protected Giorno_alim_eff effettivo;
    protected Giorno_alim_prog programmato;
    protected Giorno_alim_dinamico dinamico;

    public GiornoAlimDAO() {
        effettivo = new Giorno_alim_eff();
        programmato = new Giorno_alim_prog();
        dinamico = new Giorno_alim_dinamico();
    }

    /**
     * Metodo che recupera un giorno alimentare effettivo dalla tabella "giorno_alim_effettivo" del database in base a username e data
     * @param username Nome dell'username
     * @param data Data del giorno
     * @return Una variabile di tipo GiornoAlimEffettivoObject costruito in base alle informazioni del giorno recuperato
     */

    public GiornoAlimEffettivoObject getGiornoAlimEffettivo(String username,LocalDate data){
        effettivo.select();
        effettivo.where("username='" + username + "' and data='" + data+"'");
        ResultSet rs = effettivo.fetch();
        ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
        try{
            if(rs.isBeforeFirst()){
                rs.next();
                PastoDAO pastoDAO = new PastoDAO();
                for(int i =4; i<8; i++){
                    PastoObject pasto = pastoDAO.getPastoById(rs.getInt(i));
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

    /**
     * Metodo che modifica un giorno alimentare effettivo della tabella "giorno_alim_effettivo" del database
     * @param username Nome dell'username
     * @param data Data del giorno
     * @param map Mappa la cui chiave è di tipo String ed il valore è un tipo generico
     * @param <V> Tipo generico
     */

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

    /**
     * Metodo che inserisce un giorno alimentare effettivo nella tabella "giorno_alim_effettivo" del database in base ad un GiornoAlimEffettivoObject
     * @param giornoeff Variabile di tipo GiornoAlimEffettivoObject il cui valore degli attributi costituiscono le informazioni da inserire
     */

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

    /**
     * Metodo che inserisce un giorno alimentare programmato nella tabella "giorno_alim_prog" del database in base ad un GiornoAlimProgObject
     * @param giornoprog Variabile di tipo GiornoAlimProgObject il cui valore degli attributi costituiscono le informazioni da inserire
     */

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

    /**
     * Metodo che inserisce un giorno alimentare dinamico nella tabella "giorno_alim_dinamico" del database in base ad un GiornoAlimDinamicoObject
     * @param giornodinamico Variabile di tipo GiornoAlimDinamicoObject il cui valore degli attributi costituiscono le informazioni da inserire
     */

    public void inserisciGiornoAlimDinamico(GiornoAlimDinamicoObject giornodinamico){
        String dati= "'" + giornodinamico.getId_programma()+"'";
        dati = dati +  ", '" + giornodinamico.getData() +"'";
        dati = dati +  ", '" + giornodinamico.getCalorie() +"'";
        dati = dati + ", " + giornodinamico.getPasti(0).getId();
        dati = dati + ", " + giornodinamico.getPasti(1).getId();
        dati = dati + ", " + giornodinamico.getPasti(2).getId();
        dati = dati + ", " + giornodinamico.getPasti(3).getId();
        dinamico.insert(dati);
        dinamico.execute();
    }

    /**
     * Metodo che recupera un giorno alimentare della tabella "giorno_alim_dinamico" del database in base al codice del giorno,codice del programma e data
     * @param idgiorno Codice del giorno
     * @param idprogramma Codice del programma
     * @param data Data del giorno
     * @return Variabile di tipo GiornoAlimProgObject recuperata
     */

    public GiornoAlimProgObject getGiornoProgrammatoComb(int idgiorno, int idprogramma, LocalDate data){
        dinamico.select();
        dinamico.where("id_programma='" + idprogramma + "' and data='" + data+"'");
        ResultSet rs = dinamico.fetch();
        ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
        try {
            if (rs.isBeforeFirst()) {
                rs.next();
                PastoDAO pastoDAO = new PastoDAO();
                for(int i=4;i<8;i++){
                    PastoObject pasto = pastoDAO.getPastoById(rs.getInt(i));
                    pasti.add(pasto);
                }
                GiornoAlimDinamicoObject giornodinamico = new GiornoAlimDinamicoObject(idprogramma, data, rs.getInt("fabbisogno"), pasti);
                return giornodinamico;
            } else {
                return getGiornoProgrammatoMan(idgiorno);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
            return null;
        }

    }

    /**
     * Metodo che recupera un giorno alimentare della tabella "giorno_alim_prog" del database in base al codice del giorno
     * @param idgiorno Codice del giorno
     * @return Variabile di tipo GiornoAlimProgObject recuperata
     */

    public GiornoAlimProgObject getGiornoProgrammatoMan(int idgiorno){
        programmato.select();
        programmato.where("id_giorno='" + idgiorno + "'");
        ResultSet rs = programmato.fetch();
        ArrayList<PastoObject> pasti = new ArrayList<PastoObject>();
        try{
            rs.next();
            PastoDAO pastoDAO = new PastoDAO();
            for(int i=3;i<7;i++){
                PastoObject pasto = pastoDAO.getPastoById(rs.getInt(i));
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

    /**
     * Metodo che modifica un giorno alimentare dinamico della tabella "giorno_alim_dinamico" del database
     * @param idpastonuovo Nuovo valore di idpasto
     * @param idpastovecchio Vecchio valore
     * @param tipopasto Tipo del pasto
     */

    public void updateGiornoAlimDinamico(int idpastonuovo,int idpastovecchio, PastoEnum tipopasto){
        String dati = tipopasto + "=" + idpastonuovo;
        dinamico.update(dati);
        dinamico.where(tipopasto + "=" + idpastovecchio);
        dinamico.execute();
    }
}
