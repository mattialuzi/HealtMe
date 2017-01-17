package Object;

import Helpers.JObject;
/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class GiornoAlimProgObject extends GiornoAlimObject {

    private int id_giorno;
    private int fabbisogno;

    public GiornoAlimProgObject() {
        id_giorno=0;
        fabbisogno=0;
    }

    public int getId_giorno() {
        return id_giorno;
    }

    public void setId_giorno(int id_giorno) {
        this.id_giorno = id_giorno;
    }

    public int getFabbisogno() {
        return fabbisogno;
    }

    public void setFabbisogno(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }
}
