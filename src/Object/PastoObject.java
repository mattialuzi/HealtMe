package Object;

import Object.Enum.PastoEnum;
import java.util.ArrayList;

/**
 * La classe PastoObject mappa la tabella "pasto" del database
 */

public class PastoObject {

    private int id;
    private PastoEnum tipo;
    private ArrayList<PortataObject> portate;
    private ArrayList<PastoEnum> tipipasti;


    public PastoObject() {
        id=0;
        portate=new ArrayList<PortataObject>();
        tipipasti = new ArrayList<PastoEnum>();
        tipipasti.add(0,PastoEnum.colazione);
        tipipasti.add(1,PastoEnum.pranzo);
        tipipasti.add(2,PastoEnum.cena);
        tipipasti.add(3,PastoEnum.spuntino);
        tipo= null;
    }

    public PastoObject(int i) {
        id=0;
        portate=new ArrayList<PortataObject>();
        tipipasti = new ArrayList<PastoEnum>();
        tipipasti.add(0,PastoEnum.colazione);
        tipipasti.add(1,PastoEnum.pranzo);
        tipipasti.add(2,PastoEnum.cena);
        tipipasti.add(3,PastoEnum.spuntino);
        tipo= tipipasti.get(i);
    }

    public PastoObject(PastoEnum tipo) {
        id=0;
        this.tipo = tipo;
        portate=new ArrayList<PortataObject>();
        tipipasti = new ArrayList<PastoEnum>();
        tipipasti.add(0,PastoEnum.colazione);
        tipipasti.add(1,PastoEnum.pranzo);
        tipipasti.add(2,PastoEnum.cena);
        tipipasti.add(3,PastoEnum.spuntino);
    }

    public PastoObject(ArrayList<PortataObject> portate, PastoEnum tipo) {
        id=0;
        this.tipo=tipo;
        this.portate=portate;
        tipipasti = new ArrayList<PastoEnum>();
        tipipasti.add(0,PastoEnum.colazione);
        tipipasti.add(1,PastoEnum.pranzo);
        tipipasti.add(2,PastoEnum.cena);
        tipipasti.add(3,PastoEnum.spuntino);
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

    public void removePortata(PortataObject portata) {
        portate.remove(portata);
    }

    public void setTipoByIndex(int index){
        setTipo(tipipasti.get(index));
    }
}
