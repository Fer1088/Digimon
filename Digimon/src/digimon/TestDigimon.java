/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

import static digimon.Digimon.Tipos.*;
import java.util.HashMap;
import java.util.HashSet;

/**
 *
 * @author ferna
 */
public class TestDigimon {

    static HashMap<String, Digimon> digimons = new HashMap<>();

    static Digimon Felipe = new Digimon("Felipomon", ANIMAL, 65, 58, 23);
    static Digimon Josepa = new Digimon("Josepomon", VIRUS, 28, 28, 23);
    static Digimon Sambon = new Digimon("Sambon", VACUNA, 69, 23, 8);

    static HashSet<Digimon> digimones = new HashSet<>();


    /* public static void creadigimons(String Nombre, Digimon.Tipos t, int lvl, int ataq, int def) {
        Digimon tusmuertos = new Digimon(Nombre, t, lvl, ataq, def);

        boolean noveas = digimons.contains(tusmuertos);

        if (!noveas) {
            digimons.add(tusmuertos);
        } else {
            System.out.println("Ya hay un digimon con ese nombre");
        }
        System.out.println(noveas);
    }*/
    public static void main(String[] args) {

        digimons.put(Felipe.getNomDig(), Felipe);
        digimons.put(Josepa.getNomDig(), Josepa);
        digimons.put(Sambon.getNomDig(), Sambon);
        
        digimones.add(digimons.get("Felipomon"));
        digimones.add(digimons.get("Felipomon"));
       
        System.out.println(digimones);

    }
}
