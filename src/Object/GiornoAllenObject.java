package Object;

import Helpers.JObject;
import Object.Enum.ProgEnum;
import Object.Enum.GiornoEnum;

import java.util.Date;

/**
 * Created by lorenzobraconi on 28/12/16.
 */
public abstract class GiornoAllenObject extends JObject {

    protected GiornoEnum tipo;
    protected SedutaObject seduta;

    protected GiornoAllenObject() {
    }

    public GiornoEnum getTipo() {
        return tipo;
    }

    public void setTipo(GiornoEnum tipo) {
        this.tipo = tipo;
    }

    public SedutaObject getSeduta() {
        return seduta;
    }

    public void setSeduta(SedutaObject seduta) {
        this.seduta = seduta;
    }

    public abstract int getCalorie();

    public abstract void setCalorie(int calorie);
}
