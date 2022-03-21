package conexion;

import digimon.*;
import java.util.*;
import utilidades.*;

/**
 *
 * @author jmanuel
 */
public class TestConexion {

    private static HashMap<String,Usuario> usuarios;
    private static HashMap<String,Digimon> digimones;
    private static HashMap<Usuario,HashSet<Digimon>> usuDigi;
    
    public static void main(String[] args){
        
        /*final HashMap<String,Usuario> USUARIOS = new HashMap<>();
        final HashMap<String,Digimon> DIGIMONES = new HashMap<>();
        final HashMap<Usuario,HashSet<Digimon>> USUDIGI = new HashMap<>();*/
        
        //Conexion con = new Conexion("localhost","3306","Digimon","jmanuel","");
        /*Conexion con = new Conexion(args);
        con.conectar();*/
        
        /*recogeUsuarios(con,USUARIOS);
        System.out.println(USUARIOS);
        
        for(Usuario usu : USUARIOS.values()){
            System.out.println(usu.getNombre());
            System.out.println(usu.getPartidasGan());
            System.out.println(usu.getTokensEvo());
            System.out.println("");
        }

        recogeDigimones(con,DIGIMONES);
        System.out.println(DIGIMONES);
        
        for(Digimon digi : DIGIMONES.values()){
            System.out.println(digi.getNomDig());
            System.out.println(digi.getTipo());
            System.out.println(digi.getAtaque());
            System.out.println(digi.getDefensa());
            System.out.println(digi.getNivel());
            System.out.println("");
        }
        
        HashSet<Digimon> Digimones1 = new HashSet<>();
        HashSet<Digimon> Digimones2 = new HashSet<>();
        HashSet<Digimon> Digimones3 = new HashSet<>();
        
        Digimones1.add(DIGIMONES.get("Felipomon"));
        Digimones1.add(DIGIMONES.get("Sambon"));
        
        System.out.println(Digimones1);
        
        Digimones2.add(DIGIMONES.get("Felipomon"));
        Digimones2.add(DIGIMONES.get("Josepomon"));
        
        System.out.println(Digimones2);
        
        Digimones3.add(DIGIMONES.get("Felipomon"));
        Digimones3.add(DIGIMONES.get("Sambon"));
        Digimones3.add(DIGIMONES.get("Josepomon"));
        
        System.out.println(Digimones3);
        
        USUDIGI.put(USUARIOS.get("Dulsesico"), Digimones1);
        USUDIGI.put(USUARIOS.get("Manolo"), Digimones2);
        USUDIGI.put(USUARIOS.get("Antonio"), Digimones3);
        
        System.out.println(USUDIGI);*/
        
        usuarios = Util.recogeUsuarios(args);
        digimones = Util.recogeDigimones(args);
        usuDigi = Util.recogeUsuDigi(args,usuarios,digimones);
        
        /*for(Usuario u : USUDIGI.keySet()){
            if(u.getNombre().equals("Dulsesico")){
                for(Digimon d : USUDIGI.get(u)){
                    if(d.getNomDig().equals("Felipomon")){
                        d.setNomDig("payo");
                    }
                }
                otorgaDigimon(u,DIGIMONES,USUDIGI);
            }
        }*/
        
        //otorgaDigimon(USUARIOS.get("Dulsesico"),DIGIMONES,USUDIGI);
        
       
        usuDigi.get(usuarios.get("Dulsesico")).add(digimones.get("Josepomon"));
        
        for(Usuario u : usuDigi.keySet()){
            //System.out.println(u.getNombre() + " " + u.isEsAdmin() + " " + u.getPartidasGan());
            //System.out.println("=========");
            for(Digimon d : usuDigi.get(u)){
                if(!d.isEstaEquipo()){
                    d.setEstaEquipo(true);
                }
                /*System.out.print(d.getNomDig() + ", tipo " + d.getTipo() +
                        " " + d.isEstaEquipo() + " " + d.getNomDigEvo() +
                        "nivel" + d.getNivel() + "\n");*/
                //System.out.println(d);
            }
            //System.out.println("");
        }
/*        
        for(int i=0; i<10; i++){
            Util.partida(usuarios.get("Dulsesico"),usuarios.get("Antonio"),usuDigi,digimones,false);
            System.out.println("Dulses: " + usuarios.get("Dulsesico").getPartidasGan());
            System.out.println("Antonio: " + usuarios.get("Antonio").getPartidasGan());
            System.out.println("");
        }
*/        
        
        /*recogeDigimones(con,DIGIMONES);
        HashSet<Digimon> digi = new HashSet<>();
        digi.add(DIGIMONES.get("Felipomon"));
        digi.add(DIGIMONES.get("Sambon"));
        digi.add(DIGIMONES.get("Josepomon"));
        
        Iterator it = digi.iterator();
        
        /*byte o = 0;
        
        if(o == 0){
            ((Digimon) it.next()).setEstaEquipo(true);
        }*/
        
        /*System.out.println(((Digimon) it.next()).getNomDig());
        System.out.println(((Digimon) it.next()).getNomDig());
        System.out.println(((Digimon) it.next()).getNomDig());*/
        
        /*String a = "34";
        int b = Integer.parseInt(a);
        String c = "false";
        boolean d = Boolean.parseBoolean(c);
        if(d){
            System.out.println(d);
        }
        
        SLeer1.limpiar();*/
        
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
                                                    contrincante = Util.pideContrincante(usuarios);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, true);
                                                    Util.pausa();
                                                    break;
                                                case 3:
                                                    contrincante = Util.randomizaUsuario(usuarios.values());
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
                                                    contrincante = Util.pideContrincante(usuarios);
                                                    Util.limpiar();
                                                    Util.partida(usuario, contrincante, usuDigi, digimones, true);
                                                    Util.pausa();
                                                    break;
                                                case 3:
                                                    contrincante = Util.randomizaUsuario(usuarios.values());
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
        
        //con.cerrar();
    }
}
