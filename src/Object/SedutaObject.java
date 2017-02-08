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
    private ArrayList<AttivitaObject> listaattivita;

    public SedutaObject() {
        id=0;
        listaattivita= new ArrayList<AttivitaObject>();
    }

    public SedutaObject(ArrayList<AttivitaObject> attivita) {
        id=0;
        this.listaattivita=attivita;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<AttivitaObject> getAttivita() {
        return listaattivita;
    }

    public void setAttivita(ArrayList<AttivitaObject> attivita) {
        this.listaattivita = attivita;
    }

    public void addAttivita (AttivitaObject attivita) {
        listaattivita.add(attivita);
    }

    public void removeAttivita(AttivitaObject attivita) {
        listaattivita.remove(attivita);
    }
}
