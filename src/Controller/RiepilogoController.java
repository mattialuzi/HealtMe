package Controller;

import View.Menu;
import View.Riepilogo.RiepilogoView;
import View.Riepilogo.StoriaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class RiepilogoController {

    private RiepilogoView riepilogo;

    public RiepilogoController(Menu menu) {
        this.riepilogo = menu.getRiepilogoview();
        StoriaView storiaview = riepilogo.getStoria();

        storiaview.addMesePrecListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storiaview.getCal().add(Calendar.MONTH, -1);
                storiaview.updateMonth();
            }
        });

        storiaview.addMeseSuccListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                storiaview.getCal().add(Calendar.MONTH, +1);
                storiaview.updateMonth();
            }
        });
    }


}
