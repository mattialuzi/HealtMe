package View.Riepilogo;

import View.Alimentazione.GiornoAlimForm;
import View.Allenamento.GiornoAllenForm;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * La classe StoriaView contiene attributi e metodi associati al file XML StoriaView.form
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
    private Calendar cal = new GregorianCalendar(Locale.ITALY);
    private GiornoAlimForm giornoalimeff;
    private GiornoAllenForm giornoalleneff;
    private Integer today;
    private boolean flag=false;

    public StoriaView() {
        String[] columns = {"Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"};
        model = new DefaultTableModel(null, columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        giorniTable.setModel(model);
        giorniTable.setDefaultRenderer(Object.class,new MyCellRender());
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
        Calendar nowdate = Calendar.getInstance(Locale.ITALY);
        if (nowdate.get(Calendar.MONTH)==cal.get(Calendar.MONTH) && nowdate.get(Calendar.YEAR)==cal.get(Calendar.YEAR)) {
            today = (nowdate.get(Calendar.DAY_OF_MONTH));
            flag = true;
        }
        else {
            flag = false;
        }

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
        giornoalimeff = new GiornoAlimForm("Cosa hai mangiato in quel giorno");
        alimentarePanel = giornoalimeff.getMainPanel();
        giornoalleneff = new GiornoAllenForm("Cosa hai praticato in quel giorno");
        allenamentoPanel = giornoalleneff.getMainPanel();
    }

    public ArrayList<JTable> getAlimTables(){
        return giornoalimeff.getEffTables();
    }

    public JTable getAllenTable(){
        return giornoalleneff.getSedutaEffTable();
    }

    public void showEffPanels(){
        alimentarePanel.setVisible(true);
        allenamentoPanel.setVisible(true);
    }

    private class MyCellRender extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            c.setBackground(table.getBackground());
            if (value != null) {
                if (value.equals(today) && flag) {
                    c.setBackground(Color.RED);
                }
            }
            return c;

        }
    }
}
