package com.uncuyo.dbapp.logica;

import com.uncuyo.dbapp.dao.ControladorPersistencia;
import java.util.List;


public class Controlador {
   
    ControladorPersistencia controladorPersistencia = new ControladorPersistencia();
    
    public boolean verificarUsuario(String correo , String contrasenia){
    
        List<Usuario> listaUsuarios = controladorPersistencia.findUsers();
        
        for (Usuario usu : listaUsuarios){
           
            if (usu.getCorreo().equals(correo)){
                
                if (usu.getContraseña().equals(contrasenia)){
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
    
    public void crearUsuario(String nombre, String correo, String contrasenia, Character sexo, Double altura, Double peso) {

        Usuario usuario = new Usuario(nombre, correo, contrasenia, sexo, altura, peso);
        controladorPersistencia.crearUsuario(usuario);
    }

    

    
}
