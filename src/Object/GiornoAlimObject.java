package Object;

import Helpers.JObject;

/**
 * Created by Mattia on 16/01/2017.
 */
public class GiornoAlimObject extends JObject {

    private int id_giorno;
    private PastoObject colazione;
    private PastoObject spuntino;
    private PastoObject pranzo;
    private PastoObject cena;

    public GiornoAlimObject() {
        id_giorno=0;
        colazione=null;
        pranzo=null;
        cena=null;
        spuntino=null;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public PastoObject getColazione() {
        return colazione;
    }

    public void setColazione(PastoObject colazione) {
        this.colazione = colazione;
    }

    public PastoObject getSpuntino() {
        return spuntino;
    }

    public void setSpuntino(PastoObject spuntino) {
        this.spuntino = spuntino;
    }

    public PastoObject getPranzo() {
        return pranzo;
    }

    public void setPranzo(PastoObject pranzo) {
        this.pranzo = pranzo;
    }

    public PastoObject getCena() {
        return cena;
    }

    public void setCena(PastoObject cena) {
        this.cena = cena;
    }
}


