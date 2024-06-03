package com.uncuyo.dbapp.logica;

import com.itextpdf.text.DocumentException;
import com.uncuyo.dbapp.dao.ControladorPersistencia;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
    
    public boolean verificarAdministrador(String correo , String contrasenia){
        // Obtener el usuario administrador
        Usuario usuarioAdministrador = controladorPersistencia.getAdministrador();
        
        // Verificar si el usuario administrador existe y si la contraseña coincide
        if (usuarioAdministrador.getCorreo().equals(correo)) {
            // Obtener la contraseña cifrada del usuario administrador
            String hashedPassword = usuarioAdministrador.getContraseña();

            // Verificar si la contraseña proporcionada coincide con la contraseña cifrada del administrador
            return BCrypt.checkpw(contrasenia, hashedPassword);
        } else {
            // Si el usuario administrador no existe, no se puede verificar la contraseña
            return false;
        }
    }
    
    public void reporteRegistroComida(List<RegistroComida> rc) throws DocumentException, FileNotFoundException{
        Reportes reportes = new Reportes();
        reportes.reporteRegistroComida(rc);
    }
    
    public void reporteUsuarios(List<Usuario> listaUsuarios) throws DocumentException, FileNotFoundException{
        Reportes reportes = new Reportes();
        reportes.reporteUsuarios(listaUsuarios);
    }
    
    public void reporteComidas(List<Comida> listaComidas) throws DocumentException, FileNotFoundException{
        Reportes reportes = new Reportes();
        reportes.reporteComidas(listaComidas);
    }
    
    public void reporteTotal(List<RegistroComida> listaRegistroComidas) throws DocumentException, FileNotFoundException{
        Reportes reportes = new Reportes();
        reportes.reporteTotal(listaRegistroComidas);
    }
    
    public List<Usuario> getListaUsuarios(){
        List<Usuario> lista_usuario = controladorPersistencia.getListaUsuarios();
        return lista_usuario;
    }
    
    public List<RegistroComida> getListaRegistros(){
    
        List<RegistroComida> listaRegistros = controladorPersistencia.getListaRegistros();
        return listaRegistros;
    }
    
    public boolean findUser(String correo){
    
        Usuario user = controladorPersistencia.findUserByCorreo(correo);
        return user != null;
    }
    
    public void importarUsuarios() throws IllegalAccessException {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Llamar al método de importación
            BackUp backUp = new BackUp();
            
            backUp.importTableFromCSV(filePath);
                
        }
    }
    
    public void exportarUsuarios() throws IllegalAccessException {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Llamar al método de importación
            BackUp backUp = new BackUp();
            
            backUp.exportTableToCSV(filePath);
                
        }
    }
    
    public void exportarTotalCSV(List<RegistroComida> listaRegistroComidas) throws IllegalAccessException {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Llamar al método de importación
            BackUp backUp = new BackUp();
            
            backUp.exportarReporteCSV(listaRegistroComidas , filePath);
                
        }
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
        
        if (this.getComida(nombre) != null){
            throw new IllegalArgumentException("Ya existe una comida con ese nombre");
        }

        Double total_calorias = Double.parseDouble(calorias);
        Double total_grasas = Double.parseDouble(grasas);
        Double total_proteinas = Double.parseDouble(proteinas);
        Comida comida = new Comida(nombre , descripcion , total_calorias , total_grasas , total_proteinas);
        controladorPersistencia.crearComida(comida);
        return "Comida creada correctamente";
    }
    
    public String actualizarComida(Long idComida, String nombre, String descripcion, String calorias, String grasas, String proteinas) {
        String mensaje = verificarComida(nombre, descripcion, calorias, grasas, proteinas);
        if (!mensaje.equals("Datos correctos")) {
            return mensaje;
        }
        
        Double total_calorias = Double.parseDouble(calorias);
        Double total_grasas = Double.parseDouble(grasas);
        Double total_proteinas = Double.parseDouble(proteinas);
        Comida comida = controladorPersistencia.buscarComidaPorId(idComida);
        
        if (comida == null) {
            return "Comida no encontrada";
        }
        
        comida.setNombre(nombre);
        comida.setDescripcion(descripcion);
        comida.setTotal_calorias(total_calorias);
        comida.setTotal_grasas(total_grasas);
        comida.setTotal_proteinas(total_proteinas);
        
        controladorPersistencia.actualizarComida(comida);
        return "Comida actualizada correctamente";
    }
    
    public boolean buscarRegistroExistente(LocalDate fecha , LocalTime tiempo , Comida comida,String correo){
        Usuario usuario = controladorPersistencia.findUserByCorreo(correo);
        RegistroComida rc = controladorPersistencia.buscarRegistroExistente(fecha,tiempo,comida.getId(),usuario.getId());
        return rc != null;
    }
    
    public void eliminarRegistro(Vector<Object> datosRegistro , String correo){
        Long id_usuario = findIdUser(correo);
        LocalDate fecha = (LocalDate) datosRegistro.get(1);
        LocalTime tiempo = (LocalTime) datosRegistro.get(2);
        Long id_comida = this.getIdComida((String) datosRegistro.get(3));
        RegistroComida rc = controladorPersistencia.buscarRegistroExistente(fecha,tiempo,id_comida,id_usuario);
        controladorPersistencia.eliminarRegistro(rc);
    }
    
    public void eliminarUsuario(Usuario usuario , List<RegistroComida> listaRegistroComidas){
        // LIMPIAR REGISTROS DE UN USUARIO
        this.eliminarRegistros(listaRegistroComidas);
        // ELIMINAR USUARIO
        controladorPersistencia.eliminarUsuario(usuario);
    }
    
    public void eliminarRegistros(List<RegistroComida> listaRegistroComidas){
        if (listaRegistroComidas != null){
            for (RegistroComida rc : listaRegistroComidas){
                controladorPersistencia.eliminarRegistro(rc);
            }
        }
    }
    
    
    public void modificarUsuario(String nombre , String correo , String contrasenia , Character sexo , Double altura , Double peso){ //REVISAR
        String hashedPassword = BCrypt.hashpw(contrasenia, BCrypt.gensalt());
        Usuario usuarioExistente = controladorPersistencia.findUserByCorreo(correo);
        usuarioExistente.setNombre(nombre);
        usuarioExistente.setAltura(altura);
        usuarioExistente.setPeso_actual(peso);
        usuarioExistente.setSexo(sexo);
        usuarioExistente.setImc();
        usuarioExistente.setContraseña(hashedPassword);
        controladorPersistencia.modificarUsuario(usuarioExistente);
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
