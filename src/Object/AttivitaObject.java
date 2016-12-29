package Object;

import Helpers.JObject;

public class AttivitaObject extends JObject {


    private int id_giorno;
    private String esercizio;
    private int quantita;

    public AttivitaObject()
    {
        id_giorno=0;
        esercizio=null;
        quantita=0;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public String getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(String esercizio) {
        this.esercizio = esercizio;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}

