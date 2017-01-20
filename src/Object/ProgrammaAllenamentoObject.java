package Object;

import Helpers.JObject;

import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
abstract class ProgrammaAllenamentoObject extends JObject {

    private int id;
    //private String username;
    //private Date data_inizio;
    //private Date data_fine;
    private GiornoAlimProgObject lunedi;
    private GiornoAlimProgObject martedi;
    private GiornoAlimProgObject mercoledi;
    private GiornoAlimProgObject giovedi;
    private GiornoAlimProgObject venerdi;
    private GiornoAlimProgObject sabato;
    private GiornoAlimProgObject domenica;

    protected ProgrammaAllenamentoObject() {
        id=0;
        //username=null;
        //data_inizio=null;
        //data_fine=null;
        lunedi= new GiornoAlimProgObject();
        martedi= new GiornoAlimProgObject();
        mercoledi= new GiornoAlimProgObject();
        giovedi= new GiornoAlimProgObject();
        venerdi= new GiornoAlimProgObject();
        sabato= new GiornoAlimProgObject();
        domenica= new GiornoAlimProgObject();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public String getUsername() {
        return username;
    }*/

    /*public void setUsername(String username) {
        this.username = username;
    }

    public Date getData_inizio() {
        return data_inizio;
    }*/

    /*public void setData_inizio(Date data_inizio) {
        this.data_inizio = data_inizio;
    }*/

    /*public Date getData_fine() {
        return data_fine;
    }*/

    /*public void setData_fine(Date data_fine) {
        this.data_fine = data_fine;
    }*/

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
