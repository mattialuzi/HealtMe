package Helpers;

import javax.swing.*;

/**
 * La classe View estende ogni classe view e contiene metodi comuni a tutte le classi
 */

public class View {

    private JPanel panel;

    public View()
    {
        panel= new JPanel();
    }

    /**
     * Metodo che elimina tutti gli spazi finali di un testo presente in un TextBox
     * @param elemento Stringa di cui si vuole eliminare gli spazi finali
     * @return La stringa passata come parametro esclusa degli spazi finali
     */

    public String deleteLastSpace(String elemento)
    {
        return elemento.replaceAll("\\s+$", "");
    }

    /**
     * Metodo che permette verificare se un elemento rispetta il corretto pattern
     * @param elemento Stringa che si vuole verificare
     * @param tipo Pattern che si vuole utilizzare per verificare
     * @return true se l'elemento rispetta il pattern , false se non lo rispetta
     */

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
        if(tipo.equals("eserciziocibo"))
            res = checkEsercizioCibo(elemento);
        return res;
    }

    /**
     * Metodo che implementa il pattern corretto per stringhe di testo
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkTesto(String elemento){
        boolean ritorno = false;
        String regola = "[A-Za-z]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per stringhe di tipo password
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkPass(String elemento){
        boolean ritorno = false;
        String regola = "[A-za-z0-9_\\!\\$]{6,}";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per numeri interi
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkIntero(String elemento){
        boolean ritorno = false;
        String regola = "[1-9][0-9]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per numeri che rappresentano età
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkEta(String elemento){
        boolean ritorno = false;
        String regola = "[1-9][0-9]";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per numeri con la virgola
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkVirgola(String elemento){
        boolean ritorno = false;
        String regola = "[0-9]+[\\.][0-9]+";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per le date
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkData(String elemento){
        boolean ritorno = false;
        String regola = "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per stringhe che rappresentano email
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkEmail(String elemento){
        boolean ritorno = false;
        String regola = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che implementa il pattern corretto per stringhe di nomi di cibi e esercizi
     * @param elemento Elemento da verificare
     * @return True se lo rispetta, false se non lo rispetta
     */

    public boolean checkEsercizioCibo(String elemento){
        boolean ritorno = false;
        String regola = "[A-Za-z\\s]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Metodo che permette il calcolare il peso forma dati altezza e sesso
     * @param altezza altezza della persona di cui si vuole calcolare il peso forma
     * @param sesso sesso della persona di cui si vuole calcolare il peso forma
     * @return il valore del peso forma di tipo float
     */

    public double calcoloPesoForma(double altezza, int sesso){
        double peso_forma;
        if(sesso == 1) {
            peso_forma = 22.1 * altezza * altezza;
        }
        else {
            peso_forma = 20.6 * altezza * altezza;
        }
        return peso_forma;
    }
}
