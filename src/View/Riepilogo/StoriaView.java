package View.Riepilogo;

import View.Alimentazione.GiornoAlimForm;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
    private JPanel alimentarePanel;
    private JPanel allenamentoPanel;
    private JScrollPane tableScrollPane;
    private DefaultTableModel model;
    private Calendar cal = new GregorianCalendar();
    private GiornoAlimForm giornoalimeff;

    public StoriaView() {
        String[] columns = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"};
        model = new DefaultTableModel(null, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
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

    public JLabel getDataLabel() {
        return dataLabel;
    }

    public Calendar getCal() {
        return cal;
    }

    public JTable getGiorniTable() {
        return giorniTable;
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

    public void addMouseListener(MouseAdapter adapter){
        giorniTable.addMouseListener(adapter);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        giornoalimeff = new GiornoAlimForm("Cosa hai mangiato in quel giorno");
        alimentarePanel = giornoalimeff.getMainPanel();
    }

    public ArrayList<JTable> getTables(){
        return giornoalimeff.getEffTables();
    }

    public void showEffPanels(){
        alimentarePanel.setVisible(true);
        allenamentoPanel.setVisible(true);
    }
}
