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
    
    public Usuario getAdministrador(){
        userDao = new UsuarioDAOImp();
        Usuario usuarioAdmin = userDao.findCorreoAdmin();  
        return usuarioAdmin;
    }
    
    public List<RegistroComida> getListaRegistros(){
        registroDao = new RegistroComidaDAO();
        return registroDao.getListaRegistros();
    }
    
    public void crearUsuario(Usuario usuario){
        userDao = new UsuarioDAOImp();
        userDao.insertar(usuario);
    }
    
    public void modificarUsuario(Usuario usuario){
        userDao = new UsuarioDAOImp();
        userDao.modificar(usuario);
    }
    
    public List<Usuario> getListaUsuarios(){
        userDao = new UsuarioDAOImp();
        return userDao.getListaUsuarios();
    }
    
    
    public void crearComida(Comida comida){
        comidaDao = new ComidaDAOImp();
        comidaDao.insertar(comida);
    }
    
    public Comida buscarComidaPorId(Long idComida){
        comidaDao = new ComidaDAOImp();
        return comidaDao.findById(idComida);
    }
    
    public void actualizarComida(Comida comida){
        comidaDao = new ComidaDAOImp();
        comidaDao.actualizarComida(comida);
    }
    
    public List<RegistroComida> getRegistroComidas(Usuario usuario){
    
        registroDao = new RegistroComidaDAO();
        List<RegistroComida> registros = registroDao.findRegistros(usuario.getId());
        return registros;
    }  
    
    public List<Comida> getListComidas(){
    
        comidaDao = new ComidaDAOImp();
        return comidaDao.getListComidas();
    }
    
    public Comida getComida(String nombre){
        comidaDao = new ComidaDAOImp();
        return comidaDao.findByName(nombre);
    }
    
    public void crearRegistro(RegistroComida registroComida){
    
        registroDao = new RegistroComidaDAO();
        registroDao.insertar(registroComida);
    }
    
    public RegistroComida buscarRegistroExistente(LocalDate fecha , LocalTime tiempo , Long id_comida,Long id_usuario){
        registroDao = new RegistroComidaDAO();
        RegistroComida rc = registroDao.buscarRegistroExistente(fecha,tiempo,id_comida,id_usuario);
        return rc;
    }
    
    public void eliminarRegistro(RegistroComida rc){
        registroDao = new RegistroComidaDAO();
        registroDao.eliminar(rc);
    }
    
    public void eliminarUsuario(Usuario usuario){
        userDao = new UsuarioDAOImp();
        userDao.eliminar(usuario);
    }
    
}
