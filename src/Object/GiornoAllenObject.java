package Object;

import Helpers.JObject;
import Object.Enum.ProgEnum;
import Object.Enum.GiornoEnum;

import java.util.Date;

/**
 * Created by lorenzobraconi on 28/12/16.
 */
public abstract class GiornoAllenObject extends JObject {

    private SedutaObject seduta;

    protected GiornoAllenObject()
    {
        seduta= new SedutaObject();
    }

    public SedutaObject getSeduta() {
        return seduta;
    }

    public void setSeduta(SedutaObject seduta) {
        this.seduta = seduta;
    }
}
