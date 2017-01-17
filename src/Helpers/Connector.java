package Helpers;

import javax.swing.*;
import java.sql.*;
/**
 * Created by ALLDE on 28/12/2016.
 */
public class Connector {
    private Connection conn;

    public Connection connect()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            this.conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/healthme?user='root'");
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"Connessione non riuscita. Controlla che MySQL sia attivo!","ERRORE NELLA CONNESSIONE",JOptionPane.ERROR_MESSAGE);
        }
        return conn;
    }

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
