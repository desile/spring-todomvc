package ru.hh.school.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hh.school.model.Todo;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class TodoDAOImpl {

    private SessionFactory sessionFactory;

    public TodoDAOImpl(){

    }

    @Autowired
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

    public void delete(List<Todo> todos){
        session().createQuery("delete from Todo where id in (:ids)")
                .setParameterList("ids", todos.stream().map(Todo::getId).toArray()).executeUpdate();
    }

    public void deleteCompleted() {
        session().createQuery("delete from Todo where completed = true").executeUpdate();

    }

    public void markAll(boolean mark) {
        session().createQuery("update Todo set completed = :mark").setParameter("mark", mark).executeUpdate();
    }

    private Session session(){
        return this.sessionFactory.getCurrentSession();
    }


}
