package Object;

import java.util.ArrayList;
import java.util.Date;

public class ProgAllenManObject extends ProgrammaAllenamentoObject {

    public ProgAllenManObject () {
        settimanaallenamento = new ArrayList<GiornoAllenProgObject>();
        for (int i=0; i<7; i++)
            settimanaallenamento.add(i, new GiornoAllenProgObject());
    }

    public ProgAllenManObject(ArrayList<GiornoAllenProgObject> giorniprogrammati) {
        settimanaallenamento = giorniprogrammati;
    }

}
