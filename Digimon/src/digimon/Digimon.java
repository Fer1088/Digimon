/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

/**
 *
 * @author jmanuel
 */
public class Digimon {

    enum Tipos {
        Virus,
        Vacuna,
        Elemental,
        Animal,
        Planta,

    }

    private int id;
    private String nomDig;
    private String tipo;
    private int nivel;
    private int ataque;
    private int defensa;

    public Digimon(String Nombre,String Tipo) {
        nomDig = Nombre;
        tipo=Tipos.Tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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
