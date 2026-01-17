package service;

import lombok.AllArgsConstructor;
import utils.PrintUtils;

@AllArgsConstructor
public class DeleteUser {
    private UserCRUDService userCRUDService;

    public void execute() {
        PrintUtils.line();
        System.out.println("=== Delete User ===");
        PrintUtils.line();
        System.out.println("Enter user id to delete:");
        int userInput = Integer.parseInt(System.console().readLine());
        userCRUDService.deleteUser(userInput);
        PrintUtils.line();
        PrintUtils.success("User deleted successfully.");
    }
}