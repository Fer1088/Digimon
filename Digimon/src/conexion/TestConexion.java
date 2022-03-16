/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import digimon.Digimon;
import digimon.Usuario;
import java.util.HashMap;
import java.util.HashSet;
import static utilidades.Util.*;

/**
 *
 * @author jmanuel
 */
public class TestConexion {
    
    
    
    public static void main(String[] args){
        
        final HashMap<String,Usuario> USUARIOS = new HashMap<>();
        final HashMap<String,Digimon> DIGIMONES = new HashMap<>();
        final HashMap<Usuario,HashSet<Digimon>> USUDIGI = new HashMap<>();
        
        Conexion con = new Conexion("localhost","3306","Digimon","jmanuel","");
        con.conectar();
        
        /*recogeUsuarios(con,USUARIOS);
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
        
        HashSet<Digimon> Digimones1 = new HashSet<>();
        HashSet<Digimon> Digimones2 = new HashSet<>();
        HashSet<Digimon> Digimones3 = new HashSet<>();
        
        Digimones1.add(DIGIMONES.get("Felipomon"));
        Digimones1.add(DIGIMONES.get("Sambon"));
        
        System.out.println(Digimones1);
        
        Digimones2.add(DIGIMONES.get("Felipomon"));
        Digimones2.add(DIGIMONES.get("Josepomon"));
        
        System.out.println(Digimones2);
        
        Digimones3.add(DIGIMONES.get("Felipomon"));
        Digimones3.add(DIGIMONES.get("Sambon"));
        Digimones3.add(DIGIMONES.get("Josepomon"));
        
        System.out.println(Digimones3);
        
        USUDIGI.put(USUARIOS.get("Dulsesico"), Digimones1);
        USUDIGI.put(USUARIOS.get("Manolo"), Digimones2);
        USUDIGI.put(USUARIOS.get("Antonio"), Digimones3);
        
        System.out.println(USUDIGI);*/
        
        /*recogeUsuarios(con,USUARIOS);
        recogeDigimones(con,DIGIMONES);
        recogeUsuDigi(con,USUARIOS,DIGIMONES,USUDIGI);
        
        for(Usuario u : USUDIGI.keySet()){
            System.out.println(u.getNombre());
            System.out.println("=========");
            for(Digimon d : USUDIGI.get(u)){
                System.out.println(d.getNomDig());
            }
            System.out.println("");
        }*/
        
        con.cerrar();
    }
}
