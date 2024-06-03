/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.uncuyo.dbapp.logica;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.uncuyo.dbapp.dao.UsuarioDAOImp;
import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.persistence.TypedQuery;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

public class BackUp {

    private EntityManagerFactory emf;

    public BackUp() {
        emf = Persistence.createEntityManagerFactory("Persistencia");
    }

    public void importTableFromCSV(String filePath) {
        EntityManager em = emf.createEntityManager();
        UsuarioDAOImp usuarioDAO = new UsuarioDAOImp();
        
        try (FileReader fileReader = new FileReader(filePath); CSVReader csvReader = new CSVReader(fileReader)) {
            String[] nextRecord;
            // Ignorar la primera fila (encabezados de columna)
            csvReader.skip(1);
            em.getTransaction().begin();
            while ((nextRecord = csvReader.readNext()) != null) {
                // Verificar si el usuario ya existe en la base de datos
                String correo = nextRecord[1]; // Suponiendo que la columna del correo es la segunda
                if (usuarioDAO.findByCorreo(correo) == null) {
                    // El usuario no existe, entonces procede a insertarlo
                    Usuario usuario = parseUsuarioFromCSV(nextRecord);
                    em.persist(usuario);
                }
            }
            JOptionPane.showMessageDialog(null, "Se ingresaron los datos correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            em.getTransaction().commit();
        } catch (IOException | CsvValidationException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    private Usuario parseUsuarioFromCSV(String[] values) {
        // Aquí debes escribir el código para crear un objeto Usuario a partir de los datos del CSV
        // Por ejemplo:
        String nombre = values[0]; // Suponiendo que la columna del nombre es la primera
        String correo = values[1]; // Suponiendo que la columna del correo es la segunda
        String contraseña = values[2]; // Suponiendo que la columna de la contraseña es la tercera
        String hashedPassword = BCrypt.hashpw(contraseña, BCrypt.gensalt());
        Character sexo = values[3].charAt(0); // Suponiendo que la columna del sexo es la cuarta
        Double altura = Double.parseDouble(values[4]); // Suponiendo que la columna de la altura es la quinta
        Double peso_actual = Double.parseDouble(values[5]); // Suponiendo que la columna del peso actual es la sexta
        return new Usuario(nombre, correo, hashedPassword, sexo, altura, peso_actual);
    }
    
    public void exportTableToCSV(String fileName) {
        // Agregar la extensión .csv al nombre del archivo si no está presente
        if (!fileName.toLowerCase().endsWith(".csv")) {
            fileName += ".csv";
        }

        String filePath = fileName; // Ruta completa del archivo

        EntityManager em = emf.createEntityManager();
        try {
            // Recuperar todos los usuarios de la base de datos
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            List<Usuario> usuarios = query.getResultList();

            // Verificar que haya usuarios para exportar
            if (usuarios.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay usuarios para exportar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Crear FileWriter para escribir en el archivo CSV
            FileWriter writer = new FileWriter(filePath);

            // Escribir encabezados de columna
            writer.append("Nombre,Correo,Contraseña,Sexo,Altura,Peso Actual");
            writer.append("\n");

            // Escribir datos de cada usuario en el archivo CSV
            for (Usuario usuario : usuarios) {
                writer.append(usuario.getNombre()).append(",");
                writer.append(usuario.getCorreo()).append(",");
                writer.append(usuario.getContraseña()).append(",");
                writer.append(usuario.getSexo().toString()).append(",");
                writer.append(usuario.getAltura().toString()).append(",");
                writer.append(usuario.getPeso_actual().toString());
                writer.append("\n");
            }

            // Cerrar FileWriter
            writer.flush();
            writer.close();

            JOptionPane.showMessageDialog(null, "Se exportaron los datos correctamente.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            em.close();
        }
    }
    
    public void exportarReporteCSV(List<RegistroComida> listaComidas, String filePath) {
        // Si la ruta del archivo no termina con .csv, agregar automáticamente la extensión
        if (!filePath.toLowerCase().endsWith(".csv")) {
            filePath += ".csv";
        }

        // Crear el FileWriter para escribir en el archivo CSV
        try (FileWriter writer = new FileWriter(filePath)) {
            // Escribir encabezados de columna
            writer.append("Id,Nombre,Correo,Comida,Descripción,Calorías,Grasas,Proteínas");
            writer.append("\n");

            // Escribir datos de cada registro de comida en el archivo CSV
            for (RegistroComida rc : listaComidas) {
                writer.append(rc.getUsuario().getId().toString()).append(",");
                writer.append(rc.getUsuario().getNombre()).append(",");
                writer.append(rc.getUsuario().getCorreo()).append(",");
                writer.append(rc.getComida().getNombre()).append(",");
                writer.append(rc.getComida().getDescripcion()).append(",");
                writer.append(rc.getComida().getTotal_calorias().toString()).append(",");
                writer.append(rc.getComida().getTotal_grasas().toString()).append(",");
                writer.append(rc.getComida().getTotal_proteinas().toString());
                writer.append("\n");
            }

            // Notificar al usuario que el archivo se guardó correctamente
            JOptionPane.showMessageDialog(null, "El reporte se ha guardado correctamente en:\n" + filePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar el reporte: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


   
}