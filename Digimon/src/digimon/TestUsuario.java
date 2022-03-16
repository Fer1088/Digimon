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
    
    private static final HashMap<String,Usuario> listaUsuarios = new HashMap<>();
    
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
    
    public static void main(String[] args){
        
        creaUsuario("hola","123",listaUsuarios);
        creaUsuario("adios","456",listaUsuarios);
        creaUsuario("cara","789",listaUsuarios);
        creaUsuario("cruz","987",listaUsuarios);
        
        System.out.println(listaUsuarios);
        
    }
}
