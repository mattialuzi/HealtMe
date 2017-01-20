package Object;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public class GiornoAlimEffettivoObject extends GiornoAlimObject {

    private String username;
    private LocalDate data;
    private int cal_assunte;

    public GiornoAlimEffettivoObject(String username, LocalDate data) {
        this.username = username;
        this.data= data;
        cal_assunte =0;
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

    public int getCal_assunte() {
        return cal_assunte;
    }

    public void setCal_assunte(int cal_assunte) {
        this.cal_assunte = cal_assunte;
    }
}

