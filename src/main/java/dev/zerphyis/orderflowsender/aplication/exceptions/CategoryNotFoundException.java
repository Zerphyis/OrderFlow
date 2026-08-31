package dev.zerphyis.orderflowsender.aplication.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String category) {

        super("Product with category not found : " +category );
    }
}
