import Controller.PublicController;
import Helpers.Connector;

import java.awt.*;
import java.sql.Connection;

import Object.Enum.AllergiaEnum;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.UtenteObject;
import Model.UtenteModel;
import View.Auth;

import javax.swing.*;

public class Main {
    public static void main (String[] args) {
        //JFrame finestra = new JFrame("Health me!");
        Auth view = new Auth();

        new PublicController(view);
    }


}

