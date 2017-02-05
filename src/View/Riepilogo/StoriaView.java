package View.Riepilogo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by lorenzobraconi on 04/02/17.
 */
public class StoriaView {
    private JPanel mainPanel;
    private JButton precButton;
    private JButton succButton;
    private JTable giorniTable;
    private JLabel dataLabel;
    private DefaultTableModel model;
    private Calendar cal = new GregorianCalendar();

    public StoriaView() {
        String[] columns = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"};
        model = new DefaultTableModel(null, columns);
        giorniTable.setModel(model);
        updateMonth();
    }

    public void addMesePrecListener(ActionListener listener){
        precButton.addActionListener(listener);
    }

    public void addMeseSuccListener(ActionListener listener){
        succButton.addActionListener(listener);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Calendar getCal() {
        return cal;
    }

    public void updateMonth() {
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ITALY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int year = cal.get(Calendar.YEAR);
        dataLabel.setText(month + " " + year);

        int startDay = cal.get(Calendar.DAY_OF_WEEK);
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        startDay = (5+startDay)%7;
        int weeks = calcolaSettimaneMese(numberOfDays, startDay);

        model.setRowCount(0);
        model.setRowCount(weeks);

        int i = startDay;
        for (int day = 1; day <= numberOfDays; day++) {
            model.setValueAt(day, i / 7, i % 7);
            i++;
        }
    }

    public int calcolaSettimaneMese(int numerogiorni, int startday){
        int i = 7 - startday;
        int j = numerogiorni - i;
        int k = j % 7;
        int risultato = j/7;
        if (k!=0) risultato++;
        if (i != 0) risultato++;
        return risultato;
    }
}
