import Helpers.Connector;

import java.sql.Connection;

import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import Model.UtenteModel;

public class Main {
    public static void main (String[] args) {
        UtenteObject utente= new UtenteObject();
        utente.setNome("lorenzo");
        utente.setCognome("braconi");
        utente.setAltezza(190);
        utente.setEmail("lore@gmail.com");
        utente.setAllergia(AllergiaEnum.pesce);
        utente.setEta(22);
        utente.setLavoro(LavoroEnum.leggero);
        utente.setLivello_attivita_fisica(LivelloAttivitaFisicaEnum.leggero);
        utente.setPeso(71);
        utente.setSesso(1);
        utente.setUsername("mattialuzi");
        utente.setPassword("pass");
        utente.setProg_alim_comb(false);
        utente.setProg_allen_comb(false);
        utente.setPeso_forma(80);
        UtenteModel tabella= new UtenteModel();
        tabella.inserisciUtente(utente);
    }

    // aggiunta una riga di prova lore

}
