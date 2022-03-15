/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

import static digimon.Digimon.Tipos.ANIMAL;
import java.util.HashSet;

/**
 *
 * @author ferna
 */
public class TestDigimon {

    static HashSet<Digimon> digimons = new HashSet<>();

    public static void creadigimons(String Nombre, Digimon.Tipos t, int lvl, int ataq, int def) {
        Digimon tusmuertos = new Digimon(Nombre, t, lvl, ataq, def);

        boolean noveas = digimons.contains(tusmuertos);

        if (!noveas) {
            digimons.add(tusmuertos);
        } else {
            System.out.println("Ya hay un digimon con ese nombre");
        }
        System.out.println(noveas);
    }


public static void main(String[] args){

creadigimons("Felipe",ANIMAL,69,23,32);
creadigimons("Felipe",ANIMAL,69,23,32);
    System.out.println(digimons);
   
}
}
