package Presenter;
import Helpers.Presenter;
import DAO.CiboDAO;
import Object.Enum.GiornoEnum;
import Object.Enum.PastoEnum;
import Object.Enum.PortataEnum;
import View.Alimentazione.FormCiboEffettivo;
import View.Alimentazione.GiornoAlimView;
import Object.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * La classe BaseAlimPresenter contiene attributi e metodi comuni ad AlimentazionePresenter ed ProgAlimPresenter
 */

public abstract class BaseAlimPresenter extends Presenter {

    protected GiornoAlimEffettivoObject giornoeffcorrente;
    protected String nuovopasto;
    protected FormCiboEffettivo dialog;
    protected ResultSet alimenti;
    protected HashMap<PastoEnum,HashMap<PortataEnum,String[]>> idoneitamap;
    protected int indexoggi;

    /**
     * Metodo che aggiunge il listener alla form di inserimento di un cibo effettivo
     */

    public class ListenersAndShowButtonsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dialog.setLocationRelativeTo(null);
            nuovopasto = e.getActionCommand();
            setPortataItems();
            dialog.setTitle("Inserisci alimento per " + nuovopasto);
            dialog.getButtonOK().setActionCommand(nuovopasto);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    /**
     * Metodo che aggiunge il listener all'inserimento di una portata della form di inserimento di un cibo effettivo
     */

