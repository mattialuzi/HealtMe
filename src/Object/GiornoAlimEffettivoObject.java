package Object;

import java.util.Date;

/**
 * Created by Mattia on 16/01/2017.
 */
public class GiornoAlimEffettivoObject extends GiornoAlimObject {

    private String username;
    private Date data;
    private int cal_assunte;

    public GiornoAlimEffettivoObject() {
        data=null ;
        cal_assunte =0;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getCal_assunte() {
        return cal_assunte;
    }

    public void setCal_assunte(int cal_assunte) {
        this.cal_assunte = cal_assunte;
    }
}

