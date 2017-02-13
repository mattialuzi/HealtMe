package Helpers;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * La classe Presenter estente ogni file del package Presenter e contiene dei metodi comuni a tutti i file
 */

public class Presenter {

    public Presenter() {
    }

    /**
     * Il metodo è responsabile della visibilità del menu laterale di accesso alle varie sezioni di Health Me!
     * @param menuPanel Pannello del menu laterale
     */

    public void MenuVisibility(JPanel menuPanel){
        if (menuPanel.isVisible()) {
            menuPanel.setVisible(false);
        } else
            menuPanel.setVisible(true);
    }

    /**
     * Il metodo è responsabile della visibilità del sotto menu laterale delle sezioni di Alimentazione e Allenamento
     * @param subPanel Sotto Pannelli della sezione
     */

    public void SubMenuVisibility(JPanel subPanel){
        if (subPanel.isVisible()) {
            subPanel.setVisible(false);
        } else
            subPanel.setVisible(true);
    }

    /**
     * Il metodo verifica se la stringa presa come parametro rispetta la regola definita
     * @param elemento Stringa di cui si vuole verificare la validità
     * @return true se rispetta la regola, false se non la rispetta
     */

    public boolean checkIntero(String elemento){
        boolean ritorno = false;
        String regola = "[0-9]*";
        if (elemento.matches(regola) && elemento.length()>0)
            ritorno=true;
        return ritorno;
    }

    /**
     * Il metodo estrae un numero casuale tra 0 e un numero preso come parametro
     * @param size Limite massimo da cui estrapolare il numero casuale
     * @return Un numero casuale tra 0 e size
     */

    protected int randomize(int size){
        return ThreadLocalRandom.current().nextInt(0, size);
    }
}
