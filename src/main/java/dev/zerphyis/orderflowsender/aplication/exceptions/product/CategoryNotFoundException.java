package dev.zerphyis.orderflowsender.aplication.exceptions.product;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String category) {

        super("Product with category not found : " +category );
    }
}
