/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

/**
 *
 * @author jmanuel
 */
public class Digimon implements Cloneable{

    public enum Tipos {
        VIRUS,
        VACUNA,
        ELEMENTAL,
        ANIMAL,
        PLANTA,

    }
   
    private String nomDig;
    private Tipos tipo;
    private int nivel;
    private int ataque;
    private int defensa;
    private boolean estaEquipo;
    private String nomDigEvo;

    public Digimon(String Nombre, Tipos t,int lvl,int ataq,int def,String nombreEvo) {
        nomDig = Nombre;
        tipo=t;
        nivel = lvl;
        ataque = ataq;
        defensa= def;
        estaEquipo = false;
        nomDigEvo = nombreEvo;
        
    }

    public Digimon(String nomDig, String tipo, int nivel, int ataque, int defensa, String nomDigEvo) {
        this.nomDig = nomDig;
        this.nivel = nivel;
        this.ataque = ataque;
        this.defensa = defensa;
        this.estaEquipo = false;
        this.nomDigEvo = nomDigEvo;
        
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
    
    @Override
    public Object clone() {
        Object objeto = null;
        try{
            objeto = super.clone();
        }catch(CloneNotSupportedException ex){
            ex.printStackTrace();
        }
        return objeto;
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

    public boolean isEstaEquipo() {
        return estaEquipo;
    }

    public void setEstaEquipo(boolean estaEquipo) {
        this.estaEquipo = estaEquipo;
    }

    public String getNomDigEvo() {
        return nomDigEvo;
    }

    public void setNomDigEvo(String nomDigEvo) {
        this.nomDigEvo = nomDigEvo;
    }

}
