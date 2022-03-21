/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;
import java.util.*;
import digimon.*;
import utilidades.*;

/**
 *
 * @author jmanuel
 */
public class Principal {
    
    private static HashMap<String,Usuario> usuarios;
    private static HashMap<String,Digimon> digimones;
    private static HashMap<Usuario,HashSet<Digimon>> usuDigi;
    
    public static void main(String[] args){
        
        usuarios = Util.recogeUsuarios(args);
        digimones = Util.recogeDigimones(args);
        usuDigi = Util.recogeUsuDigi(args,usuarios,digimones);
        
        Usuario usuario = null;
        Usuario contrincante = null;
        
        byte opcion = 0;
        do{
            Menus.pantallaInicio();
            opcion = SLeer1.datoByte("Elige: ");
            SLeer1.limpiar();
            switch(opcion){
                case 1:                    
                    usuario = Util.iniciarSesion(usuarios);
                    Util.pausa();
                    if(usuario != null){
                        do{
                            if(usuario.isEsAdmin()){
                                Menus.menuPrincipalAdmin();
                                opcion = SLeer1.datoByte("Elige: ");
                                SLeer1.limpiar();
                                switch(opcion){
                                    case 1:                                        
                                        do{
                                            Menus.menuJugar();
                                            opcion = SLeer1.datoByte("Elige: ");
                                            SLeer1.limpiar();
                                            switch(opcion){
                                                case 1:
                                                    contrincante = Util.pideContrincante(usuarios);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, true);
                                                    Util.pausa();
                                                    break;
                                                case 2:
                                                    contrincante = Util.randomizaUsuario(usuarios.values());
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, true);
                                                    Util.pausa();
                                                    break;
                                                case 3:
                                                    contrincante = Util.pideContrincante(usuarios);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, false);
                                                    Util.pausa();
                                                    break;
                                                case 4:
                                                    contrincante = Util.randomizaUsuario(usuarios.values());
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, false);
                                                    Util.pausa();
                                                    break;
                                            }
                                        }while(opcion != 0);
                                        opcion = -1;
                                        break;
                                    case 2:                                        
                                        break;
                                    case 3:                                        
                                        break;
                                    case 4:                                        
                                        do{
                                            Menus.menuOpciones();
                                            opcion = SLeer1.datoByte("Elige: ");
                                            SLeer1.limpiar();
                                            switch(opcion){
                                                case 1:                                                    
                                                    do{
                                                        Menus.menuUsuario();
                                                        opcion = SLeer1.datoByte("Elige: ");
                                                        SLeer1.limpiar();
                                                        switch(opcion){
                                                            case 1:                                                                
                                                                break;
                                                            case 2:                                                                
                                                                break;
                                                            case 3:                                                                
                                                                break;
                                                            case 4:                                                                
                                                                break;
                                                            case 5:
                                                                break;
                                                        }
                                                    }while(opcion != 0);
                                                    opcion = -1;
                                                    break;
                                                case 2:                                                    
                                                    do{
                                                        Menus.menuDigimon();
                                                        opcion = SLeer1.datoByte("Elige: ");
                                                        SLeer1.limpiar();
                                                        switch(opcion){
                                                            case 1:                                                                
                                                                break;
                                                            case 2:                                                                
                                                                break;
                                                            case 3:                                                                
                                                                break;
                                                            case 4:                                                                
                                                                break;
                                                        }
                                                    }while(opcion != 0);
                                                    opcion = -1;
                                                    break;
                                                case 3:                                                    
                                                    do{
                                                        Menus.menuBaseDatos();
                                                        opcion = SLeer1.datoByte("Elige: ");
                                                        SLeer1.limpiar();
                                                        switch(opcion){
                                                            case 1:                                                                
                                                                break;
                                                            case 2:                                                                
                                                                break;
                                                        }
                                                    }while(opcion != 0);
                                                    opcion = -1;
                                                    break;
                                            }
                                        }while(opcion != 0);
                                        opcion = -1;
                                        break;
                                }
                            }
                            else{
                                Menus.menuPrincipal();
                                opcion = SLeer1.datoByte("Elige: ");
                                SLeer1.limpiar();
                                switch(opcion){
                                    case 1:
                                        do{                                            
                                            Menus.menuJugar();
                                            opcion = SLeer1.datoByte("Elige: ");
                                            SLeer1.limpiar();
                                            switch(opcion){
                                                case 1:
                                                    contrincante = Util.pideContrincante(usuarios);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, true);
                                                    Util.pausa();
                                                    break;
                                                case 2:
                                                    contrincante = Util.randomizaUsuario(usuarios.values());
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, true);
                                                    Util.pausa();
                                                    break;
                                                case 3:
                                                    contrincante = Util.pideContrincante(usuarios);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, false);
                                                    Util.pausa();
                                                    break;
                                                case 4:
                                                    contrincante = Util.randomizaUsuario(usuarios.values());
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, false);
                                                    Util.pausa();
                                                    break;
                                            }
                                        }while(opcion != 0);
                                        opcion = -1;
                                        break;
                                    case 2:                                        
                                        break;
                                    case 3:                                        
                                        break;
                                }
                            }
                        }while(opcion != 0);
                    }
                    opcion = -1;
                    break;
                case 2:
                    Util.registrarUsuario(usuarios,digimones,usuDigi);
                    opcion = -1;
                    break;
            }
        }while(opcion != 0);        
    }
}
