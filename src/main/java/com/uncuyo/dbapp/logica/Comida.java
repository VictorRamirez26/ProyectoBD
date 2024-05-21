
package com.uncuyo.dbapp.logica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comida")
public class Comida implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comida")
    private Long id;
    
    @Column(name = "nombre" , nullable = false)
    private String nombre;
    
    @Column(name = "descripcion" , nullable = false)
    private String descripcion;
    
    @Column(name = "total_grasas" , nullable = false)
    private Double total_grasas;
    
    @Column(name = "total_calorias" , nullable = false)
    private Double total_calorias;
    
    @Column(name = "total_proteinas" , nullable = false)
    private Double total_proteinas;

    public Comida(String nombre, String descripcion, Double total_grasas, Double total_calorias, Double total_proteinas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.total_calorias = total_calorias;
        this.total_grasas = total_grasas;
        this.total_proteinas = total_proteinas;
    }

    public Comida() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTotal_grasas() {
        return total_grasas;
    }

    public void setTotal_grasas(Double total_grasas) {
        this.total_grasas = total_grasas;
    }

    public Double getTotal_calorias() {
        return total_calorias;
    }

    public void setTotal_calorias(Double total_calorias) {
        this.total_calorias = total_calorias;
    }

    public Double getTotal_proteinas() {
        return total_proteinas;
    }

    public void setTotal_proteinas(Double total_proteinas) {
        this.total_proteinas = total_proteinas;
    }
    
    
    
    
    
    
    
}
