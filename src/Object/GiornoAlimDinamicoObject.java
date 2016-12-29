package Object;

import Object.Enum.PastoEnum;
import Helpers.JObject;
import java.util.Date;


public class GiornoAlimDinamicoObject extends JObject {
    private int id_giorno;
    private Date data;
    private int cal_assunte;
    private int colazione;
    private int spuntino;
    private int pranzo;
    private int cena;
    private PastoEnum status_pasto;

    public GiornoAlimDinamicoObject () {
        id_giorno=0;
        data=null;
        cal_assunte=0;
        colazione=0;
        spuntino=0;
        pranzo=0;
        cena=0;
        status_pasto=null;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getCal_assunte() {
        return cal_assunte;
    }

    public void setCal_assunte(int cal_assunte) {
        this.cal_assunte = cal_assunte;
    }

    public int getColazione() {
        return colazione;
    }

    public void setColazione(int colazione) {
        this.colazione = colazione;
    }

    public int getSpuntino() {
        return spuntino;
    }

    public void setSpuntino(int spuntino) {
        this.spuntino = spuntino;
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

    public PastoEnum getStatus_pasto() {
        return status_pasto;
    }

    public void setStatus_pasto(PastoEnum status_pasto) {
        this.status_pasto = status_pasto;
    }
}
