package com.uncuyo.dbapp.logica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id; 
    
    @Column(name = "nombre" , nullable = false)
    private String nombre;
    
    @Column(name = "correo" , nullable = false)
    private String correo;
    
    @Column(name = "contraseña" , nullable = false)
    private String contraseña;
    
    @Column(name = "sexo" , nullable = false)
    private Character sexo;
    
    @Column(name = "altura" , nullable = false)
    private Double altura;
    
    @Column(name = "peso_actual" , nullable = false)
    private Double peso_actual;
    
    @Column(name = "IMC")
    private Double imc;

    public Usuario(String nombre, String correo, String contraseña, Character sexo, Double altura, Double peso_actual) {
        this.nombre = nombre;
        this.correo = correo;
        this.contraseña = contraseña;
        this.sexo = sexo;
        this.altura = altura;
        this.peso_actual = peso_actual;
        this.imc = (peso_actual)/(altura*altura);
    }

    public Usuario() {
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso_actual() {
        return peso_actual;
    }

    public void setPeso_actual(Double peso_actual) {
        this.peso_actual = peso_actual;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }
    
  
    
}
