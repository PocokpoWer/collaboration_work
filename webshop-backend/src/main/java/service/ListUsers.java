package service;

import lombok.RequiredArgsConstructor;
import model.User;
import utils.PrintUtils;

import java.util.List;
import java.util.Scanner;
import java.util.Optional;

@RequiredArgsConstructor
public class ListUsers {
    private final UserCRUDService userCRUDService;
    protected static Optional<User> user;
    private static final Scanner scanner = new Scanner(System.in);

    public List<User> execute() {
        PrintUtils.line();
        PrintUtils.info("=== Available Users ===");
        PrintUtils.line();
        return userCRUDService.getAllUsers();
    }
}