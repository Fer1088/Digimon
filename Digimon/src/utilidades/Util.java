package utilidades;

import conexion.Conexion;
import digimon.*;
import java.sql.*;
import java.util.*;

/**
 * Una clase para almacenar todas las utilidades que puedan llegar a
 * utilizar otras clases.
 * @version 1.0, 19/03/2022
 * @author jmanuel
 */
public class Util {

    // Métodos para el control de excepciones.
    
    /**
     * Método utilizado para sacar por pantalla información detallada sobre
     * una SQLException, como por ejemplo su código de error o el mensaje
     * que produce.
     * @param ex SQLException sobre la que el método va a trabajar.
     */
    public static void muestraSQLException(SQLException ex){
        ex.printStackTrace(System.err);
        System.err.println("Código SQLState: " + ex.getSQLState());
        System.err.println("Código error: " + ex.getErrorCode());
        System.err.println("Mensaje: " + ex.getMessage());
        Throwable t = ex.getCause();
        while(t != null){
            System.out.println("Causa: " + t);
            t = t.getCause();
        }
    }

    // Métodos para la inicialización de instancias.
    
    /**
     * Inicializa un Usuario dado otrogándole 3 Digimones iniciales de
     * nivel 1 y añadiéndolos a su equipo.
     * @param usuario Usuario que se quiere inicializar.
     * @param usu Un mapa en el que se almacenan todos los Usuarios.
     * @param dig Un mapa en el que se almacenan todos los Digimones.
     * @param usuDig Un mapa que guarda una colección de Digimones para cada Usuario.
     */
    public static void inicializaUsuario(Usuario usuario,HashMap<String,Usuario> usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        HashSet<Digimon> digimones = new HashSet<>();
        usuDig.put(usuario, digimones);
        for(int i=0; i<3; i++){
            otorgaDigimon(usuario,dig,usuDig);
        }
        
        for(Digimon d : usuDig.get(usuario)){
            d.setEstaEquipo(true);
        }
    }
    
    // Métodos para la recolección de datos de la BD.
    
