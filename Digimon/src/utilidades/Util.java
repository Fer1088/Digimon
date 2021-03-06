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

    // Métodos para la inicialización y manejo de Usuarios.
    
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
    
    /**
     * Hacer administrador a otro Usuario registrado.
     * @param usu Un mapa en el que se almacenan todos los Usuarios.
     */
    public static void hacerAdmin(HashMap<String,Usuario> usu){
        String nombre = null;
        
        do{
            nombre = SLeer1.datoString("Introduce el nombre del usuario: ");
            if(!compruebaNombre(nombre,usu.values())){
                System.err.println("Ese usuario no existe.");
            }
        }while(!compruebaNombre(nombre,usu.values()));
        
        if(!usu.get(nombre).isEsAdmin()){
            usu.get(nombre).setEsAdmin(true);
            System.out.println("");
            System.out.println("Usuario " + nombre + " es ahora admin.");
        }
        else{
            System.out.println("");
            System.out.println("Usuario " + nombre + " ya era admin.");
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

    // Métodos para la modificación de la BD.
    
    /**
     * Reinicializa la Base de Datos Digimon, dejando únicamente
     * un Usuario administrador 'Admin'.
     * @param args Argumentos necesarios para conectar con la BD.
     * @see Conexion
     * @see muestraSQLException
     */
    public static void reiniciaBD(String[] args){        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(Statement st = c.getConexion().createStatement()){
            st.executeUpdate("SET FOREIGN_KEY_CHECKS=0");
            
            st.executeUpdate("DELETE FROM Tiene");
            st.executeUpdate("DELETE FROM Digimon");
            
            st.executeUpdate("SET FOREIGN_KEY_CHECKS=1");
            
            st.executeUpdate("DELETE FROM Usuario WHERE NomUsu <> 'Admin'");
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Inserta los Usuarios que han sido añadidos desde el programa a
     * la base de datos.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usuarios Un mapa que contiene todos los Usuarios.
     * @see Usuario
     * @see Conexion
     * @see recogeUsuarios
     * @see muestraSQLException
     */
    public static void insertaUsuarios(String[] args, HashMap<String,Usuario> usuarios){
        HashMap<String,Usuario> usuariosBD = recogeUsuarios(args);
        ArrayList<Usuario> nuevos = new ArrayList<>();
        
        for(Usuario u : usuarios.values()){
            if(!usuariosBD.containsKey(u.getNombre())){
                nuevos.add(u);
            }
        }
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(PreparedStatement st = c.getConexion().prepareStatement("INSERT INTO Usuario VALUES (?,?,?,?,?)")){
            for(Usuario u : nuevos){
                st.setString(1, u.getNombre());
                st.setString(2, u.getContrasena());
                st.setInt(3, u.getPartidasGan());
                st.setInt(4, u.getTokensEvo());
                st.setBoolean(5, u.isEsAdmin());
                
                st.executeUpdate();
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Compara los Usuarios existentes en la sesión actual de juego con los
     * encontrados en la BD.
     * @param usu1 Un mapa que contiene todos los Usuarios (de la sesión
     * actual de juego).
     * @param usu2 Un mapa que contiene todos los Usuarios (de la BD).
     * @return Un vector con todos los Usuarios existentes tanto en la BD
     * como en la sesión actual del juego que hayan visto modificado
     * alguno de sus atributos.
     * @see Usuario
     */
    public static ArrayList modUsuarios(HashMap<String,Usuario> usu1, HashMap<String,Usuario> usu2){
        ArrayList<Usuario> modificados = new ArrayList<>();
        
        for(Usuario u : usu1.values()){
            if(usu2.containsKey(u.getNombre())){
                modificados.add(u);
            }
        }
        
        boolean cambia = false;
        for(Usuario u : usu2.values()){
            cambia = false;
            
            for(Usuario v : modificados){
                if(v.getNombre().equals(u.getNombre())){
                    if(v.getTokensEvo() != u.getTokensEvo()){
                        cambia = true;
                    }else if(v.getPartidasGan() != u.getPartidasGan()){
                        cambia = true;
                    }else if(v.isEsAdmin() != u.isEsAdmin()){
                        cambia = true;
                    }
                }
            }
            
            if(!cambia){
                modificados.remove(u);
            }
        }
        
        return modificados;
    }
    
    /**
     * Actualiza en la BD los atributos que hayan cambiado en los Usuarios.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usuarios Un mapa que contiene todos los Usuarios.
     * @see Usuario
     * @see Conexion
     * @see recogeUsuarios
     * @see modUsuarios
     * @see muestraSQLException
     */
    public static void actualizaUsuarios(String[] args, HashMap<String,Usuario> usuarios){
        HashMap<String,Usuario> usuariosBD = recogeUsuarios(args);
        ArrayList<Usuario> modificados = modUsuarios(usuarios, usuariosBD);
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        String consulta = "UPDATE Usuario SET PartidasGan = ?, TokensEvo = ?, EsAdmin = ? WHERE NomUsu = ?";
        try(PreparedStatement st = c.getConexion().prepareStatement(consulta)){
            for(Usuario u : modificados){
                st.setInt(1, u.getPartidasGan());
                st.setInt(2, u.getTokensEvo());
                st.setBoolean(3, u.isEsAdmin());
                st.setString(4, u.getNombre());
                
                st.executeUpdate();
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
        
    }
    
    /**
     * Elimina los Usuarios que han sido borrados desde el programa a 
     * la base de datos.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usuarios Un mapa que contiene todos los Usuarios.
     * @see Usuario
     * @see Conexion
     * @see recogeUsuarios
     * @see muestraSQLException
     */
    public static void eliminaUsuarios(String[] args, HashMap<String,Usuario> usuarios){
        HashMap<String,Usuario> usuariosBD = recogeUsuarios(args);
        ArrayList<Usuario> borrados = new ArrayList<>();
        
        for(Usuario u : usuariosBD.values()){
            if(!usuarios.containsKey(u.getNombre())){
                borrados.add(u);
            }
        }
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(PreparedStatement st = c.getConexion().prepareStatement("DELETE FROM Usuario WHERE NomUsu = ?")){
            for(Usuario u : borrados){
                st.setString(1, u.getNombre());
                
                st.executeUpdate();
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Inserta los Digimones que han sido añadidos desde el programa a
     * la base de datos.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param digimones Un mapa que contiene todos los Digimones.
     * @see Digimon
     * @see Conexion
     * @see recogeUsuarios
     * @see muestraSQLException
     */
    public static void insertaDigimones(String[] args, HashMap<String,Digimon> digimones){
        HashMap<String,Digimon> digimonesBD = recogeDigimones(args);
        ArrayList<Digimon> nuevos = new ArrayList<>();
        
        for(Digimon d : digimones.values()){
            if(!digimonesBD.containsKey(d.getNomDig())){
                nuevos.add(d);
            }
        }
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(Statement s = c.getConexion().createStatement()){
            s.execute("SET FOREIGN_KEY_CHECKS=0");
            try(PreparedStatement st = c.getConexion().prepareStatement("INSERT INTO Digimon VALUES (?,?,?,?,?,?)")){
                for(Digimon d : nuevos){
                    st.setString(1, d.getNomDig());
                    st.setInt(2, d.getAtaque());
                    st.setInt(3, d.getDefensa());
                    st.setString(4, d.getTipo().name());
                    st.setInt(5, d.getNivel());
                    st.setString(6, d.getNomDigEvo());

                    st.executeUpdate();
                }
            }
            s.execute("SET FOREIGN_KEY_CHECKS=1");
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Elimina los Digimones que han sido borrados desde el programa a 
     * la base de datos.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param digimones Un mapa que contiene todos los Digimones.
     * @see Digimon
     * @see Conexion
     * @see recogeUsuarios
     * @see muestraSQLException
     */
    public static void eliminaDigimones(String[] args, HashMap<String,Digimon> digimones){
        HashMap<String,Digimon> digimonesBD = recogeDigimones(args);
        ArrayList<Digimon> borrados = new ArrayList<>();
        
        for(Digimon d : digimonesBD.values()){
            if(!digimones.containsKey(d.getNomDig())){
                borrados.add(d);
            }
        }
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(PreparedStatement st = c.getConexion().prepareStatement("DELETE FROM Digimon WHERE NomDig = ?")){
            for(Digimon d : borrados){
                st.setString(1, d.getNomDig());
                
                st.executeUpdate();
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Señala las diferencias entre dos mapas de Usuarios, con sus
     * respectivos Digimones (cada Usuario de cada uno de los mapas).
     * diferentes.
     * @param usuDig1 Primer mapa de Usuarios y Digimones.
     * @param usuDig2 Segundo mapa de Usuarios y Digimones.
     * @return Un mapa que contiene los nombres de cada Digimon de
     * un Usuario, junto con su condición de estar en el equipo.
     * @see Usuario
     * @see Digimon
     */
    public static HashMap<String,HashMap<String,Boolean>> diffUsuDig(HashMap<Usuario,HashSet<Digimon>> usuDig1, HashMap<Usuario,HashSet<Digimon>> usuDig2){
        HashMap<String,HashMap<String,Boolean>> diff = new HashMap<>();
        
        boolean cambia = false;
        boolean existe = false;
        
        for(Usuario u : usuDig1.keySet()){
            HashMap<String,Boolean> digimones = new HashMap<>();
            cambia = false;
            for(Digimon d : usuDig1.get(u)){
                existe = false;
                if(usuDig2.containsKey(u)){
                    for(Digimon g : usuDig2.get(u)){
                        if(g.getNomDig().equals(d.getNomDig())){
                            existe = true;
                        }
                    }
                }
                if(!existe){
                    digimones.put(d.getNomDig(),d.isEstaEquipo());
                    cambia = true;
                }                
            }
            if(cambia){
                diff.put(u.getNombre(), digimones);
            }
        }
        return diff;
    }
    
    /**
     * Inserta la relación entre un Usuario y varios Digimones, creada
     * desde el programa, en la base de datos.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usuDig Un mapa que guarda una colección de Digimones
     * para cada Usuario.
     * @param usu Un mapa que contiene todos los Usuarios.
     * @param dig Un mapa que contiene todos los Digimones.
     */
    public static void insertaUsuDig(String[] args, HashMap<Usuario,HashSet<Digimon>> usuDig, HashMap<String,Usuario> usu, HashMap<String,Digimon> dig){
        HashMap<Usuario,HashSet<Digimon>> usuDigBD = recogeUsuDigi(args,usu,dig);
        HashMap<String,HashMap<String,Boolean>> nuevos = diffUsuDig(usuDig,usuDigBD);
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(Statement s = c.getConexion().createStatement()){
            s.execute("SET FOREIGN_KEY_CHECKS=0");
            try(PreparedStatement st = c.getConexion().prepareStatement("INSERT INTO Tiene VALUES (?,?,?)")){
                for(String usuario : nuevos.keySet()){
                    for(String digimon : nuevos.get(usuario).keySet()){
                        st.setString(1, digimon);
                        st.setString(2, usuario);
                        st.setBoolean(3, nuevos.get(usuario).get(digimon));

                        st.executeUpdate();
                    }
                }
            }
            s.execute("SET FOREIGN_KEY_CHECKS=1");
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Compara los Usuarios, junto a sus colecciones de Digimones, de la
     * sesión actual de juego con los correspondientes de la BD.
     * @param usuDig1 Un mapa que guarda una colección de Digimones
     * para cada Usuario (de la sesión actual).
     * @param usuDig2 Un mapa que guarda una colección de Digimones
     * para cada Usuario (de la BD).
     * @return Un Mapa con los nombres de los Usuarios y Digimones (junto
     * al valor, de cada uno de los Digimones, de la condición de estar
     * en el equipo de un Usuario) que se encuentran tanto en la sesión
     * actual de juego como en la BD.
     * @see Usuario
     * @see Digimon
     */
    public static HashMap coincideUsuDig(HashMap<Usuario,HashSet<Digimon>> usuDig1, HashMap<Usuario,HashSet<Digimon>> usuDig2){
        HashMap<String,HashMap<String,Boolean>> coinciden = new HashMap<>();
        
        for(Usuario u : usuDig1.keySet()){
            if(usuDig2.containsKey(u)){
                HashMap<String,Boolean> digimones = new HashMap<>();
                for(Digimon d : usuDig1.get(u)){
                    for(Digimon g : usuDig2.get(u)){
                        if(g.getNomDig().equals(d.getNomDig())){
                            digimones.put(d.getNomDig(), d.isEstaEquipo());
                        }
                    }
                }
                coinciden.put(u.getNombre(), digimones);
            }
        }
        
        return coinciden;
    }
    
    /**
     * Compara los valores de estar en equipo de cada Digimon de la
     * colección de cada Usuario en la sesión actual del juego con la BD.
     * @param usuDig1 Un mapa que guarda una colección de Digimones
     * para cada Usuario (de la sesión actual).
     * @param usuDig2 Un mapa que guarda una colección de Digimones
     * para cada Usuario (de la BD).
     * @return Un mapa con los nombres de Usuarios y Digimones de
     * la sesión actual cuyo valor de la condición de "estar en equipo"
     * de cada Digimon es distinto al de su equivalente en la BD.
     * @see Usuario
     * @see Digimon
     * @see coincideUsuDig
     */
    public static HashMap modUsuDig(HashMap<Usuario,HashSet<Digimon>> usuDig1, HashMap<Usuario,HashSet<Digimon>> usuDig2){
        HashMap<String,HashMap<String,Boolean>> modificados = coincideUsuDig(usuDig1, usuDig2);
        HashMap<String,HashMap<String,Boolean>> originales = coincideUsuDig(usuDig2, usuDig2);
        
        for(String u : originales.keySet()){
            for(String d : originales.get(u).keySet()){
                if(Objects.equals(modificados.get(u).get(d), originales.get(u).get(d))){
                    modificados.get(u).remove(d);
                }
            }
        }
        
        return modificados;
    }
    
    /**
     * Actualiza en la Base de Datos el valor de la condición de los Digimones
     * de la colección de cada Usuario de estar en el equipo de dicho Usuario.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usuDig Un mapa que guarda una colección de Digimones
     * para cada Usuario.
     * @param usu Un mapa que contiene todos los Usuarios.
     * @param dig Un mapa que contiene todos los Digimones.
     * @see Usuario
     * @see Digimon
     * @see Conexion
     * @see recogeUsuDigi
     * @see modUsuDig
     * @see muestraSQLException
     */
    public static void actualizaUsuDig(String[] args, HashMap<Usuario,HashSet<Digimon>> usuDig, HashMap<String,Usuario> usu, HashMap<String,Digimon> dig){
        HashMap<Usuario,HashSet<Digimon>> usuDigBD = recogeUsuDigi(args,usu,dig);
        HashMap<String,HashMap<String,Boolean>> modificados = modUsuDig(usuDig, usuDigBD);
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        String consulta = "UPDATE Tiene SET EstaEquipo = ? WHERE NomUsu = ? AND NomDig = ?";
        try(PreparedStatement st = c.getConexion().prepareStatement(consulta)){
            for(String usuario : modificados.keySet()){
                for(String digimon : modificados.get(usuario).keySet()){
                    st.setBoolean(1, modificados.get(usuario).get(digimon));
                    st.setString(2, usuario);
                    st.setString(3, digimon);
                    
                    st.executeUpdate();
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
    }
    
    /**
     * Elimina la relación entre un Usuario y varios Digimones, borrada
     * desde el programa, en la base de datos.
     * @param args Argumentos necesarios para conectar con la BD.
     * @param usuDig Un mapa que guarda una colección de Digimones
     * para cada Usuario.
     * @param usu Un mapa que contiene todos los Usuarios.
     * @param dig Un mapa que contiene todos los Digimones.
     * @see Usuario
     * @see Digimon
     * @see Conexion
     * @see recogeUsuDigi
     * @see diffUsuDig
     * @see muestraSQLException
     */
    public static void eliminaUsuDig(String[] args, HashMap<Usuario,HashSet<Digimon>> usuDig, HashMap<String,Usuario> usu, HashMap<String,Digimon> dig){
        HashMap<Usuario,HashSet<Digimon>> usuDigBD = recogeUsuDigi(args,usu,dig);
        HashMap<String,HashMap<String,Boolean>> borrados = diffUsuDig(usuDigBD,usuDig);
        
        Conexion c = new Conexion(args);
        c.conectar();
        
        try(PreparedStatement st = c.getConexion().prepareStatement("DELETE FROM Tiene WHERE NomDig = ? AND NomUsu = ?")){
            for(String usuario : borrados.keySet()){
                for(String digimon : borrados.get(usuario).keySet()){
                    st.setString(1, digimon);
                    st.setString(2, usuario);
                    
                    st.executeUpdate();
                }
            }
        }catch(SQLException e){
            muestraSQLException(e);
        }finally{
            c.cerrar();
        }
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
    
    /**
     * Escoge un Usuario al azar, excluyendo a un Usuario de la colección.
     * @param usu Una colección de Usuarios.
     * @param u El Usuario a excluir.
     * @return Un Usuario aleatorio de entre todos los Usuarios de
     * la colección (menos uno).
     * @see Usuario
     */
    public static Usuario randomizaUsuario(Collection<Usuario> usu, Usuario u){
        int numRnd = new Random().nextInt(usu.size());
        
        HashSet<Usuario> usuarios = new HashSet<>(usu);
        usuarios.remove(u);
        Iterator it = usuarios.iterator();
        
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
            if(!compruebaNombre(nombre,usu.values())){
                System.err.println("Ese usuario no existe.");
            }
        }while(!compruebaNombre(nombre,usu.values()));

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
        Digimon digimon = null;
        Digimon digRnd = null;
        String nomDigRnd = null;
        
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
                digimon = randomizaDigimon(digimones.values());
                digRnd = new Digimon(digimon,false);
                nomDigRnd = digRnd.getNomDig();

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
     * Calcula la fuerza de un Digimon pasado como parámetro.
     * @param d1 Digimon a analizar.
     * @return La fuerza total del Digimon.
     * @see Digimon
     */
    public static int calcularFuerza(Digimon d1){
        int fuerza = 0;
        
        int ataque = d1.getAtaque() * 60 / 100;
        int defensa = d1.getDefensa() * 40 / 100;
        
        fuerza = ataque + defensa;
        
        return fuerza;
    }
    
    /**
     * Realiza un combate entre 2 Digimones.
     * @param d1 Primer Digimon.
     * @param d2 Segundo Digimon.
     * @return true si el primer Digimon ha ganado, false si ha perdido.
     * @see Digimon
     */
    public static boolean combate(Digimon d1, Digimon d2){
        int fuerza1 = calcularFuerza(d1);
        int fuerza2 = calcularFuerza(d2);
        int suma = fuerza1 + fuerza2;
        
        int porc1 = fuerza1 * 10 / suma;
        int porc2 = fuerza2 * 10 / suma;
        
        int mayor = (porc1 > porc2) ? porc1 : porc2;
        int menor = (porc1 == mayor) ? porc2 : porc1;
        boolean iguales = porc1 == porc2;

        Random rnd = new Random();
        int numRnd = rnd.nextInt(10);

        if(!iguales){
            if(numRnd < mayor){
                return mayor == porc1;
            }
            return menor == porc1;
        }
        
        return rnd.nextBoolean();
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
                indice++;
            }      
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
        Digimon[] d = new Digimon[3];
        HashSet<Digimon> equipo = new HashSet<>(dig);
        while(equipo.size() > 3){
            equipo.remove(randomizaDigimon(equipo));
        }
        
        return equipo.toArray(d);
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
     * @see otorgaDigimon
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
     * @see imprimeIguales
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
        
        System.out.println(u1.getNombre() + " vs " + u2.getNombre());
        imprimeIguales(23);
        System.out.println("");
        
        int cont = 0;
        for(int i=0; i<3; i++){
            System.out.println("Combate " + (i+1));
            System.out.println(equipo1[i].getNomDig() + " vs " + equipo2[i].getNomDig());
            if(combate(equipo1[i],equipo2[i])){
                System.out.println("Ganador: " + equipo1[i].getNomDig() + " de " + u1.getNombre());
                cont++;
            }else{
                System.out.println("Ganador: " + equipo2[i].getNomDig() + " de " + u2.getNombre());
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
    public static boolean compruebaNombre(String nombre,Collection<Usuario> usu){
        for(Usuario u : usu){
            if(u.getNombre().equals(nombre)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Comprueba si hay algún Digimon cuyo nombre sea coincidente con
     * la cadena de caracteres pasada por parámetro.
     * @param nombre La cadena de texto cuyo contenido va a ser evaluado.
     * @param dig Un Mapa que contiene todos los Digimones.
     * @return true si la cadena coincide con el nombre de algún Digimon,
     * false si no coincide con el nombre de ninguno.
     * @see Digimon
     */
    public static boolean compruebaNomDig(String nombre,Collection<Digimon> dig){
        for(Digimon d : dig){
            if(d.getNomDig().equals(nombre)){
                return true;
            }
        }
        return false; 
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
     * Comprueba si el tipo de Digimon que indica el Usuario existe o no.
     * @param tipo El tipo propuesto por el Usuario.
     * @return true si el tipo existe, false en caso contrario.
     * @see Digimon
     */
    public static boolean compruebaTipo(String tipo){
        Digimon.Tipos[] tipos = Digimon.Tipos.values();
        String[] t = new String[tipos.length];
        
        for(int i=0; i<tipos.length; i++){
            t[i] = tipos[i].name();
            if(tipo.equals(t[i])){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Inicio de sesión para los Usuarios, tanto Administradores como
     * Usuarios comunes.
     * @param usu Un Mapa que contiene todos los Usuarios.
     * @return El Usuario que ha logrado iniciar sesión.
     * @see Usuario
     * @see compruebaNombre
     */
    public static Usuario iniciarSesion(HashMap<String,Usuario> usu){
        String nombre = null;
        String contra = null;
        Usuario usuario = null;
        
        do{
            nombre = SLeer1.datoString("Nombre de usuario: ");
            if(!compruebaNombre(nombre,usu.values())){
                System.err.println("Ese usuario no existe.");
            }
        }while(!compruebaNombre(nombre,usu.values()));
        
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
            if(compruebaNombre(nombre,usu.values())){
                System.err.println("Ese usuario ya existe.");
            }
        }while(compruebaNombre(nombre,usu.values()));
        
        contra = SLeer1.datoString("Introduce la contraseña: ");
        String rep = null;
        do{
            rep = SLeer1.datoString("Vuelve a introducirla: ");
            if(!contra.equals(rep)){
                System.err.println("Las contraseñas no coinciden.");
            }
        }while(!contra.equals(rep));
        
        System.out.println("");
        System.out.println("Usuario " + nombre + " registrado.");
        usuario = new Usuario(nombre,contra);
        usu.put(nombre, usuario);
        
        inicializaUsuario(usuario,usu,dig,usuDig);
    }
    
    //Métodos para añadir Digimones y borrar tanto Digimones como Usuarios.
    
    /**
     * Crea un Digimon y lo añade al mapa que los contiene a todos.
     * @param dig Un mapa que contiene todos los Digimones.
     * @see Digimon
     * @see compruebaNomDig
     * @see compruebaTipo
     */
    public static void anyadeDigimon(HashMap<String,Digimon> dig){
        String nomDig = null;
        String tipo = null;
        byte nivel = 1;
        int atq = 0;
        int def = 0;
        String nomDigEvo = null;
        
        char aceptar = 'S';
        
        do{
            nomDig = SLeer1.datoString("Introduce el nombre del Digimon: ");
            if(compruebaNomDig(nomDig,dig.values())){
                System.err.println("Ya existe un Digimon con ese nombre.");
            }
        }while(compruebaNomDig(nomDig,dig.values()));
        
        do{
            System.out.print("Tipos: ");
            Digimon.Tipos[] tipos = Digimon.Tipos.values();

            for(int i=0; i<tipos.length; i++){
                System.out.print(tipos[i]);
                if(!(i == tipos.length - 1)){
                    System.out.print(", ");
                }
            }
            System.out.println("");
            
            tipo = SLeer1.datoString("Introduce el tipo del Digimon: ").toUpperCase();
            if(!compruebaTipo(tipo)){
                System.err.println("Ese tipo no existe.");
            }
        }while(!compruebaTipo(tipo));
        
        do{
            nivel = SLeer1.datoByte("Introduce el nivel del Digimon: ");
            if(nivel < 0 || nivel > 3){
                System.err.println("Su nivel debe estar entre 1 y 3.");
            }
        }while(nivel < 0 || nivel > 3);
        
        atq = SLeer1.datoInt("Introduce su ataque: ");
        def = SLeer1.datoInt("Introduce su defensa: ");
        
        SLeer1.limpiar();
        
        if(nivel != 3){
            aceptar = SLeer1.datoChar("¿Puede digievolucionar? (S/n): ");
            if(aceptar == 'n'){
                aceptar = 'N';
            }

            if(aceptar != 'N'){
                do{
                    nomDigEvo = SLeer1.datoString("Introduce el nombre del Digimon al que quieres que evolucione: ");
                    if(!compruebaNomDig(nomDigEvo,dig.values())){
                        System.err.println("No existe un Digimon con ese nombre.");
                    }
                }while(!compruebaNomDig(nomDigEvo,dig.values()));
            }
        }
        
        Digimon digimon = new Digimon(nomDig,tipo,nivel,atq,def,nomDigEvo);
        dig.put(nomDig,digimon);
        
        System.out.println("");
        System.out.println("Digimon " + nomDig + " añadido");
    }
    
    /**
     * Pide el nombre de un Usuario para eliminar a ese Usuario.
     * @param usu Un mapa que contiene todos los Usuarios.
     * @see Usuario
     * @see compruebaNombre
     */
    public static void borraUsuario(HashMap<String,Usuario> usu){
        String nombre = null;
        
        do{
            nombre = SLeer1.datoString("Introduce el nombre del Usuario: ");
            if(!compruebaNombre(nombre,usu.values())){
                System.err.println("No existe un Usuario con ese nombre.");
            }
        }while(!compruebaNombre(nombre,usu.values()));
        
        usu.remove(nombre);
        System.out.println("");
        System.out.println("Eliminado Usuario: " + nombre);
    }
    
    /**
     * Pide el nombre de un Digimon para eliminar a ese Digimon.
     * @param dig Un mapa que contiene todos los Digimones.
     * @see Digimon
     * @see compruebaNomDig
     */
    public static void borraDigimon(HashMap<String,Digimon> dig){
        String nomDig = null;
        
        do{
            nomDig = SLeer1.datoString("Introduce el nombre del Digimon: ");
            if(!compruebaNomDig(nomDig,dig.values())){
                System.err.println("No existe un Digimon con ese nombre.");
            }
        }while(!compruebaNomDig(nomDig,dig.values()));
        
        dig.remove(nomDig);
        System.out.println("");
        System.out.println("Eliminado Digimon: " + nomDig);
    }
    
    //Métodos para (re)establecer la condición de un Digimon de estar en el equipo.
    
    /**
     * Quita del equipo de un Usuario a todos los Digimones que
     * estuvieran en él.
     * @param u Usuario del que se va a reiniciar el equipo.
     * @param usuDig Un Mapa que contiene una colección de Digimones para
     * cada uno de los Usuarios. 
     * @see Usuario
     * @see Digimon
     */
    public static void reiniciaEquipo(Usuario u, HashMap<Usuario,HashSet<Digimon>> usuDig){
        for(Digimon d : usuDig.get(u)){
            d.setEstaEquipo(false);
        }
    }
    
    /**
     * Establece el equipo de un Usuario con los Digimones que él pide.
     * @param u Usuario del que se va a establecer el equipo.
     * @param usuDig Un Mapa que contiene una colección de Digimones para
     * cada uno de los Usuarios.
     * @see Usuario
     * @see Digimon
     * @see compruebaNomDig
     */
    public static void estableceEquipo(Usuario u, HashMap<Usuario,HashSet<Digimon>> usuDig){
        String nomDig = null;
        boolean yaesta = false;
        
        for(byte i=0; i<3; i++){
            do{
                yaesta = false;
                nomDig = SLeer1.datoString("Introduce el nombre del Digimon " + (i+1) + ": ");
                if(!compruebaNomDig(nomDig,usuDig.get(u))){
                    System.err.println("Ese Digimon no está disponible en tu colección (o simplemente no existe).");
                }else{
                    for(Digimon d : usuDig.get(u)){
                        if(d.getNomDig().equals(nomDig)){
                            if(!d.isEstaEquipo()){
                                d.setEstaEquipo(true);
                            }else{
                                yaesta = true;
                                System.err.println("Ya has metido a ese Digimon en tu equipo.");
                            }
                        }
                    }
                }
            }while(!compruebaNomDig(nomDig,usuDig.get(u)) || yaesta); 
        }
        
        System.out.println("");
        System.out.println("Equipo establecido.");
    }
    
    //Métodos para la digievolución.
    
    /**
     * Otorga a un Usuario un Digimon concreto que no tenga en su colección.
     * @param usu Usuario que va a recibir el Digimon.
     * @param dig Digimon que va a ser recibido por el Usuario.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     */
    public static void otorgaDigimon(Usuario usu, Digimon dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        boolean insertar = true;
        
        for(Digimon d : usuDig.get(usu)){
            if(d.getNomDig().equals(dig.getNomDig())){
                insertar = false;
            }
        }
        
        if(insertar){
            usuDig.get(usu).add(dig);
        }
    }
    
    /**
     * Digievoluciona el Digimon que un Usuario te pida.
     * @param usu Usuario que posee el Digimon.
     * @param dig Un mapa que contiene todos los Digimones.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     * @see otorgaDigimon
     */
    public static void digievolucion(Usuario usu, HashMap<String,Digimon> dig, HashMap<Usuario,HashSet<Digimon>> usuDig){
        String nomDig = null;
        String nomDigEvo = null;
        HashSet<Digimon> digimones = new HashSet<>(usuDig.get(usu));
        
        System.out.println("Tokens restantes: " + usu.getTokensEvo());
        
        do{
            nomDig = SLeer1.datoString("Introduce el nombre del Digimon que quieres digievolucionar (0 para salir): ");
            if(!nomDig.equals("0") && compruebaNomDig(nomDig,digimones)){
                nomDigEvo = dig.get(nomDig).getNomDigEvo();
                if(dig.get(nomDig).getNomDigEvo() == null){
                    System.err.println("Ese Digimon ya ha alcanzado su máximo estado");
                }else if(compruebaNomDig(nomDigEvo,digimones)){
                    System.err.println("Ya tienes su evolución, no puedes tener Digimones repetidos.");
                }else{
                    Digimon digimon = new Digimon(dig.get(nomDigEvo),false);

                    for(Digimon d : digimones){
                        if(d.getNomDig().equals(nomDig)){
                            if(d.isEstaEquipo()){
                                digimon.setEstaEquipo(true);
                            }
                            usuDig.get(usu).remove(d);
                        }
                    }

                    otorgaDigimon(usu,digimon,usuDig);

                    System.out.println("");
                    System.out.println("¡Enhorabuena, tu " + nomDig + " se ha convertido"
                            + " en un " + nomDigEvo + "!");
                    usu.decTokensEvo();
                }
            }
        }while(!nomDig.equals("0"));
    }
    
    //Métodos para mostrar Usuarios y Digimones
    
    /**
     * Lista todos los Usuarios registrados.
     * @param usuarios Un mapa que contiene todos los Usuarios.
     * @see Usuario
     */
    public static void listaUsuarios(HashMap<String,Usuario> usuarios) {
        int indice = 0;
        for (String u : usuarios.keySet()) {
            System.out.print(u);
            indice++;
            if(indice != usuarios.size()){
                System.out.print(", ");
                if(indice % 5 == 0){
                    System.out.println("");
                }
            }
        }
        System.out.println("");
    }

    /**
     * Lista todos los Digimones añadidos.
     * @param digimones Un mapa que contiene todos los Digimones.
     * @see Digimon
     */
    public static void listaDigimons(HashMap<String,Digimon> digimones) {
        int indice = 0;
        for (String d : digimones.keySet()) {
            System.out.print(d);
            indice++;
            if(indice != digimones.size()){
                System.out.print(", ");
                if(indice % 5 == 0){
                    System.out.println("");
                }
            }
        }
        System.out.println("");
    }

    /**
     * Lista todos los Digimones de un Usuario concreto.
     * @param usuario Usuario del que se van a listar los Digimones.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     */
    public static void listaDigimonsUsuario(Usuario usuario, HashMap<Usuario,HashSet<Digimon>> usuDig) {
        System.out.println("Tus Digimones: ");
        int indice = 0;
        for (Digimon d : usuDig.get(usuario)) {
            System.out.print(d.getNomDig());
            indice++;
            if(indice != usuDig.get(usuario).size()){
                System.out.print(", ");
                if(indice % 5 == 0){
                    System.out.println("");
                }
            }
        }
        System.out.println("");
    }
    
    /**
     * Lista los Digimones de un Usuario concreto que están en su equipo.
     * @param usuario Usuario del que se van a listar los Digimones.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon
     */
    public static void listaDigimonsEquipo(Usuario usuario, HashMap<Usuario,HashSet<Digimon>> usuDig){
        System.out.println("Tu Equipo: ");
        int indice = 0;
        for (Digimon d : usuDig.get(usuario)) {
            if(d.isEstaEquipo()){
                System.out.print(d.getNomDig());
                indice++;
                if(indice != 3){
                    System.out.print(", ");
                }
            }
        }
        System.out.println("");
    }
    
    /**
     * Lista los Digimones de un Usuario concreto que pueden digievolucionar.
     * @param usuario Usuario del que se van a listar los Digimones.
     * @param usuDig Un Mapa que guarda una colección de Digimones para
     * cada Usuario.
     * @see Usuario
     * @see Digimon 
     */
    public static void listaDigiEvolucionables(Usuario usuario, HashMap<Usuario,HashSet<Digimon>> usuDig){
        System.out.println("Digimones que pueden digievolucionar: ");
        int indice = 0;
        for (Digimon d : usuDig.get(usuario)) {
            if(d.getNomDigEvo() != null){
                System.out.print(d.getNomDig());
                indice++;
                if(indice != usuDig.get(usuario).size()){
                    System.out.print(", ");
                    if(indice % 5 == 0){
                        System.out.println("");
                    }
                }
            }
        }
        System.out.println("");
    }

    /**
     * Muestra detalladamente los datos de un Usuario concreto.
     * @param usuarios Un mapa que contiene todos los Usuarios.
     * @see Usuario
     */
    public static void muestraUsuario(HashMap<String,Usuario> usuarios) {
        String nombre = null;
        
        do{
            nombre = SLeer1.datoString("Introduce el nombre del Usuario: ");
            if(!usuarios.containsKey(nombre)){
                System.err.println("No existe un Usuario con ese nombre.");
            }
        }while(!usuarios.containsKey(nombre));
        
        Usuario usuario = usuarios.get(nombre);
        
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Contraseña: " + usuario.getContrasena());
        System.out.println("Partidas ganadas: " + usuario.getPartidasGan());
        System.out.println("Tokens digievolución: " + usuario.getTokensEvo());
        if(usuario.isEsAdmin()){
            System.out.println("Administrador");
        }else{
            System.out.println("Usuario");
        }
        
        System.out.println("");
    }

    /**
     * Muestra detalladamente los datos de un Digimon concreto.
     * @param digimones Un mapa que contiene todos los Digimones.
     * @see Digimon
     */
    public static void muestraDigimon(HashMap<String,Digimon> digimones) {
        String nomDig = null;
        
        do{
            nomDig = SLeer1.datoString("Introduce el nombre del Digimon: ");
            if(!digimones.containsKey(nomDig)){
                System.err.println("No existe un Digimon con ese nombre.");
            }
        }while(!digimones.containsKey(nomDig));
        
        Digimon digimon = digimones.get(nomDig);
        
        System.out.println("Nombre: " + digimon.getNomDig());
        System.out.println("Tipo: " + digimon.getTipo().name());
        System.out.println("Ataque: " + digimon.getAtaque());
        System.out.println("Defensa: " + digimon.getDefensa());
        System.out.println("Nivel: " + digimon.getNivel());
        if(digimon.getNomDigEvo() != null){
            System.out.println("Digievoluciona a: " + digimon.getNomDigEvo());
        }
        
        System.out.println("");
    }

}