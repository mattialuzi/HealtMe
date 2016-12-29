import Helpers.Connector;

import java.sql.Connection;

public class Main {
    public static void main (String[] args) {
        Connector Connessione = new Connector();
        Connection i = Connessione.connect();
    }
}
