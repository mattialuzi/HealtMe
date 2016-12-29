package Object;

import Helpers.JObject;
import Object.Enum.PastoEnum;

public class PastoObject extends JObject {

    private int id;
    private PastoEnum tipo;

    public PastoObject() {
        id=0;
        tipo=null;
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
