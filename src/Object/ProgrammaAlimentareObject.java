package Object;

import Object.Enum.AlimentazioneEnum;
import java.util.ArrayList;

/**
 * La classe ProgrammaAlimentareObject contiene dati comuni per gli Object ProgAlim
 */

public abstract class ProgrammaAlimentareObject {

    protected int id;
    protected ArrayList<GiornoAlimProgObject> settimanaalimentare;
    protected AlimentazioneEnum tipo_alimentazione;

    protected ProgrammaAlimentareObject()
    {
        id=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GiornoAlimProgObject getSettimanaalimentare(int index) { //Funzione che prende un GiornoAlimProject dall'arraylist in base all'indice
        return settimanaalimentare.get(index);
    }

    public void setSettimanaalimentare(int index, GiornoAlimProgObject giorno) {
        settimanaalimentare.set(index, giorno);
    }

    public AlimentazioneEnum getTipo_alimentazione() {
        return tipo_alimentazione;
    }

    public void setTipo_alimentazione(AlimentazioneEnum tipo_alimentazione) {
        this.tipo_alimentazione = tipo_alimentazione;
    }
}