    /**
     * Recoge todos los Digimones encontrados en la BD Digimon y los almacena
     * en una colección HashMap.
     * @param args Argumentos necesarios para conectar con la BD.
     * @return Un mapa en el que se almacenan todos los Digimones.
     * @see Digimon
     * @see Conexion
     */
    public static HashMap<String,Digimon> recogeDigimones(String[] args){
        Conexion c = new Conexion(args);
        c.conectar();
        
        HashMap<String,Digimon> lista = new HashMap<>();
        String[] param = null;
        
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT * FROM Digimon");
            if(res){
                ResultSet rs = st.getResultSet();
                ResultSetMetaData metars = rs.getMetaData();
                param = new String[metars.getColumnCount()];
                while(rs.next()){
                    /*String nomDig = rs.getString(1);
                    int atacDig = rs.getInt(2);
                    int defDig = rs.getInt(3);
                    String tipoDig = rs.getString(4);
                    int nivDig = rs.getInt(5);
                    String nomDigEvo = rs.getString(6);

                    Digimon digimon = new Digimon(nomDig,tipoDig,nivDig,atacDig,defDig,nomDigEvo);

                    lista.put(nomDig, digimon);*/
                    param[0] = rs.getString(1);
                    param[1] = rs.getString(2);
                    param[2] = rs.getString(3);
                    param[3] = rs.getString(4);
                    param[4] = rs.getString(5);
                    param[5] = rs.getString(6);
                    
                    Digimon digimon = new Digimon(param);
                    
                    lista.put(digimon.getNomDig(),digimon);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
        
        return lista;
    }
    
    /**
     * Recoge todos los Usuarios encontrados en la BD Digimon y los almacena
     * en una coleccion HashMap.
     * @param args Argumentos necesarios para conectar con la BD.
     * @return Un mapa en el que se almacenan todos los Usuarios.
     * @see Usuario
     * @see Conexion
     */
    public static HashMap<String,Usuario> recogeUsuarios(String[] args){
        Conexion c = new Conexion(args);
        c.conectar();
        
        HashMap<String,Usuario> lista = new HashMap<>();
        String[] param = null;
        
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT * FROM Usuario");
            if(res){
                ResultSet rs = st.getResultSet();
                ResultSetMetaData metars = rs.getMetaData();
                param = new String[metars.getColumnCount()];
                while(rs.next()){
                    /*String nomUsu = rs.getString(1);
                    String contUsu = rs.getString(2);
                    int partidasGan = rs.getInt(3);
                    int tokensEvo = rs.getInt(4);
                    boolean esAdmin = rs.getBoolean(5);

                    Usuario usuario = new Usuario(nomUsu,contUsu,partidasGan,tokensEvo,esAdmin);
                    
                    lista.put(nomUsu, usuario);*/
                    param[0] = rs.getString(1);
                    param[1] = rs.getString(2);
                    param[2] = rs.getString(3);
                    param[3] = rs.getString(4);
                    if(rs.getBoolean(5)){
                        param[4] = "true";
                    }else{
                        param[4] = "false";
                    }
                    
                    Usuario usuario = new Usuario(param);

                    lista.put(usuario.getNombre(), usuario);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
        
        return lista;
    }
    
    /**
     * Recoge todos los Digimones que tiene cada Usuario según su relación en
     * la BD Digimon y los almacena en una colección HashMap.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usu Un mapa que contiene todos los Usuarios.
     * @param dig Un mapa que contiene todos los Digimones.
     * @return Un mapa que guarda una colección de Digimones para cada Usuario.
     * @see Usuario
     * @see Digimon
     * @see Conexion
     */
    public static HashMap<Usuario,HashSet<Digimon>> recogeUsuDigi(String[] args, HashMap<String,Usuario> usu, HashMap<String,Digimon> dig){
        Conexion c = new Conexion(args);
        c.conectar();
        
        HashMap<Usuario,HashSet<Digimon>> usuDig = new HashMap<>();
        
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT NomUsu FROM Tiene GROUP BY NomUsu");
            if(res){
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    String nomUsu = rs.getString(1);
                    HashSet<Digimon> digimones = new HashSet<>();
                    boolean res1 = st.execute("SELECT NomDig, EstaEquipo FROM Tiene WHERE NomUsu = '" + nomUsu + "'");
                    if(res1){
                        ResultSet rs1 = st.getResultSet();
                        while(rs1.next()){
                            String nomDig = rs1.getString(1);
                            boolean estaEquipo = rs1.getBoolean(2);
                            
                            //Digimon digimon = (Digimon)dig.get(nomDig).clone();
                            Digimon digimon = new Digimon(dig.get(nomDig),estaEquipo);
                            
                            digimones.add(digimon);
                        }
                        usuDig.put(usu.get(nomUsu), digimones);
                    }
                }
            }
        }catch(SQLException ex){
            muestraSQLException(ex);
        }finally{
            c.cerrar();
        }
        
        return usuDig;
    }

    // Métodos para la aleatorización de instancias.
    
    /**
     * Escoge un Digimon al azar.
     * @param dig Una colección de Digimones.
     * @return Un Digimon aleatorio de entre todos los Digimones de
     * la colección.
     * @see Digimon
     */
    public static Digimon randomizaDigimon(Collection<Digimon> dig){
        int numRnd = new Random().nextInt(dig.size());
        Iterator it = dig.iterator();
        
        int indice = 0;
        Digimon digimonRnd = null;
        
        while(it.hasNext()){
            digimonRnd = (Digimon) it.next();
            if(indice == numRnd){
                return digimonRnd;
            }
            indice++;
        }
        return digimonRnd;
    }
    
    /**
     * Escoge un Usuario al azar.
     * @param usu Una colección de Usuarios.
     * @return Un Usuario aleatorio de entre todos los Usuarios de
     * la colección.
     * @see Usuario
     */
    public static Usuario randomizaUsuario(Collection<Usuario> usu){
        int numRnd = new Random().nextInt(usu.size());
        Iterator it = usu.iterator();
        
        int indice = 0;
        Usuario usuarioRnd = null;
        
        while(it.hasNext()){
            usuarioRnd = (Usuario) it.next();
            if(indice == numRnd){
                return usuarioRnd;
            }
            indice++;
        }
        return usuarioRnd;
    }
    
    // Métodos relacionados con el juego en sí.
    
    /**
     * Pide al usuario actual que introduzca el nombre de su contrincante,
     * su contrincante debe ser un Usuario ya existente.
     * @param usu Un Mapa que contiene todos los Usuarios.
     * @return Usuario contrincante
     * @see Usuario
     * @see compruebaNombre
     */
    public static Usuario pideContrincante(HashMap<String,Usuario> usu){
        String nombre = null;
        
        do{
            nombre = SLeer1.datoString("Introduce el nombre de usuario "
                    + "contra el que te quieres enfrentar: ");
            if(!compruebaNombre(nombre,usu)){
                System.err.println("Ese usuario no existe.");
            }
        }while(!compruebaNombre(nombre,usu));

        return usu.get(nombre);
    }
    
    /**
     * Otorga a un Usuario un Digimon de nivel 1 que no tenga en su colección.
     * @param usu Usuario que va a recibir el Digimon.
     * @param dig Un Mapa que contiene todos los Digimones.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     * @see randomizaDigimon
     */
    public static void otorgaDigimon(Usuario usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        int tamano = usuDig.get(usu).size();
        
        HashSet<Digimon> setDig = new HashSet<>(usuDig.get(usu));
        for(Digimon d : usuDig.get(usu)){
            if(d.getNivel() != 1){
                setDig.remove(d);
            }
        }
        
        HashMap<String,Digimon> digimones = new HashMap<>(dig);
        for(Digimon d : dig.values()){
            if(d.getNivel() != 1){
                digimones.remove(d.getNomDig());
            }
        }
        
        if(setDig.size() != digimones.size()){
            do{
                Digimon digRnd = randomizaDigimon(digimones.values());
                String nomDigRnd = digRnd.getNomDig();

                boolean insertar = true;
                String nomDig = null;
                for(Digimon d : setDig){
                    nomDig = d.getNomDig();
                    if(nomDig.equals(nomDigRnd)){
                        insertar = false;
                    }
                }

                if(insertar){
                    usuDig.get(usu).add(digRnd);
                }
            }while(usuDig.get(usu).size() == tamano);
        }
    }

    /**
     * Realiza un combate entre 2 Digimones
     * @param d1 Primer Digimon
     * @param d2 Segundo Digimon
     * @return true si el primer Digimon ha ganado, false si ha perdido.
     * @see Digimon
     */
    public static boolean combate(Digimon d1, Digimon d2){
        return new Random().nextBoolean();        
    }
    
    /**
     * Rellena el equipo de un Usuario con los 3 Digimones que ha asignado
     * para su equipo.
     * @param usu Usuario que ve su equipo rellenado.
     * @param dig Colección de Digimones del Usuario.
     * @return El equipo del Usuario ya completo.
     * @see Usuario
     * @see Digimon
     */
    public static Digimon[] rellenaEquipo(Usuario usu, HashSet<Digimon> dig){
        Digimon[] equipo = new Digimon[3];
        
        int indice = 0;
        for(Digimon d : dig){
            if(d.isEstaEquipo() && indice < 3){
                equipo[indice] = d;
            }
            indice++;
        }
        return equipo;
    }
    
    /**
     * Rellena el equipo de un Usuario con 3 Digimones aleatorios de su
     * colección.
     * @param usu Usuario que ve su equipo rellenado.
     * @param dig Colección de Digimones del Usuario.
     * @return El equipo del Usuario ya completo.
     * @see Usuario
     * @see Digimon
     */
    public static Digimon[] rellenaAleatorio(Usuario usu, HashSet<Digimon> dig){
        Digimon[] equipo = new Digimon[3];
        
        int indice = 0;
        while(indice < 3){
            boolean repetido = false;
            Digimon digimon = randomizaDigimon(dig);
            for(int j=0; j<3; j++){
                if(digimon == equipo[j]){
                    repetido = true;
                }
            }
            if(!repetido){
                equipo[indice] = digimon;
                indice++;
            }
        }        
        return equipo;
    }
    
    /**
     * Incrementa en uno el número de partidas del Usuario y le da un
     * token de digievolución por cada 5 partidas ganadas.
     * @param usu Usuario que recibe el token.
     * @param dig Un Mapa que contiene todos los Digimones.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     */
    public static void ganaPartida(Usuario usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        usu.incPartidasGan();
        if(usu.getPartidasGan() % 5 == 0){
            usu.incTokensEvo();
        }
        if(usu.getPartidasGan() % 10 == 0){
            otorgaDigimon(usu,dig,usuDig);
        }
    }
    
    /**
     * Se realiza una partida entre dos Usuarios.
     * @param u1 Primer Usuario.
     * @param u2 Segundo Usuario.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @param dig Un Mapa que contiene todos los Digimones.
     * @param equipo Si la partida es entre los Digimones que forman parte del
     * equipo de cada Usuario (true), o si es entre Digimones aleatorios
     * de cada Usuario (false).
     * @see Usuario
     * @see Digimon
     * @see rellenaEquipo
     * @see rellenaAleatorio
     * @see ganaPartida
     */
    public static void partida(Usuario u1, Usuario u2, HashMap<Usuario,HashSet<Digimon>> usuDig, HashMap<String,Digimon> dig, boolean equipo){
        Digimon[] equipo1 = null;
        Digimon[] equipo2 = null;
        
        if(equipo){
            equipo1 = rellenaEquipo(u1,usuDig.get(u1));
            equipo2 = rellenaEquipo(u2,usuDig.get(u2));
        }else{
            equipo1 = rellenaAleatorio(u1,usuDig.get(u1));
            equipo2 = rellenaAleatorio(u2,usuDig.get(u2));
        }
        
        int cont = 0;
        for(int i=0; i<3; i++){
            if(combate(equipo1[i],equipo2[i])){
                System.out.println(equipo1[i].getNomDig() + ": " + u1.getNombre());
                cont++;
            }else{
                System.out.println(equipo2[i].getNomDig() + ": " + u2.getNombre());
            }
        }
        
        if(cont > 1){
            ganaPartida(u1,dig,usuDig);
            imprimeIguales(23);
            System.out.println("\nHa ganado " + u1.getNombre());
        }else{
            ganaPartida(u2,dig,usuDig);
            imprimeIguales(23);
            System.out.println("\nHa ganado " + u2.getNombre());
        }        
    }
    
    // Métodos para el formato de texto
    
    /**
     * Imprime 100 saltos de línea para tratar de "limpiar" la salida por
     * consola.
     */
    public static void limpiar(){
        for(int i=0; i<100; i++){
            System.out.println("");
        }
    }
    
    /**
     * Imprime "=" en la misma línea un número de veces determinado.
     * @param n Número de veces que imprime "=".
     */
    public static void imprimeIguales(int n){
        for(int i=0; i<n; i++){
            System.out.print("=");
        }
    }
    
    /**
     * Establece una pausa en el programa pidiendo la entrada de teclado.
     */
    public static void pausa(){
        System.out.println("\nPulsa Intro para continuar");
        SLeer1.limpiar();
    }
    
    // Métodos relacionados con la pantalla de inicio.
    
    /**
     * Comprueba si hay algún Usuario cuyo nombre sea coincidente con
     * la cadena de caracteres pasada por parámetro.
     * @param nombre La cadena de texto cuyo contenido va a ser evaluado.
     * @param usu Un Mapa que contiene todos los Usuarios.
     * @return true si la cadena coincide con el nombre de algún Usuario,
     * false si no coincide con el nombre de ninguno.
     * @see Usuario
     */
    public static boolean compruebaNombre(String nombre,HashMap<String,Usuario> usu){
        return usu.containsKey(nombre);
    }
    
    /**
     * Comprueba si la contraseña pasada por parámetro coincide con
     * la contraseña del Usuario, identificado por su nombre.
     * @param nombre El nombre del Usuario.
     * @param contra Contraseña a evaluar.
     * @param usu Un Mapa que contiene todos los Usuarios.
     * @return true si la contraseña es correcta, false en caso contrario.
     * @see Usuario
     */
    public static boolean compruebaContra(String nombre,String contra,HashMap<String,Usuario> usu){
        return usu.get(nombre).getContrasena().equals(contra);
    }
    
    /**
     * Inicio de sesión para los Usuarios, tanto Administradores como
     * Usuarios comunes.
     * @param usu Un Mapa que contiene todos los Usuarios.
     * @return El Usuario que ha logrado iniciar sesión.
     * @see Usuario
     */
    public static Usuario iniciarSesion(HashMap<String,Usuario> usu){
        String nombre = null;
        String contra = null;
        Usuario usuario = null;
        
        do{
            nombre = SLeer1.datoString("Nombre de usuario: ");
            if(!compruebaNombre(nombre,usu)){
                System.err.println("Ese usuario no existe.");
            }
        }while(!compruebaNombre(nombre,usu));
        
        int contador = 0;
        do{
            contra = SLeer1.datoString("Contraseña: ");
            if(!compruebaContra(nombre,contra,usu)){
                System.err.println("Identificación fallida.");
                contador++;
            }
        }while(!compruebaContra(nombre,contra,usu) && contador != 5);
        if(contador == 5){
            System.err.println("Has agotado tus 5 intentos.");
        }else{
            limpiar();
            System.out.println("¡Bienvenido de nuevo, " + nombre + "!");
            usuario = usu.get(nombre);
        }
        return usuario;
    }
    
    /**
     * Registro de nuevos usuarios.
     * @param usu Un Mapa que contiene todos los Usuarios.
     * @param dig Un Mapa que contiene todos los Digimones.
     * @param usuDig Un Mapa que contiene una colección de Digimones para
     * cada uno de los Usuarios.
     * @see Usuario
     * @see Digimon
     * @see inicializaUsuario
     */
    public static void registrarUsuario(HashMap<String,Usuario> usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        String nombre = null;
        String contra = null;
        Usuario usuario = null;
        
        do{
            nombre = SLeer1.datoString("Introduce el nombre de usuario: ");
            if(compruebaNombre(nombre,usu)){
                System.err.println("Ese usuario ya existe.");
            }
        }while(compruebaNombre(nombre,usu));
        
        contra = SLeer1.datoString("Introduce la contraseña: ");
        String rep = null;
        do{
            rep = SLeer1.datoString("Vuelve a introducirla: ");
            if(!contra.equals(rep)){
                System.err.println("Las contraseñas no coinciden.");
            }
        }while(!contra.equals(rep));
        
        System.out.println("Usuario " + nombre + " registrado.");
        usuario = new Usuario(nombre,contra);
        usu.put(nombre, usuario);
        
        inicializaUsuario(usuario,usu,dig,usuDig);
    }
}