
package com.uncuyo.dbapp.dao;

import com.uncuyo.dbapp.logica.Comida;
import com.uncuyo.dbapp.logica.RegistroComida;
import com.uncuyo.dbapp.logica.Usuario;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


public class RegistroComidaDAO implements DAO<RegistroComida>{
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");

    @Override
    public void insertar(RegistroComida registro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RegistroComida registroComida = findByDate(registro.getFecha() , registro.getHora());
            
            if (registroComida == null){
                em.persist(registro);
                em.getTransaction().commit();
            }else {
                System.out.println("Ya hay un registro con esas características");
            }

        } finally {
            em.close();
        }
    }

    @Override
    public void modificar(RegistroComida registro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            RegistroComida registroComida = findByDate(registro.getFecha() ,registro.getHora() );
            
            if (registroComida != null) {
                
                registroComida.setComida(registro.getComida());
                registroComida.setUsuario(registro.getUsuario());
                registroComida.setFecha(registro.getFecha());
                registroComida.setHora(registro.getHora());

                em.getTransaction().commit();
                
            } else {
                System.out.println("No se encontró ese registro");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(RegistroComida registro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            RegistroComida registroComida = findByDate(registro.getFecha() , registro.getHora());
            if (registroComida != null) {
                em.remove(registroComida);
                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró ese registro");
            }
        } finally {
            em.close();
        }
    }

    public RegistroComida findByDate(LocalDate fecha , LocalTime hora) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RegistroComida> query = em.createQuery("SELECT r FROM RegistroComida r WHERE r.fecha = :fecha AND r.hora = :hora", RegistroComida.class);
        query.setParameter("fecha", fecha);
        query.setParameter("hora", hora);
        List<RegistroComida> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }    
}
