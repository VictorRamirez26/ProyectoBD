package com.uncuyo.dbapp.logica;

import com.uncuyo.dbapp.dao.ControladorPersistencia;
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

    public List<RegistroComida> getRegistroComidas(String correo){
        
        Usuario usuario = controladorPersistencia.findUserByCorreo(correo);
                
        List<RegistroComida> registros = controladorPersistencia.getRegistroComidas(usuario);
        return registros;
    }
}
