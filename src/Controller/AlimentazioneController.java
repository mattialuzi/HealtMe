package Controller;

import Helpers.Controller;
import Model.CiboModel;
import View.Alimentazione.*;
import View.Menu;
import Object.CiboObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by lorenzobraconi on 05/01/17.
 */
public class AlimentazioneController extends Controller {

    private AlimentazioneView alimentazione;
    private FormCiboEffettivo dialog;
    private CardLayout cardLayout = new CardLayout();
    private JPanel variablePanel;

    public AlimentazioneController(Menu menu) {

        this.alimentazione = menu.getAlimentazioneview();
        variablePanel = alimentazione.getMainPanel();
        cardLayout = (CardLayout)variablePanel.getLayout();
        showIndex();
        NewCiboView newcibo = alimentazione.getNewcibo();
        IndexAlimentazioneView indexalimentazione = alimentazione.getIndexalimentazione();
        dialog = new FormCiboEffettivo();

        menu.addNewProgAlimButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAlimPanel());
                cardLayout.show(variablePanel, "NewProgAlimView");
            }
        });

        menu.addNewCiboButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubMenuVisibility(menu.getSubMenuAlimPanel());
                cardLayout.show(variablePanel, "NewCiboView");
            }
        });

        newcibo.addAzzeraCampiButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newcibo.azzeraCampi();
            }
        });

        newcibo.addAggiungiAlimentoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newcibo.isValid()) {
                    CiboObject nuovocibo = newcibo.getNuovoCibo();
                    CiboModel cibomodel = new CiboModel();
                    boolean validator = cibomodel.findCiboByName(nuovocibo.getNome());
                    if (validator) {
                        JOptionPane.showMessageDialog(null, "Cibo già esistente", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        cibomodel.inserisciCibo(nuovocibo);
                        JOptionPane.showMessageDialog(null, "Cibo registrato con successo", "Operazione riuscita", JOptionPane.INFORMATION_MESSAGE);
                        newcibo.azzeraCampi();
                        cardLayout.show(variablePanel, "IndexAlimentazioneView");
                    }
                }
            }
            });

        indexalimentazione.addAggiungiCiboEffettivoButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });

        dialog.addSetPastoItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==e.SELECTED)
                setPortataItems();
            }
        });

        dialog.addSetPortataItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                showAlimenti();
            }
        });

    }

    public void showIndex(){
        cardLayout.show(variablePanel, "IndexAlimentazioneView");
    }

    public void setPortataItems(){
        String pastoscelto = dialog.getPasto().getSelectedItem().toString();
        JComboBox portata =dialog.getPortata();
        if(pastoscelto != "--scegli pasto--") {
            portata.setEnabled(true);
            dialog.getPasto().removeItem("--scegli pasto--");
            if (pastoscelto == "colazione" || pastoscelto == "spuntino") {
                portata.setModel(new DefaultComboBoxModel(new String[]{"--scegli portata--","snack", "bevanda", "frutta"}));
                DefaultComboBoxModel portataModel = (DefaultComboBoxModel) portata.getModel();
            } else {
                portata.setModel(new DefaultComboBoxModel(new String[]{"--scegli portata--","primo", "secondo", "contorno", "dolce", "frutta", "bevanda"}));
            }
        }
    }

    public void showAlimenti(){
        String pastoscelto = dialog.getPasto().getSelectedItem().toString();
        String portatascelta = dialog.getPortata().getSelectedItem().toString();
        JTextField nomeAlimento = dialog.getNomeAlimento();
        nomeAlimento.setEnabled(true);
        dialog.getScrollPane().setVisible(true);

    }
}
