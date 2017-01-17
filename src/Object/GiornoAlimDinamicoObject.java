package Object;

import Object.Enum.PastoEnum;
import Helpers.JObject;
import java.util.Date;


public class GiornoAlimDinamicoObject extends GiornoAlimObject {

    private int id_programma ;
    private Date data;

    public GiornoAlimDinamicoObject () {

        id_programma=0;
        data=null;
    }

    public Date getData() {
        return data;
    }

    public int getId_programma() {
        return id_programma;
    }

    public void setId_programma(int id_programma) {
        this.id_programma = id_programma;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
