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
    private int tokensEvo;
    
    public Usuario(String nomUsu, String contUsu){
        this.nomUsu = nomUsu;
        this.contUsu = contUsu;
        this.partidasGan = 0;
        this.tokensEvo = 0;
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

}
