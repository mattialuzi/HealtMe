package Object;

import Helpers.JObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public abstract class ProgrammaAllenamentoObject extends JObject {
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
