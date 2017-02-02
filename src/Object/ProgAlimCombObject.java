package Object;

import Object.Enum.AlimentazioneEnum;
import View.Alimentazione.AlimentazioneView;

import java.util.ArrayList;


public class ProgAlimCombObject extends ProgrammaAlimentareObject {

    private int fabbisogno;


    public ProgAlimCombObject () {

        fabbisogno=0;
        tipo_alimentazione=null;
        settimanaalimentare = new ArrayList<GiornoAlimProgObject>();
        for (int i=0; i<7; i++)
            settimanaalimentare.add(i, new GiornoAlimProgObject());
    }

    public ProgAlimCombObject(ArrayList<GiornoAlimProgObject> giorniprogrammati, int fabbisogno, AlimentazioneEnum tipoalim) {
        this.fabbisogno=fabbisogno;
        this.tipo_alimentazione=tipoalim;
        settimanaalimentare = giorniprogrammati;
    }

    public int getFabbisogno() {
        return fabbisogno;
    }

    public void setFabbisogno(int fabbisogno) {
        this.fabbisogno = fabbisogno;
    }

}
