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
     * @param lista Un HashMap en el que se almacenan todos los Usuarios.
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
     * @param lista Un HashMap en el que se almacenan todos los Digimones.
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
     * @param lista Un HashMap en el que se almacenan todos los Digimones.
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
     * @param lista Un HashMap en el que se almacenan todos los Usuarios.
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
                    byte esAdmin = rs.getByte(5);

                    Usuario usuario = new Usuario(nomUsu,contUsu,partidasGan,tokensEvo);
                    if(esAdmin == 1){
                        usuario.setEsAdmin(true);
                    }

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
     * @param usu Un HashMap que contiene todos los Usuarios.
     * @param dig Un HashMap que contiene todos los Digimones.
     * @param usuDig Un HashMap que guarda un HashSet de Digimones para cada Usuario.
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
                            byte estaEquipo = rs1.getByte(2);
                            
                            //Digimon digimon = (Digimon)dig.get(nomDig).clone();
                            Digimon digimon = new Digimon(dig.get(nomDig));
                            if(estaEquipo == 1){
                                digimon.setEstaEquipo(true);
                            }
                            
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
     * @return Un Digimon aleatorio de entre todos los Digimones de la colección.
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
     * Otorga a un Usuario un Digimon que no tenga en su colección.
     * @param usu Usuario que va a recibir el Digimon.
     * @param dig Un HashMap que contiene todos los Digimones.
     * @param usuDig Un HashMap que guarda un HashSet de Digimones para cada Usuario.
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
}
