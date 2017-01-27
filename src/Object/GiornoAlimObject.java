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
        pasti = new ArrayList<PastoObject>(Collections.nCopies(4, new PastoObject()));
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
        HashMap<String, PastoObject> pasti = new HashMap<String, PastoObject>();
        pasti.put("colazione", pasti.get(0));
        pasti.put("pranzo", pasti.get(1));
        pasti.put("cena", pasti.get(2));
        pasti.put("spuntino", pasti.get(3));
        return pasti.get(tipo);
    }
}


