package com.uncuyo.dbapp.logica;

import com.uncuyo.dbapp.dao.ControladorPersistencia;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;


public class Controlador {
   
    ControladorPersistencia controladorPersistencia = new ControladorPersistencia();
    
    public boolean verificarUsuario(String correo , String contrasenia){
    
        List<Usuario> listaUsuarios = controladorPersistencia.findUsers();
        
        for (Usuario usu : listaUsuarios){
           
            if (usu.getCorreo().equals(correo)){
                
                if (BCrypt.checkpw(contrasenia, usu.getContraseña())){
                    System.out.println("Usuario correcto");
                    return true;
                }else {
                    System.out.println("Contrasenia incorrecta");
                    return false;
                } 
            }  
        }
        return false;
    }
    
    public boolean findUser(String correo){
    
        Usuario user = controladorPersistencia.findUserByCorreo(correo);
        return user != null;
    }
    
    public Long findIdUser(String correo){
        Usuario user = controladorPersistencia.findUserByCorreo(correo);
        return user.getId();
    }
    
    public Usuario getUsuario(String correo){
        Usuario user = controladorPersistencia.findUserByCorreo(correo);
        return user;
    }
    
    public Comida getComida(String nombre){
        Comida comida = controladorPersistencia.getComida(nombre);
        return comida;
    }
    
    public Long getIdComida(String nombre){
        Comida comida = controladorPersistencia.getComida(nombre);
        return comida.getId();
    }
    
    public List<Comida> getListComidas(){
        return controladorPersistencia.getListComidas();
    }
    
    public boolean crearUsuario(String nombre, String correo, String contrasenia, Character sexo, Double altura, Double peso) {
        // Validar si el correo ya existe
        List<Usuario> listaUsuarios = controladorPersistencia.findUsers();
        for (Usuario usu : listaUsuarios) {
            if (usu.getCorreo().equals(correo)) {
                return false; // El correo ya está en uso
            }
        }
        
        // Hashear la contraseña antes de crear el usuario
        String hashedPassword = BCrypt.hashpw(contrasenia, BCrypt.gensalt());
        // Crear el usuario
        Usuario usuario = new Usuario(nombre, correo, hashedPassword, sexo, altura, peso);
        controladorPersistencia.crearUsuario(usuario);
        return true;
    }

    public String crearComida(String nombre, String descripcion, String calorias, String grasas, String proteinas) throws IllegalArgumentException {

        String mensaje = verificarComida(nombre, descripcion, calorias, grasas, proteinas);
        if (!mensaje.equals("Datos correctos")){
            throw new IllegalArgumentException(mensaje);
        }

        Double total_calorias = Double.parseDouble(calorias);
        Double total_grasas = Double.parseDouble(grasas);
        Double total_proteinas = Double.parseDouble(proteinas);
        Comida comida = new Comida(nombre , descripcion , total_calorias , total_grasas , total_proteinas);
        controladorPersistencia.crearComida(comida);
        return "Comida creada correctamente";
    }
    
    public List<RegistroComida> getRegistroComidas(String correo){
        
        Usuario usuario = controladorPersistencia.findUserByCorreo(correo);
        System.out.println(usuario.getNombre());
        List<RegistroComida> registros = controladorPersistencia.getRegistroComidas(usuario);
        return registros;
    }
    
    public String verificarComida(String nombre, String descripcion, String calorias, String grasas, String proteinas) {
        try {
            if (nombre.isEmpty()) {
                return "Ingrese el nombre de la comida";
            }

            if (descripcion.isEmpty()) {
                return "Ingrese una descripción";
            }

            validarNumeroPositivo(calorias, "calorías");
            validarNumeroPositivo(grasas, "grasas");
            validarNumeroPositivo(proteinas, "proteínas");

            return "Datos correctos";

        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }

    private void validarNumeroPositivo(String valor, String nombreCampo) {
        try {
            Double valorDouble = Double.parseDouble(valor);
            if (valorDouble <= 0) {
                throw new IllegalArgumentException("Las " + nombreCampo + " deben ser mayores a cero.");
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Por favor , ingrese un valor para " + nombreCampo + ".");
        }
    }
    
    public void crearRegistro(LocalDate fecha , LocalTime hora , Usuario usuario, Comida comida){
        RegistroComida registroComida = new RegistroComida(fecha, hora, usuario, comida);
        try {
            controladorPersistencia.crearRegistro(registroComida);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error inesperado");
        }
       
    }
    
    
}
