package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.JpaProductDao;
import dao.JpaUserDao;
import exceptions.FailedToDeleteException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.MonetaryAmount;
import model.Product;
import model.User;
import service.*;
import utils.PrintUtils;
import validators.ProductValidator;
import validators.UserValidator;

public class ConsoleMenu {

    //TODO:Should modify!
    //- account registration!
    //- ergonomically!
    //- abandon cart tactic!
    //- change 1 to 6! Maybe point 1 will delete!
    //- offering bundles!
    //- Net promoter!
    //- behavior data!
    //- product details!
    //- offer in offer(we need your email for other bundles and present!)
    //- check user activation!
    //- sql data base for user data's!
    private static ChatContext chatContext;
    private static final ShopChatBot chatBot = new ShopChatBot();
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("webshop");
    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final JpaProductDao productDao = new JpaProductDao(entityManager);
    private static final ProductCRUDService productCRUDService = new ProductCRUDService(productDao);
    private static final AddProductCommand addProduct = new AddProductCommand(productCRUDService, new ProductValidator());
    private static final ListProduct listProduct = new ListProduct(productCRUDService);
    private static final DeleteProduct deleteProduct = new DeleteProduct(productCRUDService);
    private static final JpaUserDao userDao = new JpaUserDao(entityManager);
    private static final UserCRUDService userCRUDService = new UserCRUDService(userDao);
    private static final AddUserCommand addUser = new AddUserCommand(userCRUDService, new UserValidator());
    private static final ListUsers listUser = new ListUsers(userCRUDService);
    private static final DeleteUser deleteUser = new DeleteUser(userCRUDService);
    private static final BuyProduct buyProduct = new BuyProduct();
    // ===== Menu entry points =====


    // ===== Menu actions (1-9) =====

    public void start() throws FailedToDeleteException, IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        User user = choiceUser(sc);
        while (true) {
            printMenu();
            try {

                int input = Integer.parseInt(sc.nextLine());
                switch (input) {
                    case 1 -> addUser.execute(sc);
                    case 2 -> listUser.execute().forEach(System.out::println);
                    case 3 -> deleteUser.execute();
                    case 4 -> addProduct.execute(sc);
                    case 5 -> listProduct.getAllProducts(user);
                    case 6 -> deleteProduct.removeProduct();
                    case 7 -> viewCart(sc, user);
                    case 8 -> buyProduct.pay(user.getShoppingCart());
                    case 9 -> removeFromCart(sc, user);
                    case 10 -> addBalance(sc, user);
                    case 11 -> getStatistics(userDao.findAll());
                    case 12 -> chatBot.startChat(sc, chatContext);
                    case 0 -> {
                        sc.close();
                        System.exit(0);
                    }
                    default -> PrintUtils.error("Invalid number, please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error! Please enter a number!");
            }
        }
    }

    public static void printMenu() {
        PrintUtils.line();
        PrintUtils.title("=== WebShop ===");
        PrintUtils.line();
        System.out.println("1. Add New User ");
        System.out.println("2. User List ");
        System.out.println("3. Delete User ");
        System.out.println("4. Add New Product ");
        System.out.println("5. Product List");
        System.out.println("6. Delete Product");
        System.out.println("7. View Cart");
        System.out.println("8. Pay");
        System.out.println("9. Remove from chart");
        System.out.println("10. Add Balance");
        System.out.println("11. Statistics");
        System.out.println("12. I need help please (ChatBot)");
        System.out.println("0. Exit");
        System.out.println();
    }

    private void viewCart(Scanner sc, User user) {
        PrintUtils.info("=== Your Cart ===");

        ShoppingCart cart = user.getShoppingCart();

        if (cart == null || cart.getProducts().isEmpty()) {
            PrintUtils.info("Cart is empty.");
            pressEnterToContinue(sc);
            return;
        }

        for (Product p : cart.getProducts()) {
            System.out.println(
                    p.getId() + ". " + p.getName() + " - " +
                            p.getPrice().getAmount() + " " +
                            p.getPrice().getCurrency());
        }

        System.out.println("Total: " + cart.getTotalPrice());
        pressEnterToContinue(sc);
    }

    private void removeFromCart(Scanner sc, User user) {
        System.out.println("=== Remove From Cart ===");
        ShoppingCart userCart = user.getShoppingCart();
        if (userCart.getProducts().isEmpty()) {
            System.out.println("Cart is empty ");
            return;
        }

        userCart.getProducts().forEach(System.out::println);
        System.out.println("Enter product ID to remove");
        long id = Long.parseLong(sc.nextLine());

        productCRUDService.findByID(id).ifPresentOrElse(product -> {

            try {
                if (userCart.removeProduct(product)) {
                    System.out.println("Removed from cart: " + product.getName());
                } else {
                    System.out.println("Product not found in cart. ");
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, () -> System.out.println("Product not found"));
        pressEnterToContinue(sc);
    }

    private void addBalance(Scanner sc, User user) {
        System.out.println("=== Add Balance ===");
        System.out.println("Current balance: " +
                user.getBalance().getAmount() + " " +
                user.getBalance().getCurrency());

        System.out.println("Enter amount to (0 = cancel): ");
        BigDecimal amountToAdd = BigDecimal.valueOf(Double.parseDouble(sc.nextLine()));

        if (amountToAdd.compareTo(BigDecimal.ZERO) <= 0) {
            PrintUtils.info("Top up cancelled.");
            return;
        }

        MonetaryAmount current = user.getBalance();
        BigDecimal newAmount = current.getAmount().add(amountToAdd);

        MonetaryAmount newBalance = new MonetaryAmount(
                newAmount,
                current.getCurrency()
        );

        user.setBalance(newBalance);
        System.out.println("New Balance: " +
                newBalance.getAmount() +
                " " + newBalance.getCurrency());
        pressEnterToContinue(sc);
    }

    public void getStatistics(List<User> users) {
        OrderReportService reportService = new OrderReportService(users);
        PrintUtils.line();
        PrintUtils.title("=== Statistics ===");
        PrintUtils.line();
        System.out.println("Total users: " + users.size());
        System.out.println("Total sales per product:");
        reportService.getTotalSalesProduct().forEach((product, amount) -> System.out.println(product + ": " + amount));
        System.out.println();
        System.out.println("Total revenue:");
        System.out.println(reportService.getTotalRevenue() + " Euro");
        System.out.println();
        System.out.println("Orders per user");
        reportService.getOrderCountPerUser().forEach((user, amount) -> System.out.println(user.getName() + ": " + amount));
        pressEnterToContinue(new Scanner(System.in));
    }

    private User choiceUser(Scanner sc) {
        List<User> users = userCRUDService.getAllUsers();
        users.forEach(System.out::println);
        System.out.println("Enter your user id number:");
        int userId = Integer.parseInt(sc.nextLine());
        if (!users.isEmpty()) {
            User user = userCRUDService.findById(userId).get();
            user.getShoppingCart().setOwner(user);
            System.out.println("Welcome " + user.getName() + "!");
            return user;
        }
        return null;
    }

    private void pressEnterToContinue(Scanner sc) {
        System.out.println("\nPress ENTER to return to the menu...");
        sc.nextLine();
    }
}