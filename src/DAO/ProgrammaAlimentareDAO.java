package DAO;

import Helpers.DbTable;
import DAO.Dbtable.Prog_alim_comb;
import DAO.Dbtable.Prog_alim_manu;
import Object.*;
import Object.Enum.AlimentazioneEnum;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ALLDE on 28/01/2017.
 */
public class ProgrammaAlimentareDAO {
    protected Prog_alim_manu manuale;
    protected Prog_alim_comb combinato;

    public ProgrammaAlimentareDAO() {
        manuale = new Prog_alim_manu();
        combinato = new Prog_alim_comb();
    }

    public void inserisciProgrammaManuale(ProgAlimManObject programma){
        String dati =String.valueOf(programma.getId());
        manuale.insert(aggiungiGiorni(programma,dati));
        int nuovoid = manuale.executeForKey();
        programma.setId(nuovoid);
    }

    public void inserisciProgrammaCombinato(ProgAlimCombObject programma){
        String dati =String.valueOf(programma.getId());
        dati += "," +  programma.getFabbisogno() + ",'" + programma.getTipo_alimentazione() + "'";
        combinato.insert(aggiungiGiorni(programma,dati));
        int nuovoid = combinato.executeForKey();
        programma.setId(nuovoid);
    }

    private String aggiungiGiorni (ProgrammaAlimentareObject programma,String dati) {
        PastoDAO pastoDAO = new PastoDAO();
        PortataDAO portataDAO = new PortataDAO();
        GiornoAlimDAO giornoAlimDAO = new GiornoAlimDAO();
        for (int i = 0; i < 7; i++) {
            GiornoAlimProgObject giorno = programma.getSettimanaalimentare(i);
            for (int j = 0; j < 4; j++) {
                PastoObject pasto = giorno.getPasti(j);
                pastoDAO.inserisciPasto(pasto);
                Iterator<PortataObject> iterator = pasto.getPortate().iterator();
                int idpasto = pasto.getId();
                while (iterator.hasNext()) {
                    PortataObject portata = iterator.next();
                    portata.setId_pasto(idpasto);
                    portataDAO.inserisciPortata(portata);
                }
            }
            giornoAlimDAO.inserisciGiornoAlimProg(giorno);
            dati = dati + ",'" + giorno.getId_giorno()+"'";
        }
        return dati;
    }

    public ProgrammaAlimentareObject getProgrammaAlimentare(boolean comb, Integer progalim) {
        if (progalim == 0) return null;
        int i = 4;
        DbTable tipoprogramma;
        if (comb) tipoprogramma = combinato;
        else {
            tipoprogramma = manuale;
            i = 2;
        }
        tipoprogramma.select();
        tipoprogramma.where("id='" + progalim + "'");
        ResultSet rs = tipoprogramma.fetch();
        ArrayList<GiornoAlimProgObject> giorniprogrammati = new ArrayList<GiornoAlimProgObject>();
        try {
            rs.next();
            GiornoAlimDAO giornoDAO = new GiornoAlimDAO();
            if (comb) {
                LocalDate data = LocalDate.now().with(DayOfWeek.MONDAY);
                for (int j = i; j < i + 7; j++) {
                    giorniprogrammati.add(giornoDAO.getGiornoProgrammatoComb(rs.getInt(j), progalim, data));
                    data = data.plusDays(1);
                }
                ProgAlimCombObject progcomb = new ProgAlimCombObject(giorniprogrammati,rs.getInt("fabbisogno"), AlimentazioneEnum.valueOf(rs.getString("tipo_alimentazione")));
                progcomb.setId(progalim);
                return progcomb;
            } else {
                for (int j = i; j < i + 7; j++) {
                    giorniprogrammati.add(giornoDAO.getGiornoProgrammatoMan(rs.getInt(j)));
                }
                ProgAlimManObject progman = new ProgAlimManObject(giorniprogrammati);
                progman.setId(progalim);
                return progman;
            }
        } catch (Exception e) {
            System.out.println("C'è un errore grave" + e);
            return null;
        }
    }
}
