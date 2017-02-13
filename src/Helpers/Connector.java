package Helpers;

import javax.swing.*;
import java.sql.*;

/**
 * La classe Connector è responsabile di creare, aprire, chiudere connessioni tra Health ME! e il database
 */

public class Connector {
    private Connection conn;
    private String user = "root";
    private String password = "";
    private String dbname = "healthme";

    /**
     * Metodo che stabilisce una connessione al database
     * @return La sessione di connessione con il database
     */

    public Connection connect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.dbname,this.user,this.password);
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Connessione non riuscita. Controlla che MySQL sia attivo!","ERRORE NELLA CONNESSIONE",JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

    /**
     * Metodo che chiude la connessione al database
     */

    public void disconnect(){
        try {
            conn.close();
        }
        catch(Exception e) {
            System.out.println(e);
        }
        return;
    }
}
