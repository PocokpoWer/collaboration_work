package service;

import exceptions.FailedToDeleteException;
import exceptions.MissingParamException;
import lombok.RequiredArgsConstructor;
import utils.PrintUtils;

import java.util.Scanner;

@RequiredArgsConstructor
public class DeleteProduct {
    private final ProductCRUDService productCRUDService;
    private Scanner scanner = new Scanner(System.in);

    public void removeProduct() throws FailedToDeleteException {
        PrintUtils.line();
        PrintUtils.info("=== Delete Product ===");
        PrintUtils.line();
        System.out.println("Enter product id to delete:");
        int userInput = Integer.parseInt(scanner.nextLine());
        if (userInput <= 0) {
            throw new MissingParamException("Product id cannot be null");
        }
        productCRUDService.deleteProduct(userInput);
        PrintUtils.line();
        PrintUtils.success("Product deleted successfully.");
    }
}