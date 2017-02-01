package Object;

import Object.Enum.GiornoEnum;
import Object.Enum.StatusEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public class GiornoAlimEffettivoObject extends GiornoAlimObject {

    private String username;
    private LocalDate data;
    private int cal_assunte;
    private StatusEnum status;

    public GiornoAlimEffettivoObject(String username, LocalDate data, StatusEnum status) {
        this.username = username;
        this.data= data;
        cal_assunte =0;
        tipo= GiornoEnum.effettivo;
        this.status = status;
        pasti = new ArrayList<PastoObject>();
        for (int i=0; i<4; i++)
            pasti.add(i,new PastoObject());
    }

    public GiornoAlimEffettivoObject(String username, LocalDate data, ArrayList<PastoObject> pastigiorno, StatusEnum status){
        this.username = username;
        this.data = data;
        cal_assunte = 0;
        tipo = GiornoEnum.effettivo;
        pasti = pastigiorno;
        this.status = status;
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
        return cal_assunte;
    }

    public void setCalorie(int cal_assunte) {
        this.cal_assunte = cal_assunte;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}

