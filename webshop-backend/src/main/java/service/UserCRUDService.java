package service;

import dao.Dao;
import lombok.AllArgsConstructor;
import model.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserCRUDService {
    private final Dao<User, Long> userDao;

    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    public void addUser(User user) {
        userDao.save(user);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(long id) {
        userDao.delete(id);
    }
}