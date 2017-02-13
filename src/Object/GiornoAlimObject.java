package Object;

import Object.Enum.GiornoEnum;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * La classe GiornoAlimObject contiene dati comuni per gli Object GiornoAlim
 */

public abstract class GiornoAlimObject {

    protected GiornoEnum tipo;
    protected ArrayList<PastoObject> pasti;

    protected GiornoAlimObject() {
    }

    public GiornoEnum getTipo() {
        return tipo;
    }

    public PastoObject getPasti(int index) { //prendo un pasto dell'arraylist tramite indice
        return pasti.get(index);
    }

    public void setPasti(int index, PastoObject pasto) {
        pasti.set(index, pasto);
    }

    public PastoObject getPastoByTipo(String tipo) {
        HashMap<String, PastoObject> pastimap = new HashMap<String, PastoObject>();
        pastimap.put("colazione", pasti.get(0));
        pastimap.put("pranzo", pasti.get(1));
        pastimap.put("spuntino", pasti.get(2));
        pastimap.put("cena", pasti.get(3));
        return pastimap.get(tipo);
    }

    public abstract int getCalorie();

    public abstract void setCalorie(int calorie);
}


