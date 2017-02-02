package Object;

import Helpers.JObject;
import Object.Enum.AlimentazioneEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public abstract class ProgrammaAlimentareObject extends JObject {
    protected int id;
    protected ArrayList<GiornoAlimObject> settimanaalimentare;

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

    public GiornoAlimObject getSettimanaalimentare(int index) {
        return settimanaalimentare.get(index);
    }

    public void setSettimanaalimentare(int index, GiornoAlimObject giorno) {
        settimanaalimentare.set(index, giorno);
    }


}

