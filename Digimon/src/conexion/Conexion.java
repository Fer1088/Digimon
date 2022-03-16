/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author jmanuel
 */
public class Conexion {
    
    private static Connection connection;
    private static Statement statement;
    
    public static void conectar(){
        try{
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Digimon","jmanuel","");
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void cerrar(){
        try{
            connection.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        
        conectar();
        
        try{
            statement = connection.createStatement();
            ResultSet query = statement.executeQuery("SELECT * FROM Usuario");
            while(query.next()){
                System.out.println(query.getString("nomUsu") + " " + query.getString("contUsu"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        
        cerrar();
    }
}
