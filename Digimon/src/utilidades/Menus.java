/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

/**
 *
 * @author jmanuel
 */
public class Menus {

    public static void limpiar(){
        for(int i=0; i<100; i++){
            System.out.println("");
        }
    }
    
    public static void imprimeIguales(int n){
        for(int i=0; i<n; i++){
            System.out.print("=");
        }
    }
    
    public static void menuBaseDatos(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Actualizar registros");
            System.out.println("2) Reinicializar Base de Datos");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Ir atrás");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
            SLeer1.limpiar();
            
            switch(opcion){
                case 1:
                    break;
                case 2:
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);
    }
    
    public static void menuDigimon(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Listar Digimones");
            System.out.println("2) Mostrar Digimon");
            System.out.println("3) Añadir Digimon");
            System.out.println("4) Eliminar Digimon");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Ir atrás");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
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
                case 0:
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);
    }
    
    public static void menuUsuario(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Listar Usuarios");
            System.out.println("2) Mostrar Usuario");
            System.out.println("3) Añadir Usuario");
            System.out.println("4) Eliminar Usuario");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Ir atrás");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
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
                case 0:
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);
    }
    
    public static void menuOpciones(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Usuarios");
            System.out.println("2) Digimones");
            System.out.println("3) Base de datos");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Ir atrás");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
            SLeer1.limpiar();
            
            switch(opcion){
                case 1:
                    menuUsuario();
                    break;
                case 2:
                    menuDigimon();
                    break;
                case 3:
                    menuBaseDatos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);
    }
    
    public static void menuJugar(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Tu equipo vs Usuario");
            System.out.println("2) Tu equipo vs ???");
            System.out.println("3) ??? vs Usuario");
            System.out.println("4) ??? vs ???");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Ir atrás");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
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
                case 0:
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);
    }
    
    public static void menuPrincipal(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Jugar");
            System.out.println("2) Tu equipo");
            System.out.println("3) Tus digimones");
            System.out.println("4) Opciones");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Cerrar sesión");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
            SLeer1.limpiar();
            
            switch(opcion){
                case 1:
                    menuJugar();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    menuOpciones();
                    break;
                case 0:
                    //System.out.println("Cerrando la sesión");
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);
    }
    
    public static void inicioSesion(){}
    
    public static void registro(){}
    
    public static void pantallaInicio(){
        byte opcion = 0;
        do{
            limpiar();
            
            imprimeIguales(20);
            System.out.println("");
            System.out.println("1) Iniciar sesión");
            System.out.println("2) Registrarse");
            imprimeIguales(20);
            System.out.println("");
            System.out.println("0) Salir");
            
            opcion = SLeer1.datoByte("Escoge una opción: ");
            SLeer1.limpiar();
            
            switch(opcion){
                case 1:
                    inicioSesion();
                    break;
                case 2:
                    registro();
                    break;
                case 3:
                    menuPrincipal();
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Debes introducir una opción válida"
                        + " (pulsa Intro para continuar).");
                    SLeer1.limpiar();
            }
        }while(opcion != 0);        
    }
}
