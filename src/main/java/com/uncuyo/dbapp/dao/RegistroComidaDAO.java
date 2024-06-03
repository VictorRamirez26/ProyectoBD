
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

            // Buscar la entidad gestionada
            RegistroComida registroComida = em.find(RegistroComida.class, registro.getId());
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
    
    public List<RegistroComida> findRegistros(Long id) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RegistroComida> query = em.createQuery("SELECT r FROM RegistroComida r WHERE r.usuario.id = :id", RegistroComida.class);
        query.setParameter("id", id);
        List<RegistroComida> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados;
    }
    
    public RegistroComida buscarRegistroExistente(LocalDate fecha , LocalTime tiempo , Long id_comida , Long id_usuario){
        
        EntityManager em = emf.createEntityManager();
        String consulta = "SELECT r FROM RegistroComida r WHERE r.fecha = :fecha AND r.hora = :tiempo AND r.comida.id = :id_comida AND r.usuario.id = :id_usuario";
        TypedQuery<RegistroComida> query = em.createQuery(consulta , RegistroComida.class);
        query.setParameter("fecha",fecha);
        query.setParameter("tiempo",tiempo);
        query.setParameter("id_comida",id_comida);
        query.setParameter("id_usuario", id_usuario);
        List<RegistroComida> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);  
    }
    
    public List<RegistroComida> getListaRegistros(){
        EntityManager em = emf.createEntityManager();
        String consulta = "SELECT r FROM RegistroComida r";
        TypedQuery<RegistroComida> query = em.createQuery(consulta , RegistroComida.class);
        List<RegistroComida> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados;      
    }
    
}
