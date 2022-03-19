package conexion;
import java.sql.*;
import static utilidades.Util.*;
/**
 * Clase que define una Conexion a una BD, con sus atributos, métodos y 
 * constructor.
 * @version 1.0, 19/03/2022
 * @author jmanuel
 */
public class Conexion {
    
    private Connection conexion;
    private String host;
    private String puerto;
    private String baseDatos;
    private String nombre;
    private String contrasena;
    
    /**
     * Intenta conectar a la Base de Datos (BD).
     */
    public void conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mariadb://" + host + 
                    ":" + puerto + "/" + baseDatos,nombre,contrasena);
        }catch(SQLException e){
            muestraSQLException(e);
        }
    }
    
    /**
     * Intenta cerrar la conexión a la Base de Datos (BD).
     */
    public void cerrar(){
        try{
            if(conexion != null){
                conexion.close();
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }
    }

    /**
     * Constructor de Conexion, orientado a establecer una conexión con una BD.
     * @param host El host de la conexión.
     * @param puerto El puerto de la conexión.
     * @param baseDatos La Base de Datos (BD).
     * @param nombre El nombre del usuario gestor de la BD.
     * @param contrasena La contraseña del usuario gestor de la BD.
     */
    public Conexion(String host, String puerto, String baseDatos, String nombre, String contrasena) {
        this.host = host;
        this.puerto = puerto;
        this.baseDatos = baseDatos;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    /**
     * Obtiene la propia conexión del DriverManager a la BD.
     * @return La conexión a la BD.
     */
    public Connection getConexion() {
        return conexion;
    }
    
    /**
     * Obtiene el puerto en el que se encuentra la BD.
     * @return El puerto de la conexión.
     */
    public String getPuerto() {
        return puerto;
    }

    /**
     * Modifica el puerto al que la conexión hace referencia.
     * @param puerto El puerto propuesto para la conexión.
     */
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    /**
     * Obtiene el nombre de la BD de la conexión.
     * @return El nombre de la Base de Datos.
     */
    public String getBaseDatos() {
        return baseDatos;
    }

    /**
     * Modifica el nombre de la BD a la que se conecta.
     * @param baseDatos El nombre propuesto para la BD de la conexión.
     */
    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    /**
     * Obtiene el nombre del usuario gestor de la BD.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del usuario gestor de la BD.
     * @param nombre El nombre propuesto para el usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la contraseña del usuario gestor de la BD.
     * @return La contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Modifica la contraseña del usuario gestor de la BD.
     * @param contrasena La contraseña propuesta para el usuario.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Obtiene el Host de la conexión a la BD.
     * @return El Host de la conexión.
     */
    public String getHost() {
        return host;
    }

    /**
     * Modifica el Host de la conexión a la BD.
     * @param host El Host propuesto para la conexión.
     */
    public void setHost(String host) {
        this.host = host;
    }
    
}
