package dev.zerphyis.orderflowsender.aplication.exceptions.order;

public class InvalidOrderStateTransitionException extends RuntimeException {
    public InvalidOrderStateTransitionException(String message) {
        super(message);
    }
}
