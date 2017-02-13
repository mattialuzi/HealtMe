import Presenter.PublicPresenter;
import View.Auth;

/**
 * Classe pricipale da cui parte viene avviata l'applicazione
 */

public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Auth view = new Auth();
                new PublicPresenter(view);
            }
        });
    }
}

