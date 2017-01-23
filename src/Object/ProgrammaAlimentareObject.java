package Object;

import Helpers.JObject;

import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public abstract class ProgrammaAlimentareObject extends JObject {
    private int id;
    private GiornoAlimProgObject lunedi;
    private GiornoAlimProgObject martedi;
    private GiornoAlimProgObject mercoledi;
    private GiornoAlimProgObject giovedi;
    private GiornoAlimProgObject venerdi;
    private GiornoAlimProgObject sabato;
    private GiornoAlimProgObject domenica;

    protected ProgrammaAlimentareObject()
    {
        id=0;
        lunedi= new GiornoAlimProgObject();
        martedi=new GiornoAlimProgObject();
        mercoledi=new GiornoAlimProgObject();
        giovedi=new GiornoAlimProgObject();
        venerdi=new GiornoAlimProgObject();
        sabato=new GiornoAlimProgObject();
        domenica=new GiornoAlimProgObject();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GiornoAlimProgObject getLunedi() {
        return lunedi;
    }

    public void setLunedi(GiornoAlimProgObject lunedi) {
        this.lunedi = lunedi;
    }

    public GiornoAlimProgObject getMartedi() {
        return martedi;
    }

    public void setMartedi(GiornoAlimProgObject martedi) {
        this.martedi = martedi;
    }

    public GiornoAlimProgObject getMercoledi() {
        return mercoledi;
    }

    public void setMercoledi(GiornoAlimProgObject mercoledi) {
        this.mercoledi = mercoledi;
    }

    public GiornoAlimProgObject getGiovedi() {
        return giovedi;
    }

    public void setGiovedi(GiornoAlimProgObject giovedi) {
        this.giovedi = giovedi;
    }

    public GiornoAlimProgObject getVenerdi() {
        return venerdi;
    }

    public void setVenerdi(GiornoAlimProgObject venerdi) {
        this.venerdi = venerdi;
    }

    public GiornoAlimProgObject getSabato() {
        return sabato;
    }

    public void setSabato(GiornoAlimProgObject sabato) {
        this.sabato = sabato;
    }

    public GiornoAlimProgObject getDomenica() {
        return domenica;
    }

    public void setDomenica(GiornoAlimProgObject domenica) {
        this.domenica = domenica;
    }

}

