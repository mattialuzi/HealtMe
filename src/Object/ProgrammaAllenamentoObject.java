package Object;

import java.util.ArrayList;

/**
 * La classe ProgrammaAllenamentoObject contiene dati comuni per gli Object ProgAllen
 */

public abstract class ProgrammaAllenamentoObject {

    protected int id;
    protected ArrayList<GiornoAllenProgObject> settimanaallenamento;

    protected ProgrammaAllenamentoObject() {
        id=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GiornoAllenProgObject getSettimanaallenamento(int index) { //Funzione che prende un GiornoAlimProject dall'arraylist in base all'indice
        return settimanaallenamento.get(index);
    }

    public void setSettimanaallenamento(int index, GiornoAllenProgObject giorno) {
        settimanaallenamento.set(index, giorno);
    }

}
