package Object;

import Helpers.JObject;
/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class GiornoAlimProgObject extends JObject {

    private int id_giorno;
    private int fabbisogno;
    private int colazione;
    private int pranzo;
    private int cena;
    private int spuntino;

    public GiornoAlimProgObject() {
        id_giorno=0;
        fabbisogno=0;
        colazione=0;
        pranzo=0;
        cena=0;
        spuntino=0;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public int getFabbisogno() {
        return fabbisogno;
    }

    public void setFabbisogno(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }

    public int getColazione() {
        return colazione;
    }

    public void setColazione(int colazione) {
        this.colazione = colazione;
    }

    public int getPranzo() {
        return pranzo;
    }

    public void setPranzo(int pranzo) {
        this.pranzo = pranzo;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getSpuntino() {
        return spuntino;
    }

    public void setSpuntino(int spuntino) {
        this.spuntino = spuntino;
    }
}
