package Object;

import Object.Enum.GiornoEnum;
import Object.Enum.PastoEnum;
import Helpers.JObject;
import java.util.Date;


public class GiornoAlimDinamicoObject extends GiornoAlimObject {

    private int id_programma ;
    private Date data;
    private int fabbisogno;

    public GiornoAlimDinamicoObject () {

        fabbisogno=0;
        id_programma=0;
        data=null;
        tipo= GiornoEnum.dinamico;
    }

    public int getFabbisogno() {
        return fabbisogno;
    }

    public void setFabbisogno(int fabbisogno) {
        this.fabbisogno = fabbisogno;
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
