package Object;

import Helpers.JObject;
/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class GiornoAlimProgObject extends GiornoAlimObject {

    private int fabbisogno;

    public GiornoAlimProgObject() {
        fabbisogno=0;
    }

    public int getFabbisogno() {
        return fabbisogno;
    }

    public void setFabbisogno(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }
}
