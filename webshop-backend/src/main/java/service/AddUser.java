package service;

import exceptions.MissingParamException;
import exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import model.*;
import utils.PrintUtils;
import validators.UserValidator;

import java.math.BigDecimal;
import java.util.Scanner;

@RequiredArgsConstructor
public class AddUser {
    private final UserCRUDService userCRUDService;
    private final UserValidator userValidator;
    private Scanner sc = new Scanner(System.in);

    public void execute() {
        PrintUtils.line();
        PrintUtils.title("=== Add User ===");
        PrintUtils.line();
        System.out.println("Enter user name:");
        String name = sc.nextLine();
        System.out.println("Enter user age:");
        int age = Integer.parseInt(sc.nextLine());
        System.out.println("Enter a Country (Hungary, UK, Germany):");
        String country = sc.nextLine().toUpperCase();
        System.out.println("Enter a city:");
        String city = sc.nextLine();

        User user = User.builder()
                .name(name)
                .age(age)
                .address(new Address(Country.valueOf(country), city))
                .shoppingCart(new ShoppingCart(null))
                .balance(new MonetaryAmount(BigDecimal.ZERO, MoneyCurrency.EUR))
                .build();

        if (!userValidator.isValid(user)) {
            throw new ValidationException("Invalid user");
        } else if (user == null) {
            throw new MissingParamException("Missing param");
        } else {
            userCRUDService.addUser(user);
            PrintUtils.line();
            PrintUtils.success("User added successfully.");
        }
    }
}