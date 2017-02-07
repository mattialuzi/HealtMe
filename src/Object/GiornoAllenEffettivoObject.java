package Object;

import Object.Enum.GiornoEnum;

import java.time.LocalDate;

/**
 * Created by lorenzobraconi on 17/01/17.
 */
public class GiornoAllenEffettivoObject extends GiornoAllenObject{

    private String username;
    private LocalDate data;
    private int cal_consumate;

    public GiornoAllenEffettivoObject(String username, LocalDate data) {
        this.username = username;
        this.data= data;
        cal_consumate =0;
        tipo= GiornoEnum.effettivo;
        seduta = new SedutaObject();
    }

    public GiornoAllenEffettivoObject(String username, LocalDate data, SedutaObject seduta){
        this.username = username;
        this.data = data;
        cal_consumate = 0;
        tipo = GiornoEnum.effettivo;
        this.seduta = seduta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getCalorie() {
        return cal_consumate;
    }

    public void setCalorie(int cal_consumate) {
        this.cal_consumate = cal_consumate;
    }
    
}
