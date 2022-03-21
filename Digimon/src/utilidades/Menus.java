/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

/**
 * Una clase para almacenar todos los menús que van a usarse en el programa.
 * @version 1.0, 20/03/2022
 * @author jmanuel
 */
public class Menus {
    
    /**
     * Imprime las opciones relacionadas con la BD.
     */
    public static void menuBaseDatos(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Actualizar registros");
        System.out.println("2) Reinicializar BD");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Ir atrás");    
    }
    
    /**
     * Imprime las opciones relacionadas con los Digimones.
     */
    public static void menuDigimon(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Listar Digimones");
        System.out.println("2) Mostrar Digimon");
        System.out.println("3) Añadir Digimon");
        System.out.println("4) Eliminar Digimon");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Ir atrás");
    }
    
    /**
     * Imprime las opciones relacionadas con los Usuarios.
     */
    public static void menuUsuario(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Listar Usuarios");
        System.out.println("2) Mostrar Usuario");
        System.out.println("3) Añadir Usuario");
        System.out.println("4) Eliminar Usuario");
        System.out.println("5) Hacer Admin");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Ir atrás");
    }
    
    /**
     * Imprime las opciones de administrador del programa.
     */
    public static void menuOpciones(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Usuarios");
        System.out.println("2) Digimones");
        System.out.println("3) Base de datos");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Ir atrás");
    }
    
    /**
     * Imprime los modos de juego del programa.
     */
    public static void menuJugar(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Tu equipo vs Usuario");
        System.out.println("2) Tu equipo vs ???");
        System.out.println("3) ??? vs Usuario");
        System.out.println("4) ??? vs ???");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Ir atrás");
    }
    
    /**
     * Imprime el menú principal del programa para un Usuario normal.
     */
    public static void menuPrincipal(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Jugar");
        System.out.println("2) Tu equipo");
        System.out.println("3) Tus digimones");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Cerrar sesión");
    }
    
    /**
     * Imprime el menú principal del programa para un administrador.
     */
    public static void menuPrincipalAdmin(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Jugar");
        System.out.println("2) Tu equipo");
        System.out.println("3) Tus digimones");
        System.out.println("4) Opciones");

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Cerrar sesión");
    }
    
    /**
     * Imprime la pantalla de inicio del programa.
     */
    public static void pantallaInicio(){
        Util.limpiar();

        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("1) Iniciar sesión");
        System.out.println("2) Registrarse");
        
        Util.imprimeIguales(23);
        System.out.println("");
        System.out.println("0) Salir");
    }
}
