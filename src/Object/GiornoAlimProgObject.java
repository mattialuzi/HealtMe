package Object;

import Object.Enum.GiornoEnum;
import java.util.ArrayList;

/**
 * La classe GiornoAlimProgObject mappa la tabella "giorno_alim_prog" del database
 */

public class GiornoAlimProgObject extends GiornoAlimObject {

    private int id_giorno;
    protected int fabbisogno;

    public GiornoAlimProgObject() {
        id_giorno=0;
        fabbisogno=0;
        tipo= GiornoEnum.programmato;
        pasti = new ArrayList<PastoObject>();
        for (int i=0; i<4; i++)
            pasti.add(i, new PastoObject(i));
    }

    public GiornoAlimProgObject(ArrayList<PastoObject> listapasti){
        id_giorno=0;
        fabbisogno=0;
        tipo= GiornoEnum.programmato;
        pasti = listapasti;
    }

    public GiornoAlimProgObject(ArrayList<PastoObject> listapasti, int fabbisogno){
        id_giorno=0;
        this.fabbisogno=fabbisogno;
        tipo= GiornoEnum.programmato;
        pasti = listapasti;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public int getCalorie() {
        return fabbisogno;
    }

    public void setCalorie(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }

}
