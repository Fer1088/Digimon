/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digimon;

/**
 *
 * @author jmanuel
 */
public class Usuario {
    
    private String nomUsu;
    private String contUsu;
    private int partidasGan;
    
    public Usuario(String nombre, String contrasena){
        nomUsu = nombre;
        contUsu = contrasena;
    }
    
    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
    }

    public String getContUsu() {
        return contUsu;
    }

    public void setContUsu(String contUsu) {
        this.contUsu = contUsu;
    }

    public int getPartidasGan() {
        return partidasGan;
    }

    public void setPartidasGan(int partidasGan) {
        this.partidasGan = partidasGan;
    }
}
