package Object;

import Helpers.JObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public abstract class ProgrammaAlimentareObject extends JObject {
    protected int id;
    protected ArrayList<GiornoAlimProgObject> settimanaalimentare;

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

    public GiornoAlimProgObject getSettimanaalimentare(int index) {
        return settimanaalimentare.get(index);
    }

    public void setSettimanaalimentare(int index, GiornoAlimProgObject giorno) {
        settimanaalimentare.set(index, giorno);
    }
}

