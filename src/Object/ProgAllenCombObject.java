package Object;


public class ProgAllenCombObject extends ProgrammaAllenamentoObject {

    private int calorie_da_consumare;
    private int disponibilita;

    public ProgAllenCombObject () {

        calorie_da_consumare=0;
        disponibilita=0;
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
