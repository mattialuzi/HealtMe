package Model;


import Model.Dbtable.Utente;
import Object.UtenteObject;
import java.lang.reflect.Field;

public class UtenteModel {
    protected Utente tabella;

    public UtenteModel() {
        tabella= new Utente();
    }

    public String valueString(Object o){
        Class<?> classe = o.getClass();
        String temp_name = "";
        String temp_value="";
        String total="";
        int i=0;
        for(Field field : classe.getDeclaredFields()) {
            temp_name=field.getName();
            try{
                Field f = classe.getDeclaredField(temp_name);
                f.setAccessible(true);
                temp_value = f.get(o).toString();
                if (temp_value=="false") {
                    temp_value = "0";
                }
                else if (temp_value=="true") {
                    temp_value = "1";
                }
            }
            catch(Exception e){
                temp_value="VOID";
            }
            if (i==0){
                total="'"+temp_value+"'";
            }
            else{
                total = total + ", '" + temp_value+"'";
            }
            i++;
        }
        return total;
    }

    public void inserisciUtente(UtenteObject utente){
        String dati= valueString(utente);
        tabella.insert(dati);
        tabella.execute();
    }

    public boolean getUserByUsername(String user){
        boolean success=false;
        tabella.select();
        tabella.where("username='" + user + "'");
        int n = tabella.count(tabella.fetch());
        if (n==1)
            success=true;
        else
            success=false;
        return success;
    }

    public boolean getUserByCredential(String user, String pass){
        boolean success = false;
        tabella.select();
        tabella.where("username='" + user + "' and password='" + pass+"'");
        int n = tabella.count(tabella.fetch());
        if(n==1)
            success = true;
        else
            success=false;
        return success;
    }
}
