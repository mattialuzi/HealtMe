package Object;

import Object.Enum.PortataEnum;

/**
 * La classe PortataObject mappa la tabella "portata" del database
 */

public class PortataObject {

    private int id_pasto;
    private CiboObject cibo;
    private PortataEnum tipo;
    private int quantita;

    public PortataObject(CiboObject nuovocibo) {
        id_pasto=0;
        cibo=nuovocibo;
        tipo=null;
        quantita=0;
    }

    public PortataObject(CiboObject nuovocibo, PortataEnum tipo, int quantita) {
        id_pasto=0;
        cibo=nuovocibo;
        this.tipo=tipo;
        this.quantita=quantita;
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
