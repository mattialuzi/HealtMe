import Controller.PublicController;
import Helpers.Connector;

import java.sql.Connection;

import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import Model.UtenteModel;

import javax.swing.*;

public class Main {
    public static void main (String[] args) {

        /* UtenteModel tabella= new UtenteModel();
        tabella.inserisciUtente(utente); */

        JFrame finestra = new JFrame("Health me!");
        PublicController  Public = new PublicController(finestra);
        Public.indexAction();
    }


}

