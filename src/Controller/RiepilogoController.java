package Controller;

import View.Menu;
import View.Riepilogo.RiepilogoView;
import View.Riepilogo.StoriaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        storiaview.getGiorniTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = storiaview.getGiorniTable().rowAtPoint(evt.getPoint());
                int col = storiaview.getGiorniTable().columnAtPoint(evt.getPoint());
                String giorno = storiaview.getGiorniTable().getValueAt(row,col).toString();
                if (!giorno.equals("")) {
                    String meseanno = storiaview.getDataLabel().getText();
                    String stringofdate = giorno + " " + meseanno;
                    DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY);
                    try {
                        Date data = format.parse(stringofdate);
                    } catch (ParseException s) {
                        s.printStackTrace();
                    }
                }
            }
        });
    }
}
