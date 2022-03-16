/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.*;
/**
 *
 * @author jmanuel
 */
public class Conexion {
    
    private Connection conexion;
    private String puerto;
    private String baseDatos;
    private String nombre;
    private String contrasena;
    
    public void printSQLException(SQLException ex){
        ex.printStackTrace(System.err);
        System.err.println("Código SQLState: " + ex.getSQLState());
        System.err.println("Código error: " + ex.getErrorCode());
        System.err.println("Mensaje: " + ex.getMessage());
        Throwable t = ex.getCause();
        while(t != null){
            System.out.println("Causa: " + t);
            t = t.getCause();
        }
    }
    
    public void conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mariadb://localhost:" +
                    puerto + "/" + baseDatos,nombre,contrasena);
        }catch(SQLException e){
            printSQLException(e);
        }
    }
    
    public void cerrar(){
        try{
            conexion.close();
        }catch(SQLException e){
            printSQLException(e);
        }
    }

    public Conexion(String puerto, String baseDatos, String nombre, String contrasena) {
        this.puerto = puerto;
        this.baseDatos = baseDatos;
        this.nombre = nombre;
        this.contrasena = contrasena;
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
    
}
