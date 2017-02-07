package Object;

import Helpers.JObject;

public class AttivitaObject extends JObject {

    private int id_seduta;
    private EsercizioObject esercizio;
    private int quantita;

    public AttivitaObject(EsercizioObject nuovoesercizio) {
        id_seduta =0;
        esercizio=nuovoesercizio;
        quantita=0;
    }

    public AttivitaObject(EsercizioObject nuovoesercizio, int quantita) {
        id_seduta =0;
        esercizio=nuovoesercizio;
        this.quantita=quantita;
    }

    public int getId_seduta() {
        return id_seduta;
    }

    public void setId_seduta(int id_seduta) {
        this.id_seduta = id_seduta;
    }

    public EsercizioObject getEsercizio() {
        return esercizio;
    }

    public void setEsercizio(EsercizioObject esercizio) {
        this.esercizio = esercizio;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}

