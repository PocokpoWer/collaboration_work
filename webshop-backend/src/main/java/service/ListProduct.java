package service;

import controller.ConsoleMenu;
import lombok.RequiredArgsConstructor;
import model.Product;
import model.User;
import utils.PrintUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@RequiredArgsConstructor
public class ListProduct {
    private final ProductCRUDService productCRUDService;
    private Scanner scanner = new Scanner(System.in);
    private ProductSearchService productSearchService = new ProductSearchService();

    public void getAllProducts(User user) throws IOException, InterruptedException {
        PrintUtils.line();
        PrintUtils.info("=== Available Products ===");
        PrintUtils.line();
        List<Product> list = productCRUDService.getAllProducts();
        printMenu();
        int userInput = Integer.parseInt(scanner.nextLine());
        switch (userInput) {
            case 1 -> list.forEach(System.out::println);
            case 2 -> {
                System.out.println("Enter product name: ");
                String name = scanner.nextLine();
                productSearchService.searchByName(list, name).forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("Enter min and max price: ");
                BigDecimal min = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
                BigDecimal max = BigDecimal.valueOf(Long.parseLong(scanner.nextLine()));
                productSearchService.searchByPriceRange(list, min, max).forEach(System.out::println);
            }
            case 4 -> productSearchService.searchByAvailability(list).forEach(System.out::println);
            case 5 -> {
                System.out.println("Select product ID to add to cart: ");
                int id = Integer.parseInt(scanner.nextLine());
                Optional<Product> product = productCRUDService.findByID(id);
                if (product.isEmpty()) {
                    System.out.println("Product not found.");
                } else {
                    user.getShoppingCart().addProduct(product.get());
                    System.out.println(product.get().getName() + " added to cart.");
                }
            }
            case 6 -> ConsoleMenu.printMenu();
            default -> System.out.println("Invalid option. Please choose again.");
        }
    }

    private void printMenu() {
        System.out.println("Enter a menu: ");
        System.out.println("1. List of products");
        System.out.println("2. Searching by name");
        System.out.println("3. Searching by price range");
        System.out.println("4. Searching by availability");
        System.out.println("5. Select product ID to add to cart: ");
        System.out.println("6. Back to the menu ");
    }
}