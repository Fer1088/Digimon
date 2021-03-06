/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package principal;
import java.util.*;
import digimon.*;
import utilidades.*;

/**
 * Clase principal del proyecto Digimon.
 * @version 1.0, 21/03/2022
 * @author jmanuel
 */
public class Principal {
    
    private static HashMap<String,Usuario> usuarios;
    private static HashMap<String,Digimon> digimones;
    private static HashMap<Usuario,HashSet<Digimon>> usuDigi;
    
    /**
     * Implementación del menú completo del programa, con un juego funcional.
     * @param args Argumentos pasados como parámetro desde terminal.
     */
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
                                        if(digimones.size() < 3){
                                            System.out.println("No hay suficientes Digimones para jugar :(");
                                            Util.pausa();
                                        }else{
                                            do{
                                                Menus.menuJugar(usuario);
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
                                                        contrincante = Util.randomizaUsuario(usuarios.values(),usuario);
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
                                                        contrincante = Util.randomizaUsuario(usuarios.values(),usuario);
                                                        Util.limpiar();
                                                        Util.partida(usuario, contrincante, usuDigi, digimones, false);
                                                        Util.pausa();
                                                        break;
                                                }
                                            }while(opcion != 0);
                                        }
                                        opcion = -1;
                                        break;
                                    case 2:
                                        do{
                                            Menus.menuEquipo();
                                            opcion = SLeer1.datoByte("Elige: ");
                                            SLeer1.limpiar();
                                            switch(opcion){
                                                case 1:
                                                    Util.limpiar();
                                                    Util.listaDigimonsEquipo(usuario, usuDigi);
                                                    Util.pausa();
                                                    break;
                                                case 2:
                                                    Util.reiniciaEquipo(usuario, usuDigi);
                                                    Util.limpiar();
                                                    Util.estableceEquipo(usuario, usuDigi);
                                                    Util.pausa();
                                                    break;
                                            }
                                        }while(opcion != 0);
                                        opcion = -1;
                                        break;
                                    case 3:
                                        Util.limpiar();
                                        Util.listaDigimonsUsuario(usuario, usuDigi);
                                        Util.pausa();
                                        break;
                                    case 4:
                                        Util.limpiar();
                                        Util.listaDigiEvolucionables(usuario, usuDigi);
                                        Util.digievolucion(usuario, digimones, usuDigi);
                                        Util.pausa();
                                        break;
                                    case 5:
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
                                                                Util.limpiar();
                                                                Util.listaUsuarios(usuarios);
                                                                Util.pausa();
                                                                break;
                                                            case 2:
                                                                Util.limpiar();
                                                                Util.listaUsuarios(usuarios);
                                                                Util.muestraUsuario(usuarios);
                                                                Util.pausa();
                                                                break;
                                                            case 3:
                                                                Util.limpiar();
                                                                Util.registrarUsuario(usuarios,digimones,usuDigi);
                                                                Util.pausa();
                                                                break;
                                                            case 4:
                                                                Util.limpiar();
                                                                Util.borraUsuario(usuarios);
                                                                Util.pausa();
                                                                break;
                                                            case 5:
                                                                Util.limpiar();
                                                                Util.hacerAdmin(usuarios);
                                                                Util.pausa();
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
                                                                Util.limpiar();
                                                                Util.listaDigimons(digimones);
                                                                Util.pausa();
                                                                break;
                                                            case 2:
                                                                Util.limpiar();
                                                                Util.listaDigimons(digimones);
                                                                Util.muestraDigimon(digimones);
                                                                Util.pausa();
                                                                break;
                                                            case 3:
                                                                Util.limpiar();
                                                                Util.anyadeDigimon(digimones);
                                                                Util.pausa();
                                                                break;
                                                            case 4:
                                                                Util.limpiar();
                                                                Util.borraDigimon(digimones);
                                                                Util.pausa();
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
                                                                Util.eliminaUsuDig(args, usuDigi, usuarios, digimones);
                                                                Util.eliminaUsuarios(args, usuarios);
                                                                Util.eliminaDigimones(args, digimones);
                                                                Util.actualizaUsuDig(args, usuDigi, usuarios, digimones);
                                                                Util.actualizaUsuarios(args, usuarios);
                                                                Util.insertaUsuDig(args, usuDigi, usuarios, digimones);
                                                                Util.insertaUsuarios(args, usuarios);
                                                                Util.insertaDigimones(args, digimones);
                                                                Util.pausa();
                                                                break;
                                                            case 2:
                                                                Util.reiniciaBD(args);
                                                                Util.pausa();
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
                                            Menus.menuJugar(usuario);
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
                                                    contrincante = Util.randomizaUsuario(usuarios.values(),usuario);
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
                                                    contrincante = Util.randomizaUsuario(usuarios.values(),usuario);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, false);
                                                    Util.pausa();
                                                    break;
                                            }
                                        }while(opcion != 0);
                                        opcion = -1;
                                        break;
                                    case 2:
                                        do{
                                            Menus.menuEquipo();
                                            opcion = SLeer1.datoByte("Elige: ");
                                            SLeer1.limpiar();
                                            switch(opcion){
                                                case 1:
                                                    Util.limpiar();
                                                    Util.listaDigimonsEquipo(usuario, usuDigi);
                                                    Util.pausa();
                                                    break;
                                                case 2:
                                                    Util.reiniciaEquipo(usuario, usuDigi);
                                                    Util.limpiar();
                                                    Util.estableceEquipo(usuario, usuDigi);
                                                    Util.pausa();
                                                    break;
                                            }
                                        }while(opcion != 0);
                                        opcion = -1;
                                        break;
                                    case 3:
                                        Util.limpiar();
                                        Util.listaDigimonsUsuario(usuario, usuDigi);
                                        Util.pausa();
                                        break;
                                    case 4:
                                        Util.limpiar();
                                        Util.listaDigiEvolucionables(usuario, usuDigi);
                                        Util.digievolucion(usuario, digimones, usuDigi);
                                        Util.pausa();
                                        break;
                                }
                            }
                        }while(opcion != 0);
                    }
                    opcion = -1;
                    break;
                case 2:
                    Util.limpiar();
                    Util.registrarUsuario(usuarios,digimones,usuDigi);
                    opcion = -1;
                    break;
            }
        }while(opcion != 0);
    }
}
