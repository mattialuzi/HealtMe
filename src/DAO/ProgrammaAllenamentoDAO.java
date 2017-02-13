package DAO;

import Helpers.DbTable;
import Object.ProgAllenManObject;
import Object.ProgrammaAllenamentoObject;
import Object.GiornoAllenProgObject;
import Object.ProgAllenCombObject;
import Object.SedutaObject;
import Object.AttivitaObject;
import DAO.Dbtable.Prog_allen_comb;
import DAO.Dbtable.Prog_allen_manu;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * La classe ProgrammaAllenamentoDAO contiene i metodi per la gestione dei dati delle tabella "prog_allen_man" e "prog_allen_comb" del database
 */

public class ProgrammaAllenamentoDAO {
    protected Prog_allen_manu manuale;
    protected Prog_allen_comb combinato;


    public ProgrammaAllenamentoDAO() {
        manuale = new Prog_allen_manu();
        combinato = new Prog_allen_comb();
    }

    /**
     * Metodo che inserisce un programma d'allenamento manuale nella tabella "prog_allen_man" del database a partire da un ProgAllenManObject
     * @param programma Variabile di tipo ProgAllenManObject
     */

    public void inserisciProgrammaManuale(ProgAllenManObject programma){
        String dati = String.valueOf(programma.getId());
        manuale.insert(aggiungiGiorni(programma,dati));
        int nuovoid = manuale.executeForKey();
        programma.setId(nuovoid);
    }

    /**
     * Metodo che inserisce un programma d'allenamento combinato nella tabella "prog_allen_comb" del database a partire da un ProgAllenCombObject
     * @param programma Variabile di tipo ProgAllenCombObject
     */

    public void inserisciProgrammaCombinato(ProgAllenCombObject programma) {
        String dati = String.valueOf(programma.getId());
        dati += ","+programma.getCalorie_da_consumare()+", '"+programma.getDisponibilita()+"'";
        combinato.insert(aggiungiGiorni(programma,dati));
        int nuovoid = combinato.executeForKey();
        programma.setId(nuovoid);
    }

    /**
     * Metodo che costruisce la query che "riempie" tutti i giorni di un programma d'allenamento con tutti le sedute e le relative attivita
     * @param programma Variabile di tipo ProgrammaAllenamentoObject
     * @param dati Stringa sql parziale
     * @return La stringa sql completa che permette di inserire un programma d'allenamento con tutti i giorni, completi di sedute e attivita
     */

    private String aggiungiGiorni(ProgrammaAllenamentoObject programma,String dati){
        SedutaDAO sedutamodel = new SedutaDAO();
        AttivitaDAO attivitamodel = new AttivitaDAO();
        GiornoAllenDAO giornomodel = new GiornoAllenDAO();
        for(int i = 0;i<7;i++){
            GiornoAllenProgObject giorno = programma.getSettimanaallenamento(i);
            SedutaObject seduta = giorno.getSeduta();
            sedutamodel.inserisciSeduta(seduta);
            Iterator<AttivitaObject> iterator = seduta.getAttivita().iterator();
            int idseduta = seduta.getId();
            while(iterator.hasNext()){
                AttivitaObject attivita = iterator.next();
                attivita.setId_seduta(idseduta);
                attivitamodel.inserisciAttivita(attivita);
            }
            giornomodel.inserisciGiornoAllenProg(giorno);
            dati = dati + ",'" + giorno.getId_giorno() + "'";
        }
        return dati;
    }

    /**
     * Metodo che recupera un programma d'allenamento in base al tipo e al codice
     * @param comb Variabile booleana che indica se il programma d'allenamento che si vuole recuperare è combinato o manuale
     * @param progallen codice del programma d'allenamento
     * @return null se il programma d'allenamento non è presente. Una variabile di tipo ProgAllenCombObject se il programma è presente e combinato. Una variabile di tipo ProgAllenManObject se il programma è presente e manuale.
     */

    public ProgrammaAllenamentoObject getProgrammaAllenamento(boolean comb, Integer progallen) {
        if (progallen == 0) return null;
        int i = 4;
        DbTable tipoprogramma;
        if (comb) tipoprogramma = combinato;
        else {
            tipoprogramma = manuale;
            i = 2;
        }
        tipoprogramma.select();
        tipoprogramma.where("id='" + progallen + "'");
        ResultSet rs = tipoprogramma.fetch();
        ArrayList<GiornoAllenProgObject> giorniprogrammati = new ArrayList<GiornoAllenProgObject>();
        try {
            rs.next();
            GiornoAllenDAO giornomodel = new GiornoAllenDAO();
            if (comb) {
                LocalDate data = LocalDate.now().with(DayOfWeek.MONDAY);
                for (int j = i; j < i + 7; j++) {
                    giorniprogrammati.add(giornomodel.getGiornoProgrammatoComb(rs.getInt(j), progallen, data));
                    data = data.plusDays(1);
                }
                ProgAllenCombObject progcomb = new ProgAllenCombObject(giorniprogrammati, rs.getInt("calorie_da_consumare"), rs.getInt("disponibilita"));
                progcomb.setId(progallen);
                return progcomb;
            } else {
                for (int j = i; j < i + 7; j++) {
                    giorniprogrammati.add(giornomodel.getGiornoProgrammatoMan(rs.getInt(j)));
                }
                ProgAllenManObject progman = new ProgAllenManObject(giorniprogrammati);
                progman.setId(progallen);
                return progman;
            }
        } catch (Exception e) {
            System.out.println("C'è un errore grave" + e);
            return null;
        }
    }
}
