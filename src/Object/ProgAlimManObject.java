package Object;

import Object.Enum.AlimentazioneEnum;

import java.util.ArrayList;

/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class ProgAlimManObject extends ProgrammaAlimentareObject {

    public ProgAlimManObject () {
        settimanaalimentare = new ArrayList<GiornoAlimProgObject>();
        for (int i=0; i<7; i++)
            settimanaalimentare.add(i, new GiornoAlimProgObject());
    }

    public ProgAlimManObject(ArrayList<GiornoAlimProgObject> giorniprogrammati) {
        settimanaalimentare = giorniprogrammati;
    }

}
