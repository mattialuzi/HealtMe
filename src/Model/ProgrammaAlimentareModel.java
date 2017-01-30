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

    public void inserisciProgManuale(ProgAlimManObject progmanuale) {
        PastoModel pastomodel = new PastoModel();
        PortataModel portatamodel = new PortataModel();
        GiornoAlimModel giornomodel = new GiornoAlimModel();
        String dati =String.valueOf(progmanuale.getId());
        for (int i = 0; i < 7; i++) {
            GiornoAlimProgObject giorno = progmanuale.getSettimanaalimentare(i);
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
        manuale.insert(dati);
        int nuovoid = manuale.executeForKey();
        progmanuale.setId(nuovoid);
    }

    public ProgrammaAlimentareObject getProgrammaAlimentare(boolean comb, Integer progalim) {
        if (progalim == null) return null;
        int i = 3;
        DbTable tipoprogramma;
        if (comb) tipoprogramma = combinato;
        else {
            tipoprogramma = manuale;
            i = 1;
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
                ProgAlimCombObject progcomb = new ProgAlimCombObject(giorniprogrammati);
                progcomb.setId(progalim);
                progcomb.setFabbisogno(rs.getInt("fabbisogno"));
                progcomb.setTipo_alimentazione(AlimentazioneEnum.valueOf(rs.getString("tipo_alimentazione")));
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
