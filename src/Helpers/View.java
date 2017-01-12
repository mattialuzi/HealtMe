package Helpers;

import javax.swing.*;

public class View {

    private JPanel panel;

    public View()
    {
        panel= new JPanel();
    }

    //elimina lo spazio alla fine della stringa
    public String deleteLastSpace(String elemento)
    {
        return elemento.replaceAll("\\s+$", "");
    }

    public boolean validate(String elemento, String tipo){
        boolean res = false;
        if(tipo.equals("testo"))
            res = checkTesto(elemento);
        if(tipo.equals("password"))
            res = checkPass(elemento);
        if(tipo.equals("intero"))
            res = checkIntero(elemento);
        if(tipo.equals("eta"))
            res = checkEta(elemento);
        if(tipo.equals("virgola"))
            res = checkVirgola(elemento);
        if(tipo.equals("data"))
            res = checkData(elemento);
        if(tipo.equals("email"))
            res = checkEmail(elemento);
        if(tipo.equals("cibo"))
            res = checkCibo(elemento);
        return res;
    }

    public boolean checkTesto(String elemento){
        boolean ritorno = false;
        String regola = "[A-Za-z]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkPass(String elemento){
        boolean ritorno = false;
        String regola = "[A-za-z0-9_\\!\\$]{6,}";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkIntero(String elemento){
        boolean ritorno = false;
        String regola = "[0-9]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkEta(String elemento){
        boolean ritorno = false;
        String regola = "[1-9][0-9]";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkVirgola(String elemento){
        boolean ritorno = false;
        String regola = "[0-9]+[\\.][0-9]+";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkData(String elemento){
        boolean ritorno = false;
        String regola = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkEmail(String elemento){
        boolean ritorno = false;
        String regola = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public boolean checkCibo(String elemento){
        boolean ritorno = false;
        String regola = "[A-Za-z\\s]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    public float calcoloPesoForma(float altezza, int sesso){
        float peso_forma;
        //per indicare un numero float si mette una f alla fine
        if(sesso == 1) {
            peso_forma = 22.1f * altezza * altezza;
        }
        else {
            peso_forma = 20.6f * altezza * altezza;
        }
        return peso_forma;
    }
}
