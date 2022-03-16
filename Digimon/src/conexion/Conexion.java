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
    
    private static Connection connection;
    
    public static void printSQLException(SQLException ex){
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
    
    public static void conectar(){
        try{
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Digimon","jmanuel","");
        }catch(SQLException e){
            printSQLException(e);
        }
    }
    
    public static void cerrar(){
        try{
            connection.close();
        }catch(SQLException e){
            printSQLException(e);
        }
    }
    
    public static void main(String[] args) {
        
        conectar();
        
        cerrar();
    }
}
