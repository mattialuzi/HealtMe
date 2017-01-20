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

    public int getLunedi() {
        return lunedi;
    }

    public void setLunedi(int lunedi) {
        this.lunedi = lunedi;
    }

    public int getMartedi() {
        return martedi;
    }

    public void setMartedi(int martedi) {
        this.martedi = martedi;
    }

    public int getMercoledi() {
        return mercoledi;
    }

    public void setMercoledi(int mercoledi) {
        this.mercoledi = mercoledi;
    }

    public int getGiovedi() {
        return giovedi;
    }

    public void setGiovedi(int giovedi) {
        this.giovedi = giovedi;
    }

    public int getVenerdi() {
        return venerdi;
    }

    public void setVenerdi(int venerdi) {
        this.venerdi = venerdi;
    }

    public int getSabato() {
        return sabato;
    }

    public void setSabato(int sabato) {
        this.sabato = sabato;
    }

    public int getDomenica() {
        return domenica;
    }

    public void setDomenica(int domenica) {
        this.domenica = domenica;
    }

}
