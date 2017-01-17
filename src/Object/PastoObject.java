package Object;

import Helpers.JObject;
import Model.Dbtable.Portata;
import Object.Enum.PastoEnum;

import java.util.ArrayList;
import java.util.List;

public class PastoObject extends JObject {

    private int id;
    private PastoEnum tipo;
    private List<Portata> portate;


    public PastoObject() {
        id=0;
        tipo=null;
        portate=new ArrayList<Portata>();
    }

    public List<Portata> getColazione() {
        return portate;
    }

    public void setColazione(List<Portata> colazione) {
        this.portate = colazione;
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
