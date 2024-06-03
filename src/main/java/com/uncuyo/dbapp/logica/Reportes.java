
package com.uncuyo.dbapp.logica;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Reportes {
    
    // Crea el pdf de los registros de comida de un solo usuario
    public void reporteRegistroComida(List<RegistroComida> listaComidas) throws DocumentException, FileNotFoundException{
    
        // Crear el JFileChooser para permitir al usuario elegir la ubicación y el nombre del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de registro de comidas");
        
        // Mostrar el diálogo de guardar archivo
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            // Asegurarse de que el archivo tenga la extensión .pdf por defecto
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            // Crear el documento
            Document documento = new Document();
            
            // Asocia el documento con la ruta de salida
            PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            
            // Abro el documento
            documento.open();
            
            // Titulo del documento
            Paragraph titulo = new Paragraph("Registro de comidas \n \n",
                    FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));
            
            // Añado el titulo
            documento.add(titulo);
            
            // Ajustar el ancho de las columnas
            PdfPTable tabla = new PdfPTable(8);
            float[] columnWidths = {1f, 4f, 2f, 3f, 5f, 3f, 3f, 3f};
            tabla.setWidths(columnWidths);
           
            tabla.addCell("N°");
            tabla.addCell("Fecha");
            tabla.addCell("Hora");
            tabla.addCell("Comida");
            tabla.addCell("Descripción");
            tabla.addCell("Calorías");
            tabla.addCell("Grasas");
            tabla.addCell("Proteínas");
            int j = 1;
            for (RegistroComida rc : listaComidas) {
                tabla.addCell(String.valueOf(j));
                tabla.addCell(rc.getFecha().toString()); // Convertir LocalDate a String
                tabla.addCell(rc.getHora().toString());  // Convertir LocalTime a String
                tabla.addCell(rc.getComida().getNombre());
                tabla.addCell(rc.getComida().getDescripcion());
                tabla.addCell(String.valueOf(rc.getComida().getTotal_calorias())); // Convertir Double a String
                tabla.addCell(String.valueOf(rc.getComida().getTotal_grasas()));   // Convertir Double a String
                tabla.addCell(String.valueOf(rc.getComida().getTotal_proteinas())); // Convertir Double a String
                j++;
            }
            
            // Añado la tabla
            documento.add(tabla);
            
            // Cierro el documento
            documento.close();
            
            // Notificar al usuario que el archivo se guardó correctamente
            JOptionPane.showMessageDialog(null, "El reporte se ha guardado correctamente en:\n" + filePath);
        } else {
            JOptionPane.showMessageDialog(null, "El guardado del archivo fue cancelado.");
        }
    }
    
    // Crea el pdf de todos los usuarios de la BD menos el admin
    public void reporteUsuarios(List<Usuario> listaUsuarios) throws DocumentException, FileNotFoundException{
    
        // Crear el JFileChooser para permitir al usuario elegir la ubicación y el nombre del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de usuarios");
        
        // Mostrar el diálogo de guardar archivo
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            // Asegurarse de que el archivo tenga la extensión .pdf por defecto
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            // Crear el documento
            Document documento = new Document();
            
            // Asocia el documento con la ruta de salida
            PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            
            // Abro el documento
            documento.open();
            
            // Titulo del documento
            Paragraph titulo = new Paragraph("Registro de usuarios \n \n",
                    FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));
            
            // Añado el titulo
            documento.add(titulo);
            
            // Ajustar el ancho de las columnas
            PdfPTable tabla = new PdfPTable(7);
            float[] columnWidths = {1f, 3f, 5f, 1.5f, 1.5f, 1.3f, 2f};
            tabla.setWidths(columnWidths);
           
            tabla.addCell("ID");
            tabla.addCell("Nombre");
            tabla.addCell("Correo");
            tabla.addCell("Peso");
            tabla.addCell("Altura");
            tabla.addCell("Sexo");
            tabla.addCell("IMC");
            int j = 1;
            DecimalFormat df = new DecimalFormat("#.##"); // Definir el formato con dos números después de la coma

            for (Usuario user : listaUsuarios) {
                tabla.addCell(String.valueOf(j)); // ID
                tabla.addCell(user.getNombre());  // Nombre
                tabla.addCell(user.getCorreo()); // Correo
                tabla.addCell(String.valueOf(user.getPeso_actual())); // Peso
                tabla.addCell(String.valueOf(user.getAltura())); // Altura
                tabla.addCell(String.valueOf(user.getSexo()));   // Sexo (convertido a String si es necesario)
                tabla.addCell(String.format("%.2f", user.getImc())); // IMC formateado con dos números después de la coma
                j++;
            }
            
            // Añado la tabla
            documento.add(tabla);
            
            // Cierro el documento
            documento.close();
            
            // Notificar al usuario que el archivo se guardó correctamente
            JOptionPane.showMessageDialog(null, "El reporte se ha guardado correctamente en:\n" + filePath);
        } else {
            JOptionPane.showMessageDialog(null, "El guardado del archivo fue cancelado.");
        }
    }
    
    // Crea el pdf de todas las comidas de la BD
    public void reporteComidas(List<Comida> listaComidas) throws DocumentException, FileNotFoundException{
    
        // Crear el JFileChooser para permitir al usuario elegir la ubicación y el nombre del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte de comidas");
        
        // Mostrar el diálogo de guardar archivo
        int userSelection = fileChooser.showSaveDialog(null);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            
            // Asegurarse de que el archivo tenga la extensión .pdf por defecto
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            
            // Crear el documento
            Document documento = new Document();
            
            // Asocia el documento con la ruta de salida
            PdfWriter.getInstance(documento, new FileOutputStream(filePath));
            
            // Abro el documento
            documento.open();
            
            // Titulo del documento
            Paragraph titulo = new Paragraph("Registro de comidas \n \n",
                    FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));
            
            // Añado el titulo
            documento.add(titulo);
            
            // Ajustar el ancho de las columnas
            PdfPTable tabla = new PdfPTable(6);
            float[] columnWidths = {1f, 3f, 5f, 2f, 2f, 2f};
            tabla.setWidths(columnWidths);
           
            tabla.addCell("N°");
            tabla.addCell("Nombre");
            tabla.addCell("Descripcion");
            tabla.addCell("Calorias");
            tabla.addCell("Grasas");
            tabla.addCell("Proteinas");
            int j = 1;
            DecimalFormat df = new DecimalFormat("#.##"); // Definir el formato con dos números después de la coma

            for (Comida comida : listaComidas) {
                tabla.addCell(String.valueOf(j)); // ID
                tabla.addCell(comida.getNombre());  // Nombre
                tabla.addCell(comida.getDescripcion()); // Descripcion
                tabla.addCell(String.valueOf(comida.getTotal_calorias())); // Calorias
                tabla.addCell(String.valueOf(comida.getTotal_grasas())); // Grasas
                tabla.addCell(String.valueOf(comida.getTotal_proteinas())); // Proteinas
                j++;
            }
            
            // Añado la tabla
            documento.add(tabla);
            
            // Cierro el documento
            documento.close();
            
            // Notificar al usuario que el archivo se guardó correctamente
            JOptionPane.showMessageDialog(null, "El reporte se ha guardado correctamente en:\n" + filePath);
        } else {
            JOptionPane.showMessageDialog(null, "El guardado del archivo fue cancelado.");
        }
    }

    public void reporteTotal(List<RegistroComida> listaComidas) throws DocumentException, FileNotFoundException{

        // Crear el JFileChooser para permitir al usuario elegir la ubicación y el nombre del archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Reporte total");

        // Mostrar el diálogo de guardar archivo
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Asegurarse de que el archivo tenga la extensión .pdf por defecto
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }

            // Crear el documento
            Document documento = new Document();

            // Asocia el documento con la ruta de salida
            PdfWriter.getInstance(documento, new FileOutputStream(filePath));

            // Abro el documento
            documento.open();

            // Titulo del documento
            Paragraph titulo = new Paragraph("Registro de comidas (Todos los usuarios) \n \n",
                    FontFactory.getFont("arial", 22, Font.BOLD, BaseColor.BLACK));

            // Añado el titulo
            documento.add(titulo);

            // Recorrer la lista de comidas y agregarlas como párrafos separados
            for (RegistroComida rc : listaComidas) {
                Paragraph lineaComida = new Paragraph(
                    "Id: " + rc.getUsuario().getId() + " , " +
                    "Nombre: " + rc.getUsuario().getNombre() + " , " +
                    "Correo: " + rc.getUsuario().getCorreo() + " , " +
                    "Comida: " + rc.getComida().getNombre() + " , " +
                    "Descripción: " + rc.getComida().getDescripcion() + " , " +
                    "Calorías: " + rc.getComida().getTotal_calorias() + " , " +
                    "Grasas: " + rc.getComida().getTotal_grasas() + " , " +
                    "Proteínas: " + rc.getComida().getTotal_proteinas()
                );
                documento.add(lineaComida);
            }

            // Cierro el documento
            documento.close();

            // Notificar al usuario que el archivo se guardó correctamente
            JOptionPane.showMessageDialog(null, "El reporte se ha guardado correctamente en:\n" + filePath);
        } else {
            JOptionPane.showMessageDialog(null, "El guardado del archivo fue cancelado.");
        }
    }





}

