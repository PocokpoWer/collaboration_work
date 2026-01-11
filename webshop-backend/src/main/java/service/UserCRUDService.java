package service;

import dao.Dao;
import lombok.AllArgsConstructor;
import model.User;

import java.util.List;

@AllArgsConstructor
public class UserCRUDService {
    private final Dao userDao;

    public void findById(long id) {
        userDao.findById(id);
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