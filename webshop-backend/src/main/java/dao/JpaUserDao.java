package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import model.User;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public class JpaUserDao implements Dao<User, Long> {
    EntityManager entityManager;

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        String query = "SELECT u FROM User u";
        return entityManager.createQuery(query, User.class).getResultList();
    }

    @Override
    public void save(User user) {
        executeOrder(() -> entityManager.persist(user));
    }

    @Override
    public void delete(Long id) {
        executeOrder(() -> {
            User user = entityManager.find(User.class, id);
            if (user == null) {
                throw new RuntimeException("User with id " + id + " not found");
            } else {
                entityManager.remove(user);
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