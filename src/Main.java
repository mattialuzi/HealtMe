import Controller.PublicController;
import View.Auth;

public class Main {
    public static void main (String[] args) {

        Auth view = new Auth();
        new PublicController(view);
    }


}

