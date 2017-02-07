package Object;


import java.util.ArrayList;

public class ProgAllenCombObject extends ProgrammaAllenamentoObject {

    private int calorie_da_consumare;
    private int disponibilita;

    public ProgAllenCombObject () {
        calorie_da_consumare=0;
        disponibilita=0;
        settimanaallenamento = new ArrayList<GiornoAllenProgObject>();
        for (int i=0; i<7; i++)
            settimanaallenamento.add(i, new GiornoAllenProgObject());
    }

    public ProgAllenCombObject(ArrayList<GiornoAllenProgObject> giorniprogrammati, int caloriedaconsumare, int disponibilita) {
        this.calorie_da_consumare = caloriedaconsumare;
        this.disponibilita = disponibilita;
        settimanaallenamento = giorniprogrammati;
    }

    public int getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

}
