package Object;

import Helpers.JObject;
import Model.Dbtable.Pasto;
import Object.Enum.GiornoEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Mattia on 16/01/2017.
 */
public abstract class GiornoAlimObject extends JObject {

    protected GiornoEnum tipo;
    protected ArrayList<PastoObject> pasti;

    protected GiornoAlimObject() {
    }

    public GiornoEnum getTipo() {
        return tipo;
    }

    public PastoObject getPasti(int index) {
        return pasti.get(index);
    }

    public void setPasti(int index, PastoObject pasto) {
        pasti.set(index, pasto);
    }

    public PastoObject getPastoByTipo(String tipo) {
        HashMap<String, PastoObject> pastimap = new HashMap<String, PastoObject>();
        pastimap.put("colazione", pasti.get(0));
        pastimap.put("pranzo", pasti.get(1));
        pastimap.put("cena", pasti.get(2));
        pastimap.put("spuntino", pasti.get(3));
        return pastimap.get(tipo);
    }

    public abstract int getCalorie();

    public abstract void setCalorie(int calorie);
}


