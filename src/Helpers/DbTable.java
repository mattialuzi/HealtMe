package Helpers;


import java.sql.*;
import java.util.*;

public class DbTable {

    protected String name;
    protected String sql;

    public void insert(String campi){
        sql="insert into " + name + " values("+ campi +")";
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

}