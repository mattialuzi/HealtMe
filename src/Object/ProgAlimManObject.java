package Object;

import Object.Enum.AlimentazioneEnum;
import java.util.ArrayList;

/**
 * La classe ProgAlimManObject mappa la tabella "prog_alim_man" del database
 */

public class ProgAlimManObject extends ProgrammaAlimentareObject {

    public ProgAlimManObject () {
        tipo_alimentazione = AlimentazioneEnum.manuale;
        settimanaalimentare = new ArrayList<GiornoAlimProgObject>();
        for (int i=0; i<7; i++)
            settimanaalimentare.add(i, new GiornoAlimProgObject());
    }

    public ProgAlimManObject(ArrayList<GiornoAlimProgObject> giorniprogrammati) {
        tipo_alimentazione = AlimentazioneEnum.manuale;
        settimanaalimentare = giorniprogrammati;
    }

}
