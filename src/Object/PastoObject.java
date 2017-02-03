package Object;

import Helpers.JObject;
import Model.Dbtable.Pasto;
import Model.Dbtable.Portata;
import Object.Enum.PastoEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PastoObject extends JObject {

    private int id;
    private PastoEnum tipo;
    private ArrayList<PortataObject> portate;
    private ArrayList<PastoEnum> tipipasti;


    public PastoObject() {
        id=0;
        tipo=null;
        portate=new ArrayList<PortataObject>();
        tipipasti = new ArrayList<PastoEnum>();
        tipipasti.add(0,PastoEnum.colazione);
        tipipasti.add(1,PastoEnum.pranzo);
        tipipasti.add(2,PastoEnum.cena);
        tipipasti.add(3,PastoEnum.spuntino);
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

    public void removePortata(String cibo){
        Iterator<PortataObject> portateiterator = portate.iterator();
        boolean exit = true;
        while(portateiterator.hasNext() && exit){
            PortataObject portata = portateiterator.next();
            if(portata.getCibo().getNome().equals(cibo)) {
                portate.remove(portata);
                exit = false;
            }
        }
    }

    public void setTipoByIndex(int index){
        setTipo(tipipasti.get(index));
    }
}
