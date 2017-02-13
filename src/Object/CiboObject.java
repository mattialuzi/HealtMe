package Object;

import Object.Enum.*;

/**
 * La classe CiboObject mappa la tabella "cibo" del database
 */

public class CiboObject {

    private String nome;
    private GruppoEnum gruppo;
    private int kilocal;
    private AllergiaEnum allergia;
    private PortataEnum portata;
    private CompatibilitaEnum compatibilita;
    private IdoneitaEnum idoneita;

    public CiboObject() {
        nome=null;
        kilocal=0;
        gruppo=null;
        allergia=null;
        compatibilita=null;
        portata=null;
        idoneita=null;
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

    public IdoneitaEnum getIdoneita() {
        return idoneita;
    }

    public void setIdoneita(IdoneitaEnum idoneita) {
        this.idoneita = idoneita;
    }
}
