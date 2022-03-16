/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import digimon.Usuario;
import java.sql.*;
import java.util.HashMap;
import utilidades.Util;

/**
 *
 * @author jmanuel
 */
public class TestConexion {
    
    private static HashMap<String,Usuario> listaUsuarios = new HashMap<>();
    
    public static void recogeUsuarios(Conexion c){
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT NomUsu,ContUsu,PartidasGan,"
                    + "TokensEvo FROM Usuario");
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
            Util.muestraSQLException(e);
        }    
    }
    
    public static void main(String[] args){
        Conexion con = new Conexion("localhost","3306","Digimon","jmanuel","");
        con.conectar();
        System.out.println(listaUsuarios);
        recogeUsuarios(con);
        System.out.println(listaUsuarios);
        con.cerrar();
    }
}
