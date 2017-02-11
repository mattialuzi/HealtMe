package Object;

import Object.Enum.GiornoEnum;

/**
 * Created by lorenzobraconi on 28/12/16.
 */
public abstract class GiornoAllenObject {

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
