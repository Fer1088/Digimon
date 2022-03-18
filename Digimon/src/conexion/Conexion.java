/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.*;
import static utilidades.Util.*;
/**
 *
 * @author jmanuel
 */
public class Conexion {
    
    private Connection conexion;
    private String url;
    private String puerto;
    private String baseDatos;
    private String nombre;
    private String contrasena;
    
    public void conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mariadb://" + url + 
                    ":" + puerto + "/" + baseDatos,nombre,contrasena);
        }catch(SQLException e){
            muestraSQLException(e);
        }
    }
    
    public void cerrar(){
        try{
            if(conexion != null){
                conexion.close();
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }
    }

    public Conexion(String url, String puerto, String baseDatos, String nombre, String contrasena) {
        this.url = url;
        this.puerto = puerto;
        this.baseDatos = baseDatos;
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Connection getConexion() {
        return conexion;
    }
    
    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
