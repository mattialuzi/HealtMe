package Object;

import Helpers.JObject;

/**
 * Created by Mattia on 16/01/2017.
 */
abstract class GiornoAlimObject extends JObject {

    protected PastoObject colazione;
    protected PastoObject spuntino;
    protected PastoObject pranzo;
    protected PastoObject cena;

    protected GiornoAlimObject() {
        colazione= new PastoObject();
        pranzo= new PastoObject();
        cena= new PastoObject();
        spuntino= new PastoObject();
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


