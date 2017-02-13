package Object;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * La classe ProgressiObject mappa la tabella "progressi" del database
 */

public class ProgressiObject {

    private String username;
    private ArrayList<LocalDate> date = new ArrayList<>();
    private ArrayList<Double> pesi = new ArrayList<>();
    private ArrayList<Double> calorie_assunte = new ArrayList<>();
    private ArrayList<Double> fabbisogni = new ArrayList<>();
    private ArrayList<Double> calorie_consumate = new ArrayList<>();
    private ArrayList<Double> calorie_da_consumare = new ArrayList<>();

    public ProgressiObject (String username) {
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<LocalDate> getData() {
        return date;
    }

    public void setDate(LocalDate data) {
        date.add(data);
    }

    public ArrayList<Double> getPesi() {
        return pesi;
    }

    public void setPesi(double peso) {
        pesi.add(peso);
    }

    public ArrayList<Double> getCalorie_assunte() {
        return calorie_assunte;
    }

    public void setCalorie_assunte(double calorie_assunte) {
        this.calorie_assunte.add(calorie_assunte);
    }

    public ArrayList<Double> getFabbisogno() {
        return fabbisogni;
    }

    public void setFabbisogno(double fabbisogno) {
        fabbisogni.add(fabbisogno);
    }

    public ArrayList<Double> getCalorie_consumate() {
        return calorie_consumate;
    }

    public void setCalorie_consumate(double calorie_consumate) {
        this.calorie_consumate.add(calorie_consumate);
    }

    public ArrayList<Double> getCalorie_da_consumare() {
        return calorie_da_consumare;
    }

    public void setCalorie_da_consumare(Double calorie_da_consumare) {
        this.calorie_da_consumare.add(calorie_da_consumare);
    }
}
