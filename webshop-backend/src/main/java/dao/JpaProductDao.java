package dao;

import jakarta.persistence.EntityTransaction;
import model.Product;

import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaProductDao implements ProductDao {
    private EntityManager entityManager;

    @Override
    public Optional<Product> findById(long id) {
        Product product = entityManager.find(Product.class, id);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("SELECT product_name, product_price, product_stock FROM product", Product.class).getResultList();
    }

    @Override
    public void save(Product product) {
        executeOrder(() -> {
            entityManager.merge(product);
        });
    }

    @Override
    public void delete(long id) {
        executeOrder(() -> {
            Product product = entityManager.find(Product.class, id);
            entityManager.remove(product);
        });
    }

    private void executeOrder(Runnable runnable) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        runnable.run();
        entityTransaction.commit();
    }
}
