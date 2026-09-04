package dev.zerphyis.orderflowsender.aplication.exceptions.order;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}
