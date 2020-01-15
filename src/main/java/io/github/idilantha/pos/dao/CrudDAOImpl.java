package io.github.idilantha.pos.dao;

import io.github.idilantha.pos.entity.SuperEntity;
import org.hibernate.Session;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class CrudDAOImpl<T extends SuperEntity,Id extends Serializable> implements CrudDAO<T,Id> {

    protected Session session;
    private Class<T> entity;

    public CrudDAOImpl() {
        entity = (Class<T>)((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }

    @Override
    public List<T> findAll() throws Exception {
        return session.createQuery("FROM "+entity.getName()).list();
    }

    @Override
    public T find(Id id) throws Exception {
        return session.get(entity,id);
    }

    @Override
    public void save(T entity) throws Exception {
         session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.merge(entity);
    }

    @Override
    public void delete(Id id) throws Exception {
        session.delete(session.load(entity,id));
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }
}
