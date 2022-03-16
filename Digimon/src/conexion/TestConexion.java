/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import digimon.Digimon;
import digimon.Usuario;
import java.util.HashMap;
import static utilidades.Util.*;

/**
 *
 * @author jmanuel
 */
public class TestConexion {
    
    public static void main(String[] args){
        final HashMap<String,Usuario> USUARIOS = new HashMap<>();
        final HashMap<String,Digimon> DIGIMONES = new HashMap<>();
        
        Conexion con = new Conexion("localhost","3306","Digimon","jmanuel","");
        con.conectar();
        
        recogeUsuarios(con,USUARIOS);
        System.out.println(USUARIOS);
        
        for(Usuario usu : USUARIOS.values()){
            System.out.println(usu.getNombre());
            System.out.println(usu.getPartidasGan());
            System.out.println(usu.getTokensEvo());
            System.out.println("");
        }

        recogeDigimones(con,DIGIMONES);
        System.out.println(DIGIMONES);
        
        for(Digimon digi : DIGIMONES.values()){
            System.out.println(digi.getNomDig());
            System.out.println(digi.getTipo());
            System.out.println(digi.getAtaque());
            System.out.println(digi.getDefensa());
            System.out.println(digi.getNivel());
            System.out.println("");
        }
        
        con.cerrar();
    }
}
