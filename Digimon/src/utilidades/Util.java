/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import conexion.Conexion;
import digimon.*;
import java.sql.*;
import java.util.*;
import java.io.*;

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
                throw new Exception("Ya existe un Usuario con ese nombre.");
            }
            else{
                lista.put(usuario.getNombre(), usuario);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void creaDigimon(String nombre, String tipo, int ataque, int defensa, int nivel, String nomEvo, HashMap<String,Digimon> lista){
        Digimon digimon = new Digimon(nombre,tipo,nivel,ataque,defensa,nomEvo);
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
                    String nomDigEvo = rs.getString(6);

                    Digimon digimon = new Digimon(nomDig,tipoDig,nivDig,atacDig,defDig,nomDigEvo);

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
                    byte esAdmin = rs.getByte(5);

                    Usuario usuario = new Usuario(nomUsu,contUsu,partidasGan,tokensEvo);
                    if(esAdmin == 1){
                        usuario.setEsAdmin(true);
                    }

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
                            byte estaEquipo = rs1.getByte(2);
                            
                            //Digimon digimon = (Digimon)dig.get(nomDig).clone();
                            Digimon digimon = new Digimon(dig.get(nomDig));
                            if(estaEquipo == 1){
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
    
    public static void otorgaDigimon(Usuario usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){        
        Digimon[] arrayDig = dig.values().toArray(new Digimon[dig.values().size()]);
        Random rnd = new Random();
        int numRnd = rnd.nextInt(arrayDig.length);
        
        Digimon[] arrayUsuDig = usuDig.values().toArray(new Digimon[usuDig.values().size()]);
        
        Digimon digimon = arrayDig[numRnd];
        String nomDig = digimon.getNomDig();
        System.out.println(nomDig);
        
        /*if(!usuDig.get(usu).contains(arrayDig[numRnd])){
        usuDig.get(usu).add(arrayDig[numRnd]);
        }
        boolean insertar = false;
        for(Digimon d : usuDig.get(usu)){
            insertar = !(d.getNomDig().equals(digimon.getNomDig().toString()));
            if(insertar){
                usuDig.get(usu).add(digimon);
                break;
            }
        }
        for(Digimon arrayDig1 : arrayDig) {
            System.out.println(arrayDig1.getNomDig());
        }*/
    }    
}
