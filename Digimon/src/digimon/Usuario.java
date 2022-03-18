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
    
    private String nombre;
    private String contrasena;
    private int partidasGan;
    private int tokensEvo;
    private boolean esAdmin;
    
    public Usuario(){
        this.nombre = "Usuario";
        this.contrasena = "1234";
        this.partidasGan = 0;
        this.tokensEvo = 0;
    }
    
    public Usuario(String nombre, String contrasena){
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.partidasGan = 0;
        this.tokensEvo = 0;
        this.esAdmin = false;
    }
    
    public Usuario(String nombre, String contrasena, int partidasGan, int tokensEvo) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.partidasGan = partidasGan;
        this.tokensEvo = tokensEvo;
        this.esAdmin = false;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public int getPartidasGan() {
        return partidasGan;
    }

    public int getTokensEvo() {
        return tokensEvo;
    }
    
    public void incPartidasGan(){
        this.partidasGan++;
    }
    
    public void incTokensEvo(){
        this.tokensEvo++;
    }
    
    public void decTokensEvo(){
        this.tokensEvo--;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

}
