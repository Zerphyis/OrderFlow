package dev.zerphyis.orderflowsender.domain.entity;

import dev.zerphyis.orderflowsender.aplication.exceptions.order.InvalidOrderException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public class OrderItem {

    private static final int MONEY_SCALE = 2;

    private final UUID productid;
    private final String productName;
    private final BigDecimal unitprice;
    private final Integer quantity;

    public OrderItem(UUID productid, String productName, BigDecimal unitprice, Integer quantity) {

        if (Objects.isNull(productid)) {
            throw new InvalidOrderException("Product id not must be null");
        }
        if (Objects.isNull(productName) || productName.isBlank()) {
            throw new InvalidOrderException("Product name must not be blank");
        }

        if (Objects.isNull(unitprice)) {
            throw new InvalidOrderException("Unit price must not be null");
        }

        if (unitprice.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidOrderException("Unit price must not be negative");
        }

        if (Objects.isNull(quantity) || quantity <= 0) {
            throw new InvalidOrderException("Quantity must be greater than zero");
        }

        this.productid = productid;
        this.productName = productName;
        this.unitprice = unitprice.setScale(MONEY_SCALE, RoundingMode.HALF_UP);
        this.quantity = quantity;
    }

    public BigDecimal subtotal() {
        return unitprice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(MONEY_SCALE, RoundingMode.HALF_UP);
    }

    public UUID getProductId() {
        return productid;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitprice;
    }

    public int getQuantity() {
        return quantity;
    }
}