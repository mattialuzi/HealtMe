package Object;

import Object.Enum.GiornoEnum;
import Object.Enum.PastoEnum;
import Helpers.JObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class GiornoAlimDinamicoObject extends GiornoAlimProgObject {

    private int id_programma ;
    private LocalDate data;

    public GiornoAlimDinamicoObject () {

        id_programma=0;
        data=null;
        tipo= GiornoEnum.dinamico;
    }

    public GiornoAlimDinamicoObject (int idprogramma, LocalDate data, int fabbisogno, ArrayList<PastoObject> pasti) {
        this.id_programma=idprogramma;
        this.data=data;
        this.fabbisogno=fabbisogno;
        tipo= GiornoEnum.dinamico;
        this.pasti = pasti;
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
