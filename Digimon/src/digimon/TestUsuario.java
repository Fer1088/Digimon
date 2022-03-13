/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

import java.util.HashMap;

/**
 *
 * @author jmanuel
 */
public class TestUsuario {
    
    private static HashMap<String,Usuario> listaUsuarios = new HashMap<>();
    
    public static void creaUsuario(String nombre, String contrasena){
        Usuario usuario = new Usuario(nombre, contrasena);
        try{
            if(listaUsuarios.containsKey(usuario.getNombre())) {
                throw new Exception("Ya existe un usuario con ese nombre.");
            }
            else{
                listaUsuarios.put(usuario.getNombre(), usuario);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static void main(String[] args){
        
        creaUsuario("hola","123");
        creaUsuario("adios","456");
        creaUsuario("cara","789");
        creaUsuario("cruz","987");
        
        System.out.println(listaUsuarios);
        
    }
}
