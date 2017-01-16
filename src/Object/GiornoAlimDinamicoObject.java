package Object;

import Object.Enum.PastoEnum;
import Helpers.JObject;
import java.util.Date;


public class GiornoAlimDinamicoObject extends GiornoAlimObject {

    private Date data;
    private int cal_assunte;

    public GiornoAlimDinamicoObject () {

        data=null;
        cal_assunte=0;
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
