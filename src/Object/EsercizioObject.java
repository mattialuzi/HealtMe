package Object;

import Helpers.JObject;
import Object.Enum.CategoriaEnum;
import Object.Enum.IntensitaEnum;
import Object.Enum.UnitaMisuraEnum;

/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class EsercizioObject extends JObject{

    private String tipologia;
    private CategoriaEnum categoria;
    private IntensitaEnum intensita;
    private UnitaMisuraEnum unita_misura;
    private int consumo_calorico;

    public EsercizioObject() {
        tipologia=null;
        consumo_calorico=0;
        categoria=null;
        intensita=null;
        unita_misura=null;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public int getConsumo_calorico() {
        return consumo_calorico;
    }

    public void setConsumo_calorico(int consumo_calorico) {
        this.consumo_calorico = consumo_calorico;
    }

    public CategoriaEnum getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEnum categoria) {
        this.categoria = categoria;
    }

    public IntensitaEnum getIntensita() {
        return intensita;
    }

    public void setIntensita(IntensitaEnum intensita) {
        this.intensita = intensita;
    }

    public UnitaMisuraEnum getUnita_misura() {
        return unita_misura;
    }

    public void setUnita_misura(UnitaMisuraEnum unita_misura) {
        this.unita_misura = unita_misura;
    }
}
