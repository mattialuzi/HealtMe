package Helpers;


import java.sql.*;
import java.util.*;

public class DbTable {

    protected String name;
    protected String sql;

    public void select(){
        sql="SELECT * FROM " + name;
    }

    public void select(String campo) { sql="SELECT " +campo+ " FROM " + name;}

    public void where(String clausola){
        sql=sql + " WHERE " + clausola;
    }

    public void insert(String campi){

        sql="insert into " + name + " values("+ campi +")";
    }

    public void update(String info){

        sql="update "+ name + " set " + info;
    }

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

    public void delete(){
        sql="delete from "+name;
    }

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