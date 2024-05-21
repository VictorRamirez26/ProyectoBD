package com.uncuyo.dbapp.dao;

import com.uncuyo.dbapp.logica.Usuario;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UsuarioDAOImp implements DAO<Usuario> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");

    @Override
    public void insertar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            if (!existsByCorreo(em, usuario.getCorreo())) {
                em.persist(usuario);
                em.getTransaction().commit();
            } else {
                System.out.println("Ya existe un usuario con el mismo correo electrónico.");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void modificar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Usuario usuarioExistente = findByCorreo(usuario.getCorreo());

            if (usuarioExistente != null){
                // Modificar los datos del usuario existente
                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setAltura(usuario.getAltura());
                usuarioExistente.setContraseña(usuario.getContraseña());
                usuarioExistente.setPeso_actual(usuario.getPeso_actual());
                usuarioExistente.setImc(usuario.getImc());
                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró un usuario con el correo electrónico proporcionado.");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Usuario usuario) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();

            Usuario user = findByCorreo(usuario.getCorreo());
            
            if (user != null) {
                em.remove(user);
                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró un usuario con el correo electrónico proporcionado.");
            }
        } finally {
            em.close();
        }
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private boolean existsByCorreo(EntityManager em, String correo) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo", Long.class);
        query.setParameter("correo", correo);
        return query.getSingleResult() > 0;
    }

    public Usuario findByCorreo(String correo) {
        EntityManager em = getEntityManager();
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :correo", Usuario.class);
        query.setParameter("correo", correo);
        List<Usuario> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null; 
        }
        return resultados.get(0);
    }
    
    public List<Usuario> findUsers() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            
            return query.getResultList();
        } finally {
            em.close();
            
        }
    }
    
    
}
