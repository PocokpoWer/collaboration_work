package dao;

import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import model.Product;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class JpaProductDao implements Dao<Product, Long> {
    private EntityManager entityManager;

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Product.class, id));
    }

    @Override
    public List<Product> findAll() {
        String query = "SELECT p FROM Product p";
        return (entityManager.createQuery(query, Product.class).getResultList());
    }

    @Override
    public void save(Product product) {
        executeOrder(() -> {
            entityManager.persist(product);
        });
    }

    @Override
    public void delete(Long id) {
        executeOrder(() -> {
            Product product = entityManager.find(Product.class, id);
            if (product.getStock() > 1) {
                product.setStock(product.getStock() - 1);
            } else if (product == null) {
                throw new RuntimeException("Product with id " + id + " not found");
            } else {
                entityManager.remove(product);
            }
        });
    }

    private void executeOrder(Runnable runnable) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        runnable.run();
        entityTransaction.commit();
    }
}