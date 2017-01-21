package Object;

import Helpers.JObject;
import Model.Dbtable.Portata;
import Object.Enum.PastoEnum;

import java.util.ArrayList;
import java.util.List;

public class PastoObject extends JObject {

    private int id;
    private PastoEnum tipo;
    private ArrayList<PortataObject> portate;


    public PastoObject() {
        id=0;
        tipo=null;
        portate=new ArrayList<PortataObject>();
    }

    public ArrayList<PortataObject> getPortate() {
        return portate;
    }

    public void setPortate(ArrayList<PortataObject> portate) {
        this.portate = portate;
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

    public void addPortata (PortataObject portata) {
        portate.add(portata);
    }
}
