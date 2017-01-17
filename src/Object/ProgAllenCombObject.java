package Object;


import Object.Enum.DisponibilitaEnum;

import java.util.Date;

public class ProgAllenCombObject extends ProgrammaAllenamentoObject {

    private int calorie_da_consumare;
    private DisponibilitaEnum disponibilita;

    public ProgAllenCombObject () {

        calorie_da_consumare=0;
        disponibilita=null;
    }

    public int getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }

    public DisponibilitaEnum getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(DisponibilitaEnum disponibilita) {
        this.disponibilita = disponibilita;
    }

}
