package Object;

import Helpers.JObject;
import Object.Enum.LavoroEnum;
import Object.Enum.LivelloAttivitaFisicaEnum;
import Object.Enum.AllergiaEnum;
/**
 * Created by lorenzobraconi on 28/12/16.
 */
public class UtenteObject extends JObject {

    private String username;
    private String password;
    private String nome;
    private String cognome;
    private int eta;
    private int sesso;
    private float altezza;
    private float peso;
    private LavoroEnum lavoro;
    private LivelloAttivitaFisicaEnum livello_attivita_fisica;
    private AllergiaEnum allergia;
    private float peso_forma;
    private String email;
    private ProgrammaAlimentareObject programma_alimentare;
    private ProgrammaAllenamentoObject programma_allenamento;
    private boolean prog_allen_comb;
    private boolean prog_alim_comb;

    public UtenteObject()
    {
        username=null;
        password=null;
        nome=null;
        cognome=null;
        eta=0;
        sesso=0;
        altezza=0;
        peso=0;
        lavoro=null;
        livello_attivita_fisica=null;
        allergia=null;
        peso_forma=0;
        email=null;
        programma_alimentare=null;
        programma_allenamento=null;
        prog_allen_comb=false;
        prog_alim_comb=false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getEta() {
        return eta;
    }

    public void setEta(int eta) {
        this.eta = eta;
    }

    public int getSesso() {
        return sesso;
    }

    public void setSesso(int sesso) {
        this.sesso = sesso;
    }

    public float getAltezza() {
        return altezza;
    }

    public void setAltezza(float altezza) {
        this.altezza = altezza;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public LavoroEnum getLavoro() {
        return lavoro;
    }

    public void setLavoro(LavoroEnum lavoro) {
        this.lavoro = lavoro;
    }

    public LivelloAttivitaFisicaEnum getLivello_attivita_fisica() {
        return livello_attivita_fisica;
    }

    public void setLivello_attivita_fisica(LivelloAttivitaFisicaEnum livello_attivita_fisica) {
        this.livello_attivita_fisica = livello_attivita_fisica;
    }

    public AllergiaEnum getAllergia() {
        return allergia;
    }

    public void setAllergia(AllergiaEnum allergia) {
        this.allergia = allergia;
    }

    public float getPeso_forma() {
        return peso_forma;
    }

    public void setPeso_forma(float peso_forma) {
        this.peso_forma = peso_forma;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ProgrammaAlimentareObject getProgramma_alimentare() {
        return programma_alimentare;
    }

    public void setProgramma_alimentare(ProgrammaAlimentareObject programma_alimentare) {
        this.programma_alimentare = programma_alimentare;
    }

    public ProgrammaAllenamentoObject getProgramma_allenamento() {
        return programma_allenamento;
    }

    public void setProgramma_allenamento(ProgrammaAllenamentoObject programma_allenamento) {
        this.programma_allenamento = programma_allenamento;
    }

    public boolean isProg_allen_comb() {
        return prog_allen_comb;
    }

    public void setProg_allen_comb(boolean prog_allen_comb) {
        this.prog_allen_comb = prog_allen_comb;
    }

    public boolean isProg_alim_comb() {
        return prog_alim_comb;
    }

    public void setProg_alim_comb(boolean prog_alim_comb) {
        this.prog_alim_comb = prog_alim_comb;
    }
}
