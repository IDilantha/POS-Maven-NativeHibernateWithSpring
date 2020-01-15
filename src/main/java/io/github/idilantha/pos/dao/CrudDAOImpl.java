package io.github.idilantha.pos.dao;

import io.github.idilantha.pos.entity.SuperEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,Id extends Serializable> implements CrudDAO<T,Id> {

    @Autowired
    protected SessionFactory sessionFactory;
    private Class<T> entity;

    public CrudDAOImpl() {
        entity = (Class<T>)((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() throws Exception {
        return getSession().createQuery("FROM "+entity.getName()).list();
    }

    @Override
    public T find(Id id) throws Exception {
        return getSession().get(entity,id);
    }

    @Override
    public void save(T entity) throws Exception {
         getSession().save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        getSession().merge(entity);
    }

    @Override
    public void delete(Id id) throws Exception {
        getSession().delete(getSession().load(entity,id));
    }


    @Override
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
