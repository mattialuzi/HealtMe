package Object;

import Object.Enum.GiornoEnum;
import Object.Enum.PastoEnum;
import Helpers.JObject;

import java.time.LocalDate;
import java.util.Date;


public class GiornoAlimDinamicoObject extends GiornoAlimObject {

    private int id_programma ;
    private LocalDate data;
    private int fabbisogno;

    public GiornoAlimDinamicoObject () {

        fabbisogno=0;
        id_programma=0;
        data=null;
        tipo= GiornoEnum.dinamico;
    }

    public int getCalorie() {
        return fabbisogno;
    }

    public void setCalorie(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }

    public LocalDate getData() {
        return data;
    }

    public int getId_programma() {
        return id_programma;
    }

    public void setId_programma(int id_programma) {
        this.id_programma = id_programma;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

}
