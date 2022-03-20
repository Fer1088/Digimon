/*
 * SLeer1.java
 *
 *
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilidades;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class SLeer1 {

    public static Scanner teclado = new Scanner(System.in);

    public static void limpiar() {

        try {
            if (teclado.hasNextLine()) {
                teclado.nextLine();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    ;
    // ********************** MÉTODOS SIN PARÁMETRO MENSAJE ********************
    
    public static char datoChar() {
        boolean leido = false;
        String dato;
        char c = 0;
        do {
            try {
                dato = null;
                c = 0;
                dato = teclado.nextLine();
                //if (!dato.isEmpty() && dato.length() < 2) {
                    c = dato.charAt(0);
                    leido = true;
                /*} else {
                    System.err.println("Error, por favor introduce un carácter...");
                }*/
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Error, por favor introduce un carácter...");
                //limpiar();
            }

        } while (leido == false);
        return c;
    }

    ;
    
    public static String datoString() {
        boolean leido = false;
        String dato = null;
        do {
            try {
                dato = null;
                dato = teclado.nextLine();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return dato;
    }

    public static short datoShort() {
        boolean leido = false;
        short num = 0;
        do {
            try {
                num = 0;
                num = teclado.nextShort();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    public static byte datoByte() {
        boolean leido = false;
        byte num = 0;
        do {
            try {
                num = 0;
                num = teclado.nextByte();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();

            }
        } while (leido == false);
        return num;
    }

    public static int datoInt() {
        boolean leido = false;
        int num = 0;
        do {
            try {
                num = 0;
                num = teclado.nextInt();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    ;
  
  
  public static long datoLong() {
        boolean leido = false;
        long num = 0;
        do {
            try {
                num = 0;
                num = teclado.nextLong();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    public static float datoFloat() {
        boolean leido = false;
        float num = 0;
        do {
            try {
                num = 0;
                num = teclado.nextFloat();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    public static double datoDouble() {
        boolean leido = false;
        double num = 0;
        do {
            try {
                num = 0;
                num = teclado.nextDouble();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    // ********************** MÉTODOS CON PARÁMETRO MENSAJE ********************
    public static char datoChar(String mensaje) {
        boolean leido = false;
        String cadena = null;
        char c = 0;
        do {
            try {
                cadena = null;
                c = 0;
                System.out.print("\n" + mensaje);
                cadena = teclado.nextLine();
                c = cadena.charAt(0);
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Error, por favor introduce un carácter...");
                limpiar();
            }

        } while (leido == false);
        return c;
    }

    ;
    
    public static String datoString(String mensaje) {
        boolean leido = false;
        String dato = null;
        do {
            try {
                dato = null;
                System.out.print("\n" + mensaje);
                dato = teclado.nextLine();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return dato;
    }

    public static short datoShort(String mensaje) {
        boolean leido = false;
        short num = 0;
        do {
            try {
                num = 0;
                System.out.print("\n" + mensaje);
                num = teclado.nextShort();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    public static byte datoByte(String mensaje) {
        boolean leido = false;
        byte num = 0;
        do {
            try {

                num = 0;
                System.out.print("\n" + mensaje);
                num = teclado.nextByte();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();

            }
        } while (leido == false);
        return num;
    }

    public static int datoInt(String mensaje) {
        boolean leido = false;
        int num = 0;
        do {
            try {
                num = 0;
                System.out.print("\n" + mensaje);
                num = teclado.nextInt();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    ;
  
  
  public static long datoLong(String mensaje) {
        boolean leido = false;
        long num = 0;
        do {
            try {
                num = 0;
                System.out.print("\n" + mensaje);
                num = teclado.nextLong();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    public static float datoFloat(String mensaje) {
        boolean leido = false;
        float num = 0;
        do {
            try {
                num = 0;
                System.out.print("\n" + mensaje);
                num = teclado.nextFloat();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

    public static double datoDouble(String mensaje) {
        boolean leido = false;
        double num = 0;
        do {
            try {
                num = 0;
                System.out.print("\n" + mensaje);
                num = teclado.nextDouble();
                leido = true;
            } catch (InputMismatchException e) {
                System.err.println("Error, por favor introduce dato de nuevo...");
                limpiar();
            }
        } while (leido == false);
        return num;
    }

}
