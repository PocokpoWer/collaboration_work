package service;

import exceptions.MissingParamException;
import exceptions.ValidationException;
import lombok.AllArgsConstructor;
import model.MonetaryAmount;
import model.MoneyCurrency;
import model.Product;
import utils.PrintUtils;
import validators.ProductValidator;

import java.math.BigDecimal;
import java.util.Scanner;

@AllArgsConstructor
public class AddProductCommand implements Command {
    private ProductCRUDService productCRUDService;
    private ProductValidator productValidator;

    public void execute(Scanner scanner) throws MissingParamException, ValidationException {
        PrintUtils.line();
        PrintUtils.title("=== Add Product ===");
        PrintUtils.line();
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        System.out.println("Enter product price:");
        String price = scanner.nextLine();
        System.out.println("Enter a currency (EUR, HUF, USD):");
        MoneyCurrency currency = MoneyCurrency.valueOf(scanner.nextLine().toUpperCase());
        while (currency != MoneyCurrency.EUR && currency != MoneyCurrency.HUF && currency != MoneyCurrency.USD) {
            System.out.println("Invalid currency. Please enter EUR, HUF, or USD.");
            currency = MoneyCurrency.valueOf(scanner.nextLine().toUpperCase());
        }
        System.out.println("Enter product stock: ");
        String stock = scanner.nextLine();
        Product product = Product.builder()
                .name(name)
                .price(new MonetaryAmount(BigDecimal.valueOf(Double.parseDouble(price)), currency))
                .stock(Integer.parseInt(stock))
                .build();
        if (!productValidator.isValid(product)) {
            throw new ValidationException("Invalid product");
        } else if (product == null) {
            throw new MissingParamException("Missing param");
        } else {
            try {
                productCRUDService.addProduct(product);
                PrintUtils.line();
                PrintUtils.success("Product added successfully.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}