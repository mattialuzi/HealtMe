package Object;

import Helpers.JObject;
import Object.Enum.ProgEnum;
import Object.Enum.GiornoEnum;

import java.util.Date;

/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class GiornoAllenObject extends JObject {

    private int id_giorno;
    private int id_prog;
    private Date data;
    private int calorie_consumate;
    private int calorie_da_consumare;
    private GiornoEnum tipo_giorno;
    private ProgEnum tipo_prog;

    public GiornoAllenObject()
    {
        id_giorno=0;
        id_prog=0;
        data=null;
        calorie_consumate=0;
        calorie_da_consumare=0;
        tipo_giorno=null;
        tipo_prog=null;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public int getId_prog() {
        return id_prog;
    }

    public void setId_prog(int id_prog) {
        this.id_prog = id_prog;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getCalorie_consumate() {
        return calorie_consumate;
    }

    public void setCalorie_consumate(int calorie_consumate) {
        this.calorie_consumate = calorie_consumate;
    }

    public int getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }

    public GiornoEnum getTipo_giorno() {
        return tipo_giorno;
    }

    public void setTipo_giorno(GiornoEnum tipo_giorno) {
        this.tipo_giorno = tipo_giorno;
    }

    public ProgEnum getTipo_prog() {
        return tipo_prog;
    }

    public void setTipo_prog(ProgEnum tipo_prog) {
        this.tipo_prog = tipo_prog;
    }
}
