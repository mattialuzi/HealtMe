package Object;

import Helpers.JObject;
import Object.Enum.PortataEnum;

public class PortataObject extends JObject {

    private int id_pasto;
    private CiboObject cibo;
    private PortataEnum tipo;
    private int quantita;

    public PortataObject() {
        id_pasto=0;
        cibo=new CiboObject();
        tipo=null;
        quantita=0;
    }

    public int getId_pasto() {
        return id_pasto;
    }

    public void setId_pasto(int id_pasto) {
        this.id_pasto = id_pasto;
    }

    public CiboObject getCibo() {
        return cibo;
    }

    public void setCibo(CiboObject cibo) {
        this.cibo = cibo;
    }

    public PortataEnum getTipo() {
        return tipo;
    }

    public void setTipo(PortataEnum tipo) {
        this.tipo = tipo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
