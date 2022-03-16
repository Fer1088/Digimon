/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author jmanuel
 */
public class TestConexion {
    public static void main(String[] args){
        Conexion con = new Conexion("localhost","3306","Digimon","jmanuel","");
        con.conectar();
        
        con.cerrar();
    }
}
