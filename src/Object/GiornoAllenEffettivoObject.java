package Object;

import java.util.Date;

/**
 * Created by lorenzobraconi on 17/01/17.
 */
public class GiornoAllenEffettivoObject extends GiornoAllenObject{

    private String username;
    private Date data;
    private int cal_consumate;

    public GiornoAllenEffettivoObject() {
        username=null;
        data=null ;
        cal_consumate =0;
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

    public int getCal_consumate() {
        return cal_consumate;
    }

    public void setCal_consumate(int cal_consumate) {
        this.cal_consumate = cal_consumate;
    }
    
}
