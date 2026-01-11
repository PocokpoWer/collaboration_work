package service;

import lombok.AllArgsConstructor;
import model.User;
import utils.PrintUtils;

import java.util.List;

@AllArgsConstructor
public class ListUsers {
    private UserCRUDService userCRUDService;

    public List<User> getAllUser() {
        PrintUtils.line();
        PrintUtils.info("=== Available Users ===");
        PrintUtils.line();
        return userCRUDService.getAllUsers();
    }
}