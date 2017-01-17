package Object;

import Helpers.JObject;
import Model.Dbtable.Portata;
import Object.Enum.PastoEnum;

import java.util.ArrayList;
import java.util.List;

public class PastoObject extends JObject {

    private int id;
    private PastoEnum tipo;
    private List<Portata> colazione;
    private List<Portata> pranzo;
    private List<Portata> cena;
    private List<Portata> spuntino;

    public PastoObject() {
        id=0;
        tipo=null;
        colazione=new ArrayList<Portata>();
        pranzo=new ArrayList<Portata>();
        cena=new ArrayList<Portata>();
        spuntino=new ArrayList<Portata>();
    }

    public List<Portata> getColazione() {
        return colazione;
    }

    public void setColazione(List<Portata> colazione) {
        this.colazione = colazione;
    }

    public List<Portata> getPranzo() {
        return pranzo;
    }

    public void setPranzo(List<Portata> pranzo) {
        this.pranzo = pranzo;
    }

    public List<Portata> getCena() {
        return cena;
    }

    public void setCena(List<Portata> cena) {
        this.cena = cena;
    }

    public List<Portata> getSpuntino() {
        return spuntino;
    }

    public void setSpuntino(List<Portata> spuntino) {
        this.spuntino = spuntino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PastoEnum getTipo() {
        return tipo;
    }

    public void setTipo(PastoEnum tipo) {
        this.tipo = tipo;
    }
}
