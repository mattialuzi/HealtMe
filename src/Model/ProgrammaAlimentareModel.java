package Model;

import Model.Dbtable.Prog_alim_manu;
import Object.ProgAlimManObject;
import Object.GiornoAlimProgObject;
import Object.PastoObject;
import Object.PortataObject;

import java.util.Iterator;

/**
 * Created by ALLDE on 28/01/2017.
 */
public class ProgrammaAlimentareModel {
    protected Prog_alim_manu manuale;

    public ProgrammaAlimentareModel() {
        manuale = new Prog_alim_manu();
    }

    public void inserisciProgManuale(ProgAlimManObject progmanuale) {
        PastoModel pastomodel = new PastoModel();
        PortataModel portatamodel = new PortataModel();
        GiornoAlimModel giornomodel = new GiornoAlimModel();
        String dati = String.valueOf(progmanuale.getId());
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
            dati = dati + "'" + giorno.getId_giorno()+"'";
        }
        manuale.insert(dati);
        int nuovoid = manuale.executeForKey();
        progmanuale.setId(nuovoid);
    }
}
