
package com.uncuyo.dbapp.logica;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "registrocomida")
public class RegistroComida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_registro")
    private Long id;
    
    @Column(name = "fecha" , nullable = false)
    private LocalDate fecha;
    
    @Column(name = "hora" , nullable = false)
    private LocalTime hora;  
    
    @ManyToOne
    @JoinColumn(name = "id_usuario" , nullable = false)
    private Usuario usuario;
    
    @JoinColumn(name = "id_comida" , nullable = false)
    @ManyToOne
    private Comida comida;

    public RegistroComida(LocalDate fecha, LocalTime hora, Usuario usuario, Comida comida) {
        this.fecha = fecha;
        this.hora = hora;
        this.usuario = usuario;
        this.comida = comida;
    }

    public RegistroComida() {
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Comida getComida() {
        return comida;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
}
