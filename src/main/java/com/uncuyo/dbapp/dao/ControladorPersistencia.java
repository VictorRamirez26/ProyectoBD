package com.uncuyo.dbapp.dao;

import antlr.CppCodeGenerator;
import com.uncuyo.dbapp.dao.ComidaDAOImp;
import com.uncuyo.dbapp.dao.RegistroComidaDAO;
import com.uncuyo.dbapp.dao.UsuarioDAOImp;
import com.uncuyo.dbapp.logica.Comida;
import com.uncuyo.dbapp.logica.RegistroComida;
import com.uncuyo.dbapp.logica.Usuario;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ControladorPersistencia {
    private UsuarioDAOImp userDao;
    private ComidaDAOImp comidaDao;
    private RegistroComidaDAO registroDao;
    
    
    public List<Usuario> findUsers(){
        userDao = new UsuarioDAOImp();
        List<Usuario> usuarios = userDao.findUsers();
        return usuarios;
    }
    
    public Usuario findUserByCorreo(String correo){
        userDao = new UsuarioDAOImp();
        Usuario usuario = userDao.findByCorreo(correo);
        return usuario;
    }
    
    public void crearUsuario(Usuario usuario){
        userDao = new UsuarioDAOImp();
        userDao.insertar(usuario);
    }
    
    public void crearComida(Comida comida){
        comidaDao = new ComidaDAOImp();
        comidaDao.insertar(comida);
    }
    
    public List<RegistroComida> getRegistroComidas(Usuario usuario){
    
        registroDao = new RegistroComidaDAO();
        List<RegistroComida> registros = registroDao.findRegistros(usuario.getId());
        return registros;
    }  
}
