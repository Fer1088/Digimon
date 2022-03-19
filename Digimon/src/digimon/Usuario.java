package digimon;

/**
 * Clase que define a un Usuario, con sus atributos, métodos y constructores.
 * @version 1.0, 19/03/2022
 * @author jmanuel
 */
public class Usuario {

    private String nombre;
    private String contrasena;
    private int partidasGan;
    private int tokensEvo;
    private boolean esAdmin;
    
    /**
     * Constructor de Usuario, orientado al registro de nuevos Usuarios.
     * @param nombre El nombre que se le quiere dar al Usuario.
     * @param contrasena La contraseña que se le quiere dar al Usuario.
     */
    public Usuario(String nombre, String contrasena){
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.partidasGan = 0;
        this.tokensEvo = 0;
        this.esAdmin = false;
    }
    
    /**
     * Constructor de Usuario, orientado al volcado de valores desde la
     * BD Digimon
     * @param nombre El nombre del Usuario.
     * @param contrasena La contraseña del Usuario.
     * @param partidasGan El número de partidas ganadas por el Usuario.
     * @param tokensEvo El número de tokens que el Usuario tiene para poder digievolucionar a sus Digimones.
     */
    public Usuario(String nombre, String contrasena, int partidasGan, int tokensEvo) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.partidasGan = partidasGan;
        this.tokensEvo = tokensEvo;
        this.esAdmin = false;
    }
    
    /**
     * Obtiene el nombre del Usuario.
     * @return El nombre actual del Usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del Usuario.
     * @param nombre El nombre propuesto para el Usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del Usuario.
     * @return La contraseña actual del Usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Modifica la contraseña del Usuario.
     * @param contrasena La contraseña propuesta para el Usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    /**
     * Obtiene el número de partidas ganadas por el Usuario.
     * @return El número actual de partidas ganadas.
     */
    public int getPartidasGan() {
        return partidasGan;
    }

    /**
     * Obtiene el número de tokens de digievolución del Usuario
     * @return El número actual de tokens de digievolución.
     */
    public int getTokensEvo() {
        return tokensEvo;
    }
    
    /**
     * Incrementa en uno el número de partidas ganadas por el Usuario.
     */
    public void incPartidasGan(){
        this.partidasGan++;
    }
    
    /**
     * Incrementa en uno el número de tokens de digievolución del Usuario.
     */
    public void incTokensEvo(){
        this.tokensEvo++;
    }
    
    /**
     * Decrementa en uno el número de tokens de digievolución del Usuario.
     */
    public void decTokensEvo(){
        this.tokensEvo--;
    }

    /**
     * Obtiene si el Usuario actual es Administrador o no.
     * @return true si el Usuario es Administrador, false si no lo es.
     */
    public boolean isEsAdmin() {
        return esAdmin;
    }

    /**
     * Modifica la condición de Administrador del Usuario actual.
     * @param esAdmin true para hacer Administrador al Usuario, false para que no sea Administrador.
     */
    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

}
