import exceptions.MissingParamException;
import exceptions.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.PrintUtils;
import utils.RuleUtils;

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

    private final List<Product> products = new ArrayList<>();
    private final ProductValidator productValidator = new ProductValidator();
    private final MonetaryAmount balance = new MonetaryAmount(10_000, "HUF");
    private final AddProduct addProduct;
    private final User user;
    private final ShoppingCart shoppingCart;
    private final PayService payService;
    private final ProductSearchService productSearchService = new ProductSearchService();
    private final ShopChatBot chatBot = new ShopChatBot();
    private final ChatContext chatContext;

    // ===== Menu entry points =====

    public ConsoleMenu() {
        this.addProduct = new AddProduct(products, productValidator);
        this.user = new User(1, "User", 30, null, null, balance);
        this.shoppingCart = new ShoppingCart(user);
        this.payService = new PayService();
        this.chatContext = new ChatContext(user, products, shoppingCart);
        initProducts();
    }

    // ===== Menu actions (1-9) =====

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    addNewProduct(sc);
                    break;
                case 2:
                    listProducts(sc);
                    break;
                case 3:
                    viewCart(sc);
                    break;
                case 4:
                    pay(sc);
                    break;
                case 5:
                    removeFromCart(sc);
                    break;
                case 6:
                    searchMenu(sc);
                    break;
                case 7:
                    addBalance(sc);
                    break;
                case 8:
                    chatBot.startChat(sc, chatContext);
                    break;
                case 9:
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    PrintUtils.error("Invalid number, please try again.");

            }
        }
    }

    public void printMenu() {
        PrintUtils.line();
        PrintUtils.title("=== WebShop ===");
        PrintUtils.line();
        System.out.println("1. Add New Product ");
        System.out.println("2. Product List");
        System.out.println("3. View Cart");
        System.out.println("4. Pay");
        System.out.println("5. Remove");
        System.out.println("6. Search Product");
        System.out.println("7. Add Balance");
        System.out.println("8. I need help please (ChatBot)");
        System.out.println("9. Exit");
        System.out.println();
    }


    private void addNewProduct(Scanner sc) {
        System.out.println("Enter product name : ");
        String name = sc.nextLine();
        System.out.println("Enter product price : ");
        String price = sc.nextLine();
        System.out.println("Enter product stock : ");
        String stock = sc.nextLine();

        if (!RuleUtils.isNonEmpty(name) ||
                !RuleUtils.isNonEmpty(price) ||
                !RuleUtils.isNonEmpty(stock)) {
            PrintUtils.error("All fields are required!");
            pressEnterToContinue(sc);
            return;
        }

        String args = "name= " + name + "price= " + price + " stock= " + stock;

        try {
            addProduct.execute(args);
            PrintUtils.success("Product added successfully!");
        } catch (MissingParamException | ValidationException e) {
            PrintUtils.error("Error: " + e.getMessage());
        }
        pressEnterToContinue(sc);
    }

    private void listProducts(Scanner sc) {
        PrintUtils.info("=== Available Products ===");

        if (products.isEmpty()) {
            PrintUtils.info("No products.");
            pressEnterToContinue(sc);
            return;
        }

        printProductListWithAffordability(products);

        System.out.println();
        System.out.print("Select product ID to add to cart: ");
        long id = sc.nextLong();
        sc.nextLine();

        Product selected = findProductById(id);
        if (selected != null) {
            shoppingCart.addProduct(selected);
            PrintUtils.success("Added to cart: " + selected.getName());
        } else {
            PrintUtils.error("Invalid ID!");
        }
        pressEnterToContinue(sc);
    }

    private void printProductListWithAffordability(List<Product> list) {
        MonetaryAmount balance = user.getBalance();
        double balanceAmount = balance.getAmount();

        for (Product p : list) {
            double price = p.getPrice().getAmount();

            String line = p.getId() + ". " + p.getName() + " - " +
                    price + " " + p.getPrice().getCurrency() +
                    " | stock: " + p.getStock();

            if (price <= balanceAmount) {
                PrintUtils.success(line);   // GREEN
            } else {
                PrintUtils.error(line);     // RED
            }
        }
    }

    private void viewCart(Scanner sc) {
        PrintUtils.info("=== Your Cart ===");

        if (shoppingCart.getProducts().isEmpty()) {
            PrintUtils.info("Cart is empty.");
            pressEnterToContinue(sc);
            return;
        }

        for (Product p : shoppingCart.getProducts()) {
            System.out.println(
                    p.getId() + ". " + p.getName() + " - " +
                            p.getPrice().getAmount() + " " +
                            p.getPrice().getCurrency());
        }

        System.out.println("Total: " + shoppingCart.getTotalPrice());
        pressEnterToContinue(sc);
    }

    private void pay(Scanner sc) {
        payService.pay(shoppingCart);
        pressEnterToContinue(sc);
    }

    private void removeFromCart(Scanner sc) {
        System.out.println("=== Remove From Cart ===");

        if (shoppingCart.getProducts().isEmpty()) {
            System.out.println("Cart is empty ");
            return;
        }

        printProductList(shoppingCart.getProducts());
        System.out.println("Enter product ID to remove");
        long id = sc.nextLong();
        sc.nextLine();

        Product toRemove = findProductById(id);

        if (toRemove != null && shoppingCart.removeProduct(toRemove)) {
            System.out.println("Removed from cart: " + toRemove.getName());
        } else {
            System.out.println("Product not found in cart. ");
        }
        pressEnterToContinue(sc);
    }

    private void searchMenu(Scanner sc) {
        System.out.println("=== Search Products ===");
        System.out.println("1. Search by name");
        System.out.println("2. Search by price range");
        System.out.println("3. Only available products");
        System.out.println("4. Back");
        System.out.print("Choose option: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                searchByName(sc);
                break;
            case 2:
                searchByPriceRange(sc);
                break;
            case 3:
                searchByAvailability(sc);
                break;
            case 4:
                return;
            default:
                break;
        }
    }

    private void addBalance(Scanner sc) {
        System.out.println("=== Add Balance ===");
        System.out.println("Current balance: " +
                user.getBalance().getAmount() + " " +
                user.getBalance().getCurrency());

        System.out.println("Enter amount to (0 = cancel): ");
        double amountToAdd = sc.nextDouble();
        sc.nextLine();

        if (amountToAdd <= 0) {
            PrintUtils.info("Top up cancelled.");
            return;
        }

        MonetaryAmount current = user.getBalance();
        double newAmount = current.getAmount() + amountToAdd;

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

    // --- Search helpers ---

    private void searchByName(Scanner sc) {
        System.out.println("Enter text to search in product name: ");
        String text = sc.nextLine();

        List<Product> result = productSearchService.searchByName(products, text);
        System.out.println("=== Search result (By Name) ===");
        printProductList(result);
        pressEnterToContinue(sc);
    }

    private void searchByPriceRange(Scanner sc) {
        System.out.println("Enter the minimum price: ");
        double min = sc.nextDouble();
        System.out.println("Enter the maximum price: ");
        double max = sc.nextDouble();
        sc.nextLine();

        if (!RuleUtils.isPositive(min) || !RuleUtils.isPositive(max) || min > max) {
            PrintUtils.error("Invalid price range!");
            pressEnterToContinue(sc);
            return;
        }

        List<Product> result = productSearchService.searchByPriceRange(products, min, max);
        System.out.println("=== Search result (by price) ===");
        printProductList(result);
        pressEnterToContinue(sc);
    }

    private void searchByAvailability(Scanner sc) {
        List<Product> result = productSearchService.searchByAvailability(products);
        PrintUtils.info("=== Available products (stock > 0) ===");
        printProductList(result);
        pressEnterToContinue(sc);
    }

    // --- Helpers / utils inside ConsoleMenu ---

    private Product findProductById(long id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private void printProductList(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("No products. ");
            return;
        }

        for (Product p : list) {
            System.out.println(
                    p.getId() + ". " +
                            p.getName() + " - " +
                            p.getPrice().getAmount() + " " +
                            p.getPrice().getCurrency() + " | stock: " +
                            p.getStock()
            );
        }
    }

    private void pressEnterToContinue(Scanner sc) {
        System.out.println("\nPress ENTER to return to the menu...");
        sc.nextLine();
    }

    private void initProducts() {
        products.add(new Product(1L, "Laptop", new MonetaryAmount(350000, "HUF"), 5));
        products.add(new Product(2L, "Headphones", new MonetaryAmount(15000, "HUF"), 20));
        products.add(new Product(3L, "Keyboard", new MonetaryAmount(10000, "HUF"), 10));
        products.add(new Product(4L, "Mouse", new MonetaryAmount(7000, "HUF"), 12));
        products.add(new Product(5L, "Backpack", new MonetaryAmount(12000, "HUF"), 8));
        products.add(new Product(6L, "USB Drive", new MonetaryAmount(3500, "HUF"), 30));
        products.add(new Product(7L, "Powerbank", new MonetaryAmount(8000, "HUF"), 15));
        products.add(new Product(8L, "Smartwatch", new MonetaryAmount(50000, "HUF"), 7));
        products.add(new Product(9L, "Speaker", new MonetaryAmount(20000, "HUF"), 9));
        products.add(new Product(10L, "Monitor", new MonetaryAmount(65000, "HUF"), 4));
    }
}