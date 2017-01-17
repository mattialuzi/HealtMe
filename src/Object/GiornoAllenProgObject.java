package Object;

/**
 * Created by lorenzobraconi on 17/01/17.
 */
public class GiornoAllenProgObject extends GiornoAllenObject{

    private int id_giorno;
    private int calorie_da_consumare;

    public GiornoAllenProgObject() {
        id_giorno=0;
        calorie_da_consumare=0;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public int getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }
}
