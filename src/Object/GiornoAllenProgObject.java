package Object;

import Object.Enum.GiornoEnum;

/**
 * La classe GiornoAllenProgObject mappa la tabella "giorno_allen_progr" del database
 */

public class GiornoAllenProgObject extends GiornoAllenObject{

    private int id_giorno;
    protected int calorie_da_consumare;

    public GiornoAllenProgObject() {
        id_giorno=0;
        calorie_da_consumare=0;
        tipo= GiornoEnum.programmato;
        seduta = new SedutaObject();
    }

    public GiornoAllenProgObject(SedutaObject seduta){
        id_giorno=0;
        calorie_da_consumare=0;
        tipo= GiornoEnum.programmato;
        this.seduta = seduta;
    }

    public GiornoAllenProgObject(SedutaObject seduta, int calorie_da_consumare){
        id_giorno=0;
        this.calorie_da_consumare=calorie_da_consumare;
        tipo= GiornoEnum.programmato;
        this.seduta = seduta;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public int getCalorie() {
        return calorie_da_consumare;
    }

    public void setCalorie(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }
}
