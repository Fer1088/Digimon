/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import digimon.Usuario;
import java.sql.*;
import java.util.HashMap;
import static utilidades.Util.*;

/**
 *
 * @author jmanuel
 */
public class TestConexion {
    
    public static void recogeUsuarios(Conexion c, HashMap<String,Usuario> listaUsuarios){
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT * FROM Usuario");
            if(res){
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    String nomUsu = rs.getString(1);
                    String contUsu = rs.getString(2);
                    int partidasGan = rs.getInt(3);
                    int tokensEvo = rs.getInt(4);

                    Usuario usuario = new Usuario(nomUsu,contUsu,partidasGan,tokensEvo);

                    listaUsuarios.put(nomUsu, usuario);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }    
    }
    
    public static void recogeDigimones(Conexion c, HashMap<String,Digimon> listaDigimones){
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT * FROM Digimon");
            if(res){
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    String nomDig = rs.getString(1);
                    int atacDig = rs.getInt(2);
                    int defDig = rs.getInt(3);
                    String tipoDig = rs.getString(4);
                    int nivDig = rs.getInt(5);

                    Digimon digimon = new Digimon(nomDig,tipoDig,nivDig,atacDig,defDig);

                    listaDigimones.put(nomDig, digimon);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }    
    }
    
    public static void main(String[] args){
        final HashMap<String,Usuario> USUARIOS = new HashMap<>();
        final HashMap<String,Digimon> DIGIMONES = new HashMap<>();
        
        Conexion con = new Conexion("localhost","3306","Digimon","jmanuel","");
        con.conectar();
        
        System.out.println(listaUsuarios);
        recogeUsuarios(con,listaUsuarios);
        System.out.println(listaUsuarios);
        
        System.out.println(listaDigimones);
        recogeDigimones(con,listaDigimones);
        System.out.println(listaDigimones);
        
        con.cerrar();
    }
}
