package Object;

import Helpers.JObject;

/**
 * Created by Mattia on 16/01/2017.
 */
abstract class GiornoAlimObject extends JObject {

    private PastoObject colazione;
    private PastoObject spuntino;
    private PastoObject pranzo;
    private PastoObject cena;

    public GiornoAlimObject() {

        colazione=null;
        pranzo=null;
        cena=null;
        spuntino=null;
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


