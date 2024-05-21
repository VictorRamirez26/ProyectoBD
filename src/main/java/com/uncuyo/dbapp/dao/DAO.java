
package com.uncuyo.dbapp.dao;

public interface DAO<T> {
    void insertar(T entity);
    void modificar(T entity);
    void eliminar(T entity);
}

