package Object;

import java.util.Date;

/**
 * Created by lorenzobraconi on 17/01/17.
 */
public class GiornoAllenDinamicoObject extends GiornoAllenObject{

    private int id_programma;
    private Date data;
    private int calorie_da_consumare;

    public GiornoAllenDinamicoObject() {
        calorie_da_consumare=0;
        id_programma=0;
        data=null;
    }

    public int getId_programma() {
        return id_programma;
    }

    public void setId_programma(int id_programma) {
        this.id_programma = id_programma;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }
}