    public class SetPortataItemAction implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getStateChange()== ItemEvent.SELECTED) {
                showAlimenti();
                dialog.getScrollPane().setVisible(true);
                dialog.pack();
            }
        }
    }

    /**
     * Metodo che aggiunge il listener all'inserimento di un carattere della form di inserimento di un cibo effettivo
     */

    public class SearchKeyAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            filtraAlimenti();
        }
    }

    /**
     * Metodo che aggiunge il listener alla selezione di un cibo della form di inserimento di un cibo effettivo
     */

    public class SetCiboListSelectionAction implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting()) {
                dialog.getNomeAlimento().setText(dialog.getListaAlimenti().getSelectedValue().toString());
            }
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaAlimenti().isSelectionEmpty() && !dialog.getQuantita().getText().equals("0"))
                dialog.getButtonOK().setEnabled(true);
            else dialog.getButtonOK().setEnabled(false);
        }
    }

    /**
     * Metodo che aggiunge il listener all'inserimento della quantità di un cibo della form di inserimento di un cibo effettivo
     */

    public class QuantitaKeyAction implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
            if (!checkIntero(dialog.getQuantita().getText())) {
                dialog.getQuantita().setText("");
            }
            if (!dialog.getQuantita().getText().equals("") && !dialog.getListaAlimenti().isSelectionEmpty() && !dialog.getQuantita().getText().equals("0"))
                dialog.getButtonOK().setEnabled(true);
            else dialog.getButtonOK().setEnabled(false);
        }
    }

    /**
     * Metodo che permette di mostrare certe tipi di portate in base al pasto nella form di inserimento di un cibo effettivo
     */

    public void setPortataItems(){
        JComboBox portata = dialog.getPortata();
        dialog.getNomeAlimento().setEnabled(false);
        dialog.getNomeAlimento().setText("");
        dialog.getScrollPane().setVisible(false);
        if (nuovopasto == "colazione" || nuovopasto == "spuntino") {
            portata.setModel(new DefaultComboBoxModel(new String[]{"--scegli portata--","snack", "bevanda", "frutta"}));
        } else {
            portata.setModel(new DefaultComboBoxModel(new String[]{"--scegli portata--","primo", "secondo", "contorno", "dolce", "frutta"}));
        }
    }

    /**
     * Metodo che permette di mostrare gli alimenti nella form di inserimento di un cibo effettivo
     */

    public void showAlimenti(){
        String portatascelta = dialog.getPortata().getSelectedItem().toString();
        JTextField nomeAlimento = dialog.getNomeAlimento();
        dialog.getPortata().removeItem("--scegli portata--");
        if (portatascelta.equals("bevanda"))
            dialog.getMisuraLabel().setText("ml");
        else
            dialog.getMisuraLabel().setText("grammi");
        nomeAlimento.setEnabled(true);
        nomeAlimento.setText("");
        CiboDAO ciboDAO = new CiboDAO();
        alimenti = ciboDAO.getCibiByPortata(portatascelta);
        JList lista = dialog.getListaAlimenti();
        DefaultListModel listmodel = new DefaultListModel();
        try {
            while(alimenti.next()){
                listmodel.addElement(alimenti.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
        lista.setModel(listmodel);
    }

    /**
     * Metodo che permette di filtrare gli alimenti nella form di inserimento di un cibo effettivo
     */

    public void filtraAlimenti(){
        String input = dialog.getNomeAlimento().getText();
        DefaultListModel listafiltrata = new DefaultListModel();
        try {
            alimenti.beforeFirst();
            while(alimenti.next()){
                if(alimenti.getString("nome").contains(input)){
                    listafiltrata.addElement(alimenti.getString("nome"));
                }
            }
            dialog.getListaAlimenti().setModel(listafiltrata);
        } catch (Exception e) {
            System.out.println("C'è un errore:" + e);
        }
    }

    /**
     * Metodo che permette di mostrare tutte le portate di tutti i pasti di un determinato giorno
     * @param giorno Variabile di tipo GiornoAlimObject di cui si vuole mostrare tutti le portate di tutti i pasti
     * @param giornoview Variabile della classe view GiornoAlimView su di cui si vogliono mostrare le informazioni
     */

    protected void showPasti(GiornoAlimObject giorno, GiornoAlimView giornoview) {
        ArrayList<JTable> tabelle = giornoview.getTables(giorno.getTipo());
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

    /**
     * Metodo che permette di rimuovere  tutte le portate di tutti le tabelle pasti
     * @param giornoview Variabile della classe view GiornoAlimView da cui prendere le tabelle
     * @param tipogiorno Tipo del giorno di cui rimuovere le portate
     */

    protected void removePasti(GiornoAlimView giornoview, GiornoEnum tipogiorno){
        ArrayList<JTable> tabelle = giornoview.getTables(tipogiorno);
        for (int i=0; i<4; i++){
            DefaultTableModel model = (DefaultTableModel)tabelle.get(i).getModel();
            model.setRowCount(0);
        }
    }

    /**
     * Metodo che permette a di calcolare le calorie a partire da una variabile PortataObject
     * @param portata Variabile di tipo PortataObject di cui si vuole calcolare le calorie
     * @return Il valore delle calorie
     */

    protected int calcolaCalorie(PortataObject portata){
        return portata.getQuantita()*(portata.getCibo().getKilocal())/100;
    }

    /**
     * Metodo che calcola la quantità di un cibo a partire da calorie e kcal di un cibo
     * @param calorie Calorie necessarie per la quantità da calcolare
     * @param kilocal Kcal del cibo
     * @return Il valore della quantità
     */

    protected int calcolaQuantita(int calorie, int kilocal){
        return (calorie*100)/kilocal;
    }

    /**
     * Metodo che riempie un HashMap la cui chiave è un enumerativo di portata e il valore è un array di stringhe
     */

    protected void generateIdoneitaMap() {
        if (idoneitamap == null) {
            idoneitamap = new HashMap<>();
            HashMap<PortataEnum, String[]> colazionemap = new HashMap<>();
            colazionemap.put(PortataEnum.snack, new String[]{"colazione", "colazione_spuntino"});
            colazionemap.put(PortataEnum.bevanda, new String[]{"colazione", "colazione_spuntino", "tutti"});
            colazionemap.put(PortataEnum.frutta, new String[]{"tutti"});
            idoneitamap.put(PastoEnum.colazione, colazionemap);
            HashMap<PortataEnum, String[]> pranzomap = new HashMap<>();
            pranzomap.put(PortataEnum.primo, new String[]{"pranzo", "pranzo_cena"});
            pranzomap.put(PortataEnum.secondo, new String[]{"pranzo_cena"});
            pranzomap.put(PortataEnum.contorno, new String[]{"pranzo_cena"});
            pranzomap.put(PortataEnum.frutta, new String[]{"tutti"});
            pranzomap.put(PortataEnum.dolce, new String[]{"pranzo_cena"});
            idoneitamap.put(PastoEnum.pranzo, pranzomap);
            HashMap<PortataEnum, String[]> spuntinomap = new HashMap<>();
            spuntinomap.put(PortataEnum.snack, new String[]{"spuntino", "colazione_spuntino"});
            spuntinomap.put(PortataEnum.bevanda, new String[]{"spuntino", "colazione_spuntino", "tutti"});
            spuntinomap.put(PortataEnum.frutta, new String[]{"tutti"});
            idoneitamap.put(PastoEnum.spuntino, spuntinomap);
            HashMap<PortataEnum, String[]> cenamap = new HashMap<>();
            cenamap.put(PortataEnum.primo, new String[]{"cena", "pranzo_cena"});
            cenamap.put(PortataEnum.secondo, new String[]{"pranzo_cena"});
            cenamap.put(PortataEnum.contorno, new String[]{"pranzo_cena"});
            cenamap.put(PortataEnum.frutta, new String[]{"tutti"});
            idoneitamap.put(PastoEnum.cena, cenamap);
        }
    }

    /**
     * Metodo che ottiene il valore di un componenete dell'HashMap in base al valore della chiave
     * @param pasto Tipo enumerativo del pasto
     * @param portata Valore della chiave da cui si estapola l'elemento dell'HashMap
     * @return Array di stringa
     */

    protected String[] getIdoneita (PastoEnum pasto, PortataEnum portata) {
        return idoneitamap.get(pasto).get(portata);
    }

}