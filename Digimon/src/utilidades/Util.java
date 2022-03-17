/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import conexion.Conexion;
import digimon.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author jmanuel
 */
public class Util {
    
    public static void muestraSQLException(SQLException ex){
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
    
    public static void creaUsuario(String nombre, String contrasena, HashMap<String,Usuario> lista){
        Usuario usuario = new Usuario(nombre, contrasena);
        try{
            if(lista.containsKey(usuario.getNombre())) {
                throw new Exception("Ya existe un usuario con ese nombre.");
            }
            else{
                lista.put(usuario.getNombre(), usuario);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void creaDigimon(String nombre, String tipo, int ataque, int defensa, int nivel, HashMap<String,Digimon> lista){
        Digimon digimon = new Digimon(nombre,tipo,nivel,ataque,defensa);
        try{
            if(lista.containsKey(digimon.getNomDig())) {
                throw new Exception("Ya existe un Digimon con ese nombre.");
            }
            else{
                lista.put(digimon.getNomDig(), digimon);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void recogeDigimones(Conexion c, HashMap<String,Digimon> lista){
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

                    lista.put(nomDig, digimon);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }    
    }
    
    public static void recogeUsuarios(Conexion c, HashMap<String,Usuario> lista){
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

                    lista.put(nomUsu, usuario);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }    
    }
    
    public static void recogeUsuDigi(Conexion c, HashMap<String,Usuario> usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT NomUsu FROM Tiene GROUP BY NomUsu");
            if(res){
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    String nomUsu = rs.getString(1);
                    HashSet<Digimon> digimones = new HashSet<>();
                    boolean res1 = st.execute("SELECT NomDig, EstaEquipo FROM Tiene WHERE NomUsu = '" + nomUsu + "'");
                    if(res1){
                        ResultSet rs1 = st.getResultSet();
                        while(rs1.next()){
                            String nomDig = rs1.getString(1);
                            int estaEquipo = rs1.getByte(2);
                            
                            Digimon digimon = (Digimon)dig.get(nomDig).clone();
                            if(estaEquipo == 0){
                                digimon.setEstaEquipo(true);
                            }
                            
                            digimones.add(digimon);
                        }
                        usuDig.put(usu.get(nomUsu), digimones);
                    }
                }
            }
        }catch(SQLException ex){
            muestraSQLException(ex);
        }
    }
}
