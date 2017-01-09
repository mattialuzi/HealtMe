package Controller;

import Object.UtenteObject;
import View.Profilo.ProfiloView;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class ProfiloController {

    public ProfiloController(ProfiloView profilo, UtenteObject utente) {

        profilo.setInfoUtente(utente);
    }
}
