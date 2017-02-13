package Helpers;

import java.sql.*;

/**
 * La classe DbTable estente ogni file del package Dbtable e contiene tutte le funzioni principali che permettono di interagire con il DBMS
 */

public class DbTable {

    protected String name;
    protected String sql;

    /** Metodi DML (insert, update , delete)  */

    public void insert(String campi){

        sql="insert into " + name + " values("+ campi +")";
    }

    public void update(String info){

        sql="update "+ name + " set " + info;
    }

    public void delete(){
        sql="delete from "+name;
    }

    /**Metodi select*/

    /** Seleziono tutte le tuple della tabella */

    public void select(){
        sql="SELECT * FROM " + name;
    }

    /** Seleziono solo alcuni campi della tabella */

    public void select(String campo) { sql="SELECT " +campo+ " FROM " + name;}

    /**Metodi per il completamento della query*/

    /** Aggiungo alla query la clausola where */

    public void where(String clausola){
        sql=sql + " WHERE " + clausola;
    }

    /** Aggiungo alla query la clausola order by */

    public void order(String clausola){
        sql = sql + "ORDER BY " + clausola;
    }

    /** Metodi di esecuzione della query */

    public int count(ResultSet risultato){
        int i=0;
        try {
            while (risultato.next()) {
                i++;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return i;
    }

    /** Utilizzata per l'esecuzione di una query di tipo insert che non contiene un attributo di tipo AUTO_INCREMENT nel database */

    public boolean execute(){
        Connector connector= new Connector();
        Connection db = connector.connect();
        boolean check = false;
        sql = sql + ";";
        try {
            PreparedStatement query = db.prepareStatement(sql);
            query.executeUpdate();
            check=true;
        } catch (Exception e) {
            System.out.println(e);
            check=false;
        }
        connector.disconnect();
        return check;
    }

    /** Utilizzata per l'esecuzione di una query di tipo insert che contiene un attributo di tipo AUTO_INCREMENT nel database */

    public int executeForKey(){
        Connector connector= new Connector();
        Connection db = connector.connect();
        sql = sql + ";";
        int generated_key = 0;
        try {
            Statement query = db.createStatement();
            query.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = query.getGeneratedKeys();
            try {
                rs.next();
                generated_key = rs.getInt("GENERATED_KEY");

            } catch (Exception e) {
                System.out.println("C'è un errore:" + e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        connector.disconnect();
        return generated_key;
    }

    /**Utilizzata per l'esecuzione di query da cui si deve estrapolare dati dal database */

    public ResultSet fetch(){

        ResultSet risultato=null;
        Connector connector= new Connector();
        Connection db = connector.connect();
        sql = sql + ";";
        try {
            Statement query = db.createStatement();
            risultato=query.executeQuery(sql);
        } catch (Exception e) {
            System.out.println(e);
        }
        return risultato;

    }

}