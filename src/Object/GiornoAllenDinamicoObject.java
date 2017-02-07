package Object;

import Object.Enum.GiornoEnum;

import java.time.LocalDate;
import java.util.Date;


public class GiornoAllenDinamicoObject extends GiornoAllenProgObject{

    private int id_programma;
    private LocalDate data;

    public GiornoAllenDinamicoObject() {
        id_programma=0;
        data=null;
        tipo = GiornoEnum.dinamico;
    }

    public GiornoAllenDinamicoObject (int idprogramma, LocalDate data, int caloriedaconsumare, SedutaObject seduta) {
        this.id_programma=idprogramma;
        this.data=data;
        this.calorie_da_consumare = caloriedaconsumare;
        tipo= GiornoEnum.dinamico;
        this.seduta = seduta;
    }

    public int getId_programma() {
        return id_programma;
    }

    public void setId_programma(int id_programma) {
        this.id_programma = id_programma;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
