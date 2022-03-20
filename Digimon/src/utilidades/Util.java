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
    
    /**
     * Crea un usuario y lo añade a una colección que almacena todos los
     * usuarios como valores, cada usuario es identificado por una clave
     * definida con su nombre.
     * @param nombre El nombre que se le quiere dar al Usuario.
     * @param contrasena La contraseña que se le quiere dar al Usuario.
     * @param lista Un mapa en el que se almacenan todos los Usuarios.
     * @see Usuario 
     */
    public static void creaUsuario(String nombre, String contrasena, HashMap<String,Usuario> lista){
        Usuario usuario = new Usuario(nombre, contrasena);
        try{
            if(lista.containsKey(usuario.getNombre())) {
                throw new Exception("Ya existe un Usuario con ese nombre.");
            }
            else{
                lista.put(usuario.getNombre(), usuario);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Crea un digimon y lo añade a una colección que almacena todos los
     * digimones como valores, cada digimon es identificado por una clave
     * definida con su nombre.
     * @param nombre El nombre que se le quiere dar al Digimon.
     * @param tipo El tipo que se le quiere dar al Digimon.
     * @param ataque El ataque (entero) que se le quiere dar al Digimon.
     * @param defensa La defensa (entero) que se le quiere dar al Digimon.
     * @param nivel El nivel (entero) que se le quiere dar al Digimon.
     * @param nomEvo El nombre del Digimon al que podrá evolucionar.
     * @param lista Un mapa en el que se almacenan todos los Digimones.
     * @see Digimon
     */
    public static void creaDigimon(String nombre, String tipo, int ataque, int defensa, int nivel, String nomEvo, HashMap<String,Digimon> lista){
        Digimon digimon = new Digimon(nombre,tipo,nivel,ataque,defensa,nomEvo);
        try{
            if(lista.containsKey(digimon.getNomDig())) {
                throw new Exception("Ya existe un Digimon con ese nombre.");
            }
            else{
                lista.put(digimon.getNomDig(), digimon);
            }
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Recoge todos los Digimones encontrados en la BD Digimon y los almacena
     * en una colección HashMap.
     * @param c Conexión a la BD Digimon.
     * @param lista Un mapa en el que se almacenan todos los Digimones.
     * @see Digimon
     * @see Conexion
     */
    public static void recogeDigimones(Conexion c, HashMap<String,Digimon> lista){
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT * FROM Digimon");
            if(res){
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    String nomDig = rs.getString(1);
                    int atacDig = rs.getInt(2);
                    int defDig = rs.getInt(3);
                    String tipoDig = rs.getString(4);
                    int nivDig = rs.getInt(5);
                    String nomDigEvo = rs.getString(6);

                    Digimon digimon = new Digimon(nomDig,tipoDig,nivDig,atacDig,defDig,nomDigEvo);

                    lista.put(nomDig, digimon);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }    
    }
    
    /**
     * Recoge todos los Usuarios encontrados en la BD Digimon y los almacena
     * en una coleccion HashMap.
     * @param c Conexión a la BD Digimon.
     * @param lista Un mapa en el que se almacenan todos los Usuarios.
     * @see Usuario
     * @see Conexion
     */
    public static void recogeUsuarios(Conexion c, HashMap<String,Usuario> lista){
        try(Statement st = c.getConexion().createStatement()){
            boolean res = st.execute("SELECT * FROM Usuario");
            if(res){
                ResultSet rs = st.getResultSet();
                while(rs.next()){
                    String nomUsu = rs.getString(1);
                    String contUsu = rs.getString(2);
                    int partidasGan = rs.getInt(3);
                    int tokensEvo = rs.getInt(4);
                    boolean esAdmin = rs.getBoolean(5);

                    Usuario usuario = new Usuario(nomUsu,contUsu,partidasGan,tokensEvo,esAdmin);

                    lista.put(nomUsu, usuario);
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }    
    }
    
    /**
     * Recoge todos los Digimones que tiene cada Usuario según su relación en
     * la BD Digimon y los almacena en una colección HashMap.
     * @param c Conexión a la BD Digimon.
     * @param usu Un mapa que contiene todos los Usuarios.
     * @param dig Un mapa que contiene todos los Digimones.
     * @param usuDig Un mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     * @see Conexion
     */
    public static void recogeUsuDigi(Conexion c, HashMap<String,Usuario> usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
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
        }
    }
    
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
    
    /**
     * Otorga a un Usuario un Digimon que no tenga en su colección.
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
        
        if(usuDig.get(usu).size() != dig.values().size()){
            do{
                Digimon digRnd = randomizaDigimon(dig.values());
                String nomDigRnd = digRnd.getNomDig();

                boolean insertar = true;
                String nomDig = null;
                for(Digimon d : usuDig.get(usu)){
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
     * Da un token de digievolución a un Usuario por cada 5 partidas ganadas.
     * @param usu Usuario que recibe el token.
     */
    public static void darToken(Usuario usu){
        if(usu.getPartidasGan() % 5 == 0){
            usu.incTokensEvo();
        }
    }
    
    /**
     * Se realiza una partida entre dos Usuarios.
     * @param u1 Primer Usuario.
     * @param u2 Segundo Usuario.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @param equipo Si la partida es entre los Digimones que forman parte del
     * equipo de cada Usuario, o si es entre Digimones aleatorios
     * de cada Usuario.
     */
    public static void partida(Usuario u1, Usuario u2, HashMap<Usuario,HashSet<Digimon>> usuDig, boolean equipo){
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
            u1.incPartidasGan();
            darToken(u1);
        }else{
            u2.incPartidasGan();
            darToken(u2);
        }
        
    }
}
