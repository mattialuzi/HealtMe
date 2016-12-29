package Object;

import Helpers.JObject;
import Object.Enum.GruppoEnum;
import Object.Enum.AllergiaEnum;
import Object.Enum.CompatibilitaEnum;
import Object.Enum.PortataEnum;

public class CiboObject extends JObject {

    private String nome;
    private int kilocal;
    private GruppoEnum gruppo;
    private AllergiaEnum allergia;
    private CompatibilitaEnum compatibilita;
    private PortataEnum portata;

    public CiboObject() {
        nome=null;
        kilocal=0;
        gruppo=null;
        allergia=null;
        compatibilita=null;
        portata=null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getKilocal() {
        return kilocal;
    }

    public void setKilocal(int kilocal) {
        this.kilocal = kilocal;
    }

    public GruppoEnum getGruppo() {
        return gruppo;
    }

    public void setGruppo(GruppoEnum gruppo) {
        this.gruppo = gruppo;
    }

    public AllergiaEnum getAllergia() {
        return allergia;
    }

    public void setAllergia(AllergiaEnum allergia) {
        this.allergia = allergia;
    }

    public CompatibilitaEnum getCompatibilita() {
        return compatibilita;
    }

    public void setCompatibilita(CompatibilitaEnum compatibilita) {
        this.compatibilita = compatibilita;
    }

    public PortataEnum getPortata() {
        return portata;
    }

    public void setPortata(PortataEnum portata) {
        this.portata = portata;
    }
}
