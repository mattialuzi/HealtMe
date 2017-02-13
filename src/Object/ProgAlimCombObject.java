package Object;

import Object.Enum.AlimentazioneEnum;
import java.util.ArrayList;

/**
 * La classe ProgAlimCombObject mappa la tabella "prog_alim_comb" del database
 */


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
