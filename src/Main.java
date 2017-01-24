import Controller.PublicController;
import View.Auth;

public class Main {
    public static void main (String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Auth view = new Auth();
                new PublicController(view);
            }
        });
    }
}

