package Model;
import Helpers.DbTable;
import Object.ProgAllenManObject;
import Object.ProgrammaAllenamentoObject;
import Object.GiornoAllenProgObject;
import Object.ProgAllenCombObject;
import Object.SedutaObject;
import Object.AttivitaObject;

import Model.Dbtable.Prog_allen_comb;
import Model.Dbtable.Prog_allen_manu;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ALLDE on 08/02/2017.
 */
public class ProgrammaAllenamentoModel {
    protected Prog_allen_manu manuale;
    protected Prog_allen_comb combinato;


    public ProgrammaAllenamentoModel() {
        manuale = new Prog_allen_manu();
        combinato = new Prog_allen_comb();
    }

    public void inserisciProgrammaManuale(ProgAllenManObject programma){
        String dati = String.valueOf(programma.getId());
        manuale.insert(aggiungiGiorni(programma,dati));
        int nuovoid = manuale.executeForKey();
        programma.setId(nuovoid);
    }

    public void inserisciProgrammaCombinato(ProgAllenCombObject programma) {
        String dati = String.valueOf(programma.getId());
        dati += ","+programma.getCalorie_da_consumare()+", '"+programma.getDisponibilita()+"'";
        combinato.insert(aggiungiGiorni(programma,dati));
        int nuovoid = combinato.executeForKey();
        programma.setId(nuovoid);
    }

    private String aggiungiGiorni(ProgrammaAllenamentoObject programma,String dati){
        SedutaModel sedutamodel = new SedutaModel();
        AttivitaModel attivitamodel = new AttivitaModel();
        GiornoAllenModel giornomodel = new GiornoAllenModel();
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
            GiornoAllenModel giornomodel = new GiornoAllenModel();
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
