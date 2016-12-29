package Object;


import Object.Enum.DisponibilitaEnum;

import java.util.Date;

public class ProgAllenCombObject {
    private int id;
    private String username;
    private Date data_inizio;
    private Date data_fine;
    private int calorie_da_consumare;
    private DisponibilitaEnum disponibilita;
    private int lunedi;
    private int martedi;
    private int mercoledi;
    private int giovedi;
    private int venerdi;
    private int sabato;
    private int domenica;

    public ProgAllenCombObject () {
        id=0;
        username=null;
        data_inizio=null;
        data_fine=null;
        calorie_da_consumare=0;
        disponibilita=null;
        lunedi=0;
        martedi=0;
        mercoledi=0;
        giovedi=0;
        venerdi=0;
        sabato=0;
        domenica=0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getData_inizio() {
        return data_inizio;
    }

    public void setData_inizio(Date data_inizio) {
        this.data_inizio = data_inizio;
    }

    public Date getData_fine() {
        return data_fine;
    }

    public void setData_fine(Date data_fine) {
        this.data_fine = data_fine;
    }

    public int getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(int calorie_da_consumare) {
        this.calorie_da_consumare = calorie_da_consumare;
    }

    public DisponibilitaEnum getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(DisponibilitaEnum disponibilita) {
        this.disponibilita = disponibilita;
    }

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
