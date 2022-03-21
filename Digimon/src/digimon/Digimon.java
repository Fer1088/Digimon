package digimon;

/**
 * Clase que define a un Digimon, con sus atributos, métodos y constructores.
 * @version 1.0, 19/03/2022
 * @author jmanuel
 */
public class Digimon/* implements Cloneable*/{

    /**
     * Tipos que el Digimon puede tomar.
     */
    public enum Tipos {
        VIRUS,
        VACUNA,
        ELEMENTAL,
        ANIMAL,
        PLANTA
    }
   
    private String nomDig;
    private Tipos tipo;
    private int nivel;
    private int ataque;
    private int defensa;
    private boolean estaEquipo;
    private String nomDigEvo;

    /**
     * Constructor de Digimon, orientado tanto a la extracción de valores de la
     * BD Digimon como a la creación de nuevos Digimones.
     * @param nomDig El nombre del Digimon.
     * @param tipo El tipo del Digimon.
     * @param nivel El nivel (de 1 a 3) del Digimon.
     * @param ataque El ataque (valor numérico) del Digimon.
     * @param defensa La defensa (valor numérico) del Digimon.
     * @param nomDigEvo El nombre del Digimon al que puede evolucionar.
     */
    public Digimon(String nomDig, String tipo, int nivel, int ataque, int defensa, String nomDigEvo) {
        this.nomDig = nomDig;
        this.ataque = ataque;
        this.defensa = defensa;
        this.estaEquipo = false;
        this.setTipo(tipo);
        this.setNivel(nivel);
        this.setNomDigEvo(nomDigEvo);
    }
    
    /**
     * Constructor de Digimon, orientado a la extracción de valores de la
     * BD Digimon por medio de un vector de cadenas de caracteres.
     * @param param Un vector que contiene cadenas de caracteres. 
     */
    public Digimon(String[] param){
        this.nomDig = param[0];
        this.ataque = Integer.parseInt(param[1]);
        this.defensa = Integer.parseInt(param[2]);
        this.estaEquipo = false;
        this.setTipo(param[3]);
        this.setNivel(Integer.parseInt(param[4]));
        this.setNomDigEvo(param[5]);
    }
    
    /**
     * Constructor copia de Digimon, copia los valores de los atributos de un
     * Digimon a los atributos de otro nuevo.
     * @param d El Digimon que se quiere copiar.
     * @param estaEquipo Si el Digimon está en el Equipo (true), o no (false).
     */
    public Digimon(Digimon d, boolean estaEquipo){
        this.nomDig = d.nomDig;
        this.nivel = d.nivel;
        this.ataque = d.ataque;
        this.defensa = d.defensa;
        this.tipo = d.tipo;
        this.nomDigEvo = d.nomDigEvo;
        this.estaEquipo = estaEquipo;
    }

    /**
     * Obtiene el nombre del Digimon.
     * @return El nombre actual del Digimon.
     */
    public String getNomDig() {
        return nomDig;
    }

    /**
     * Modifica el nombre del Digimon.
     * @param nomDig El nombre propuesto para el Digimon.
     */
    public void setNomDig(String nomDig) {
        this.nomDig = nomDig;
    }

    /**
     * Obtiene el tipo del Digimon.
     * @return El tipo actual del Digimon.
     */
    public Tipos getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del Digimon.
     * @param tipo El tipo propuesto para el Digimon.
     */
    private void setTipo(String tipo) {
        this.tipo = Tipos.valueOf(tipo);
    }

    /**
     * Obtiene el nivel del Digimon.
     * @return El nivel actual del Digimon.
     */
    public int getNivel() {
        return nivel;
    }

    /**
     * Modifica el nivel del Digimon.
     * @param nivel El nivel propuesto para el Digimon.
     */
    private void setNivel(int nivel) {
        if(nivel > 3){
            this.nivel = 3;
        }else if(nivel < 1){
            this.nivel = 1;
        }else{
            this.nivel = nivel;
        }
    }

    /**
     * Obtiene el ataque del Digimon.
     * @return El ataque actual del Digimon.
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Modifica el ataque del Digimon.
     * @param ataque El ataque propuesto para el Digimon.
     */
    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    /**
     * Obtiene la defensa del Digimon.
     * @return La defensa actual del Digimon.
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Modifica la defensa del Digimon.
     * @param defensa La defensa propuesta para el Digimon.
     */
    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    /**
     * Obtiene si el Digimon actual está en el Equipo o no.
     * @return true si el Digimon está en el Equipo, false si no está.
     */
    public boolean isEstaEquipo() {
        return estaEquipo;
    }

    /**
     * Modifica la condición de estar en el Equipo del Digimon actual.
     * @param estaEquipo true para hacer que el Digimon esté en el Equipo,
     * false para que no esté en el Equipo.
     */
    public void setEstaEquipo(boolean estaEquipo) {
        this.estaEquipo = estaEquipo;
    }

    /**
     * Obtiene el nombre del Digimon al que el Digimon actual puede
     * digievolucionar.
     * @return El nombre del Digimon al que puede digievolucionar.
     */
    public String getNomDigEvo() {
        return nomDigEvo;
    }

    /**
     * Modifica el nombre del Digimon al que el Digimon acual puede
     * digievolucionar.
     * @param nomDigEvo El nombre del Digimon propuesto para el que
     * puede digievolucionar.
     */
    private void setNomDigEvo(String nomDigEvo) {
        if(nomDigEvo == null){
            this.nomDigEvo = "";
        }else{
            if(this.nivel < 3){
                this.nomDigEvo = nomDigEvo;
            }else{
                this.nomDigEvo = "";
            }
        }
    }

}
