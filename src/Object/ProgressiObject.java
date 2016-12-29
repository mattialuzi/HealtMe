package Object;

import java.util.Date;

public class ProgressiObject {
    private String username;
    private Date data;
    private float peso;
    private int calorie_assunte;
    private int calorie_consumate;
    private int fabbisogno;

    public ProgressiObject () {
        username=null;
        data=null;
        peso=0;
        calorie_assunte=0;
        calorie_consumate=0;
        fabbisogno=0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getCalorie_assunte() {
        return calorie_assunte;
    }

    public void setCalorie_assunte(int calorie_assunte) {
        this.calorie_assunte = calorie_assunte;
    }

    public int getCalorie_consumate() {
        return calorie_consumate;
    }

    public void setCalorie_consumate(int calorie_consumate) {
        this.calorie_consumate = calorie_consumate;
    }

    public int getFabbisogno() {
        return fabbisogno;
    }

    public void setFabbisogno(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }
}
