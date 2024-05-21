package com.uncuyo.dbapp.dao;

import com.uncuyo.dbapp.logica.Comida;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ComidaDAOImp implements DAO<Comida> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Persistencia");

    @Override
    public void insertar(Comida comida) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Comida comidaExistente = findByName(comida.getNombre());
            
            if (comidaExistente == null){
                em.persist(comida);
                em.getTransaction().commit();
            }else {
                System.out.println("Ya se encontró una comida con ese nombre");
            }

        } finally {
            em.close();
        }
    }

    @Override
    public void modificar(Comida comida) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Comida comidaExistente = findByName(comida.getNombre());
            if (comidaExistente != null) {
                
                comidaExistente.setNombre(comida.getNombre());
                comidaExistente.setDescripcion(comida.getDescripcion());
                comidaExistente.setTotal_calorias(comida.getTotal_calorias());
                comidaExistente.setTotal_grasas(comida.getTotal_grasas());
                comidaExistente.setTotal_proteinas(comida.getTotal_proteinas());

                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró una comida con ese nombre");
            }
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Comida comida) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Comida com = findByName(comida.getNombre());
            if (com != null) {
                em.remove(com);
                em.getTransaction().commit();
            } else {
                System.out.println("No se encontró una comida con ese nombre");
            }
        } finally {
            em.close();
        }
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Comida findByName(String nombre) {
        EntityManager em = getEntityManager();
        TypedQuery<Comida> query = em.createQuery("SELECT c FROM Comida c WHERE c.nombre = :nombre", Comida.class);
        query.setParameter("nombre", nombre);
        List<Comida> resultados = query.getResultList();
        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }
   
    
    
    
}
