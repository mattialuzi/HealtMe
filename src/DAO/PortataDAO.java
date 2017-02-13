package DAO;

import DAO.Dbtable.Portata;
import Object.Enum.PortataEnum;
import Object.PortataObject;
import Object.CiboObject;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * La classe PortataDAO contiene i metodi per la gestione dei dati della tabella "portata" del database.
 */

public class PortataDAO {

    protected Portata tabella;

    public PortataDAO() {
        tabella = new Portata();
    }

    /**
     * Metodo che recupera tutte le portate di un pasto in base al codice
     * @param idpasto Codice del pasto
     * @return ArrayList di PortateObject
     */

    public ArrayList<PortataObject> getPortateById(int idpasto){
        tabella.select();
        tabella.where("id_pasto ='" + idpasto + "'");
        tabella.order("tipo");
        ResultSet rs = tabella.fetch();
        ArrayList<PortataObject> portate = new ArrayList<PortataObject>();
        try{
            while(rs.next()){
                CiboDAO ciboDAO = new CiboDAO();
                CiboObject cibo = ciboDAO.getCiboByName(rs.getString("cibo"));
                PortataObject portata = new PortataObject(cibo);
                portata.setId_pasto(rs.getInt("id_pasto"));
                portata.setTipo(PortataEnum.valueOf(rs.getString("tipo")));
                portata.setQuantita(rs.getInt("quantita"));
                portate.add(portata);
            }
        } catch (Exception e){
            System.out.println("Errore:" + e);
        }
        return portate;
    }

    /**
     * Metodo che inserisce una nuova portata a partire da una PortataObject
     * @param portata Variabile di tipo PortataObject il cui valore degli attributi costituiscono i dati da inserire
     */

    public void inserisciPortata (PortataObject portata) {
        String dati= ""+portata.getId_pasto()+"";
        dati=dati+",'"+String.valueOf(portata.getCibo().getNome())+"'";
        dati=dati+",'"+String.valueOf(portata.getTipo())+"'";
        dati=dati+", "+String.valueOf(portata.getQuantita())+"";
        tabella.insert(dati);
        tabella.execute();
    }

    /**
     * Metodo che modifica la quantita di una portata
     * @param id_pasto Codice del pasto
     * @param cibo Nome del cibo
     * @param quantita Valore della nuova quantità
     */

    public  void updatePortata (int id_pasto, String cibo, int quantita) {
        String dati= "quantita = "+quantita;
        tabella.update(dati);
        tabella.where("id_pasto='" + id_pasto + "' AND cibo='" + cibo + "'");
        tabella.execute();
    }

    /**
     * Metodo che modifica la quantita e il nome di un cibo di una portata
     * @param id_pasto Codice del pasto
     * @param vecchiocibo Vecchio nome del cibo
     * @param nuovocibo Nuovo nome del cibo
     * @param quantita Valore della nuova quantità
     */

    public  void updatePortata (int id_pasto, String vecchiocibo, String nuovocibo, int quantita) {
        String dati= "quantita = " + quantita + " , cibo = '" + nuovocibo + "'";
        tabella.update(dati);
        tabella.where("id_pasto='" + id_pasto + "' AND cibo='" + vecchiocibo + "'");
        tabella.execute();
    }

    /**
     * Metodo che elimina una portata in base al codice del pasto ed il cibo
     * @param id_pasto Codice del pasto
     * @param cibo Nome del cibo da eliminare
     */

    public void eliminaPortata (int id_pasto, String cibo) {
        tabella.delete();
        tabella.where("id_pasto='" + id_pasto + "' AND cibo='" + cibo + "'");
        tabella.execute();
    }
}
