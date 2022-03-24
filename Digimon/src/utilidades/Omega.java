/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import digimon.Digimon;
import digimon.Usuario;
import java.util.HashMap;
import java.util.HashSet;
import principal.*;

/**
 *
 * @author ferna
 */
public class Omega {

    private static HashMap<String, Usuario> omegausu = Principal.getUsuarios();
    private static HashMap<String, Digimon> omegadigi = Principal.getDigimones();
    private static HashMap<Usuario, HashSet<Digimon>> omegausuDigi = Principal.getUsuDigi();

    public static void muestraUsuarios() {
        for (String i : omegausu.keySet()) {
            System.out.println(i);
        }
    }

    public static void muestraDigimons() {
        for (String i : omegadigi.keySet()) {
            System.out.println(i);
        }
    }

    public static void muestraDigimonsUsuario() {
        for (Usuario i : omegausuDigi.keySet()) {
            System.out.println(i);
            for (HashSet<Digimon> j : omegausuDigi.values()) {
                System.out.println(j);
            }
        }
    }

    public static void muestraUsuarioConcreto(String elfuerte) {
        for (String i : omegausu.keySet()) {
            if (i == elfuerte) {
                System.out.println(i);
            }
        }
    }

    public static void muestraDigimonConcreto(String elfuerte) {
        for (String i : omegadigi.keySet()) {
            if (i == elfuerte) {
                System.out.println(i);
            }
        }
    }

    public static void muestraDigimonsUsuarioConcreto(Usuario elfuerte) {
        for (Usuario i : omegausuDigi.keySet()) {
            if (i == elfuerte) {
                System.out.println(i);
                for (HashSet<Digimon> j : omegausuDigi.values()) {
                    System.out.println(j);
                }
            }
        }
    }
    
  

   
}
