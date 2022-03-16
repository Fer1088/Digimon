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
    private static Statement statement;
    
    public static void main(String[] args) throws SQLException{
        
        try{
            connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Digimon","jmanuel","");
            statement = connection.createStatement();
            ResultSet query = statement.executeQuery("SELECT * FROM Usuario");
            while(query.next()){
                System.out.println(query.getString("nomUsu") + " " + query.getString("contUsu"));
            }
            System.out.println("Connection succeded!");
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            connection.close();
        }
    }
}
