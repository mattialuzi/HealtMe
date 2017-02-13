package Object;

import java.util.ArrayList;

/**
 * La classe SedutaObject mappa la tabella "seduta" del database
 */

public class SedutaObject {

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
