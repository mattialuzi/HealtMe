package Object;

import Helpers.JObject;
import Model.Dbtable.Attivita;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lorenzobraconi on 17/01/17.
 */
public class SedutaObject extends JObject {
    private int id;
    private List<Attivita> attivita;

    public SedutaObject() {
        id=0;
        attivita=new ArrayList<Attivita>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Attivita> getAttivita() {
        return attivita;
    }

    public void setAttivita(List<Attivita> attivita) {
        this.attivita = attivita;
    }
}
