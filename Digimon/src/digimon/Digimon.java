/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

import java.util.HashSet;

/**
 *
 * @author jmanuel
 */
public class Digimon {

    public enum Tipos {
        VIRUS,
        VACUNA,
        ELEMENTAL,
        ANIMAL,
        PLANTA,

    }
   
    
    private int id;
    private String nomDig;
    private Tipos tipo;
    private int nivel;
    private int ataque;
    private int defensa;

    public Digimon(String Nombre, Tipos t,int lvl,int ataq,int def) {
        nomDig = Nombre;
        tipo=t;
        nivel = lvl;
        ataque = ataq;
        defensa= def;
        
    }

    public Digimon(String nomDig, String tipo, int nivel, int ataque, int defensa) {
        this.nomDig = nomDig;
        this.nivel = nivel;
        this.ataque = ataque;
        this.defensa = defensa;
        
        switch(tipo){
            case "VIRUS":
                this.tipo = Tipos.VIRUS;
                break;
            case "VACUNA":
                this.tipo = Tipos.VACUNA;
                break;
            case "ELEMENTAL":
                this.tipo = Tipos.ELEMENTAL;
                break;
            case "ANIMAL":
                this.tipo = Tipos.ANIMAL;
                break;
            case "PLANTA":
                this.tipo = Tipos.PLANTA;
                break;
            default:
                this.tipo = null;
        }
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomDig() {
        return nomDig;
    }

    public void setNomDig(String nomDig) {
        this.nomDig = nomDig;
    }

    public Tipos getTipo() {
        return tipo;
    }

    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

}
