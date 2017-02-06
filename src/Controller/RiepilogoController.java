package Controller;

import Model.GiornoAlimModel;
import View.Menu;
import View.Riepilogo.RiepilogoView;
import View.Riepilogo.StoriaView;
import Object.UtenteObject;
import Object.GiornoAlimEffettivoObject;
import Object.PastoObject;
import Object.PortataObject;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class RiepilogoController {

    private UtenteObject utente;
    private RiepilogoView riepilogo;
    private StoriaView storiaview;



    public RiepilogoController(Menu menu,UtenteObject utente) {

        this.utente = utente;
        this.riepilogo = menu.getRiepilogoview();
        storiaview = riepilogo.getStoria();

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
                if (storiaview.getGiorniTable().getValueAt(row,col) != null) {
                    String giorno = storiaview.getGiorniTable().getValueAt(row,col).toString();
                    String meseanno = storiaview.getDataLabel().getText();
                    String stringofdate = giorno + " " + meseanno;
                    DateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY);
                    try {
                        Date data = format.parse(stringofdate);
                        LocalDate localDate = LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(data));
                        showGiornoAlim(localDate);
                        storiaview.showEffPanels();
                    } catch (ParseException s) {
                        s.printStackTrace();
                    }
                }
            }
        });
    }

    public void showGiornoAlim(LocalDate date){
        ArrayList<JTable> tabelle = storiaview.getTables();
        for (int i=0; i<4; i++){
            DefaultTableModel model = (DefaultTableModel)tabelle.get(i).getModel();
            model.setRowCount(0);
        }
        GiornoAlimEffettivoObject giorno = new GiornoAlimModel().getGiornoAlimEffettivo(utente.getUsername(),date);
        String[] tipipasto = new String[] {"colazione","pranzo","spuntino","cena"};
        for (int i=0; i<4; i++) {
            PastoObject pasto = giorno.getPastoByTipo(tipipasto[i]);
            Iterator<PortataObject> portateiterator = pasto.getPortate().iterator();
            DefaultTableModel model = (DefaultTableModel)tabelle.get(i).getModel();
            while (portateiterator.hasNext()) {
                PortataObject portata = portateiterator.next();
                String tipoportata = String.valueOf(portata.getTipo());
                String alimento = portata.getCibo().getNome();
                int quantita = portata.getQuantita();
                model.addRow(new Object[]{tipoportata, alimento, quantita});
            }
        }
    }
}
