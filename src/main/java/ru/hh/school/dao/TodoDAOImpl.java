package ru.hh.school.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.hh.school.model.Todo;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Component
@Transactional
public class TodoDAOImpl {

    private SessionFactory sessionFactory;

    public TodoDAOImpl(){

    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    public List<Todo> listTodos(Boolean completed){
        Criteria criteria = session().createCriteria(Todo.class);
        if (completed != null){
            criteria.add(Restrictions.eq("completed", completed));
        }
        return criteria.list();
    }

    public void update(Todo todo){
        session().update(todo);
    }

    public void save(Todo todo){
        session().persist(todo);
    }

    public void delete(Todo todo){
        session().delete(todo);
    }

    private Session session(){
        return this.sessionFactory.getCurrentSession();
    }



}
