package Model;

import Helpers.DbTable;
import Model.Dbtable.Prog_alim_comb;
import Model.Dbtable.Prog_alim_manu;
import Object.*;
import Object.Enum.AlimentazioneEnum;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ALLDE on 28/01/2017.
 */
public class ProgrammaAlimentareModel {
    protected Prog_alim_manu manuale;
    protected Prog_alim_comb combinato;

    public ProgrammaAlimentareModel() {
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
        PastoModel pastomodel = new PastoModel();
        PortataModel portatamodel = new PortataModel();
        GiornoAlimModel giornomodel = new GiornoAlimModel();
        for (int i = 0; i < 7; i++) {
            GiornoAlimProgObject giorno = programma.getSettimanaalimentare(i);
            for (int j = 0; j < 4; j++) {
                PastoObject pasto = giorno.getPasti(j);
                pastomodel.inserisciPasto(pasto);
                Iterator<PortataObject> iterator = pasto.getPortate().iterator();
                int idpasto = pasto.getId();
                while (iterator.hasNext()) {
                    PortataObject portata = iterator.next();
                    portata.setId_pasto(idpasto);
                    portatamodel.inserisciPortata(portata);
                }
            }
            giornomodel.inserisciGiornoAlimProg(giorno);
            dati = dati + ",'" + giorno.getId_giorno()+"'";
        }
        return dati;
    }

    public ProgrammaAlimentareObject getProgrammaAlimentare(boolean comb, Integer progalim) {
        if (progalim == null) return null;
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
            GiornoAlimModel giornomodel = new GiornoAlimModel();
            for (int j = i; j < i + 7; j++) {
                giorniprogrammati.add(giornomodel.getGiornoProgrammato(rs.getInt(j)));
            }
            if (comb) {
                ProgAlimCombObject progcomb = new ProgAlimCombObject(giorniprogrammati,rs.getInt("fabbisogno"), AlimentazioneEnum.valueOf(rs.getString("tipo_alimentazione")));
                progcomb.setId(progalim);
                return progcomb;
            } else {
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
