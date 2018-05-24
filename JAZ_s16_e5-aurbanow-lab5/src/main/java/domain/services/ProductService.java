package domain.services;

import domain.entity.Product;
import domain.entity.ProductCategory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Product add(Product product) {
         entityManager.persist(product);
         return product;
    }

    public Product findById(Integer id) {
        try {
            return entityManager.createQuery("select p from Product p where p.id = :id", Product.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Product> findAll() {
        return entityManager.createQuery("select p from Product p", Product.class) .getResultList();

    }
    public List<Product> findByPrice(Integer price) {

            return entityManager.createQuery("select p from Product p where p.price = :price", Product.class)
                    .setParameter("price", price).getResultList();
    }

    public List<Product> findByName(String name) {
        return entityManager.createQuery("select p from Product p where p.name = :name", Product.class)
                .setParameter("name", name).getResultList();

    }

    public List<Product> productType(String productType) {
        try {
            ProductCategory productCategory = ProductCategory.valueOf(productType);
            return entityManager.createQuery("select p from Product p where p.productCategory = :productType", Product.class)
                    .setParameter("productType", productCategory).getResultList();
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    public void update(Product productFromDb) {
        entityManager.merge(productFromDb);
    }
}
