package domain.services;

import domain.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    public void add(Comment comment) {
        entityManager.persist(comment);
    }

    public List<Comment> findByProductId(Integer id) {
        return entityManager.createQuery("select c from Comment c where c.product.id = :id", Comment.class)
                .setParameter("id", id).getResultList();
    }

    public List<Comment> findAll() {
        return entityManager.createQuery("select c from Comment c", Comment.class).getResultList();
    }

    public Comment find(Integer id) {
        try {
            return entityManager.createQuery("select c from Comment c where c.id = :id", Comment.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void remove(Comment comment) {
        entityManager.remove(comment);
    }
}
