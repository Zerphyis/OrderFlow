package dev.zerphyis.orderflowsender.domain.entity;



import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private String sku;
    private Integer stockQuantity;
    private boolean active;

    public Product() {
        this.active = true;
    }

    public Product(
            UUID id,
            String name,
            String description,
            String category,
            String sku,
            BigDecimal price,
            Integer stockQuantity
    ) {
        this(
                id,
                name,
                description,
                category,
                sku,
                price,
                stockQuantity,
                true
        );
    }

    public Product(
            UUID id,
            String name,
            String description,
            String category,
            String sku,
            BigDecimal price,
            Integer stockQuantity,
            boolean active
    ) {
        validateId(id);
        validateName(name);
        validatePrice(price);
        validateSku(sku);
        validateStockQuantity(stockQuantity);

        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.sku = sku;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.active = active;
    }

    private void validateId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException(
                    "Product id cannot be null"
            );
        }
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Product name cannot be null or blank"
            );
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException(
                    "Product price cannot be null"
            );
        }

        if (price.signum() < 0) {
            throw new IllegalArgumentException(
                    "Product price cannot be negative"
            );
        }
    }

    private void validateSku(String sku) {
        if (sku == null || sku.isBlank()) {
            throw new IllegalArgumentException(
                    "Product SKU cannot be null or blank"
            );
        }
    }

    private void validateStockQuantity(Integer stockQuantity) {
        if (stockQuantity == null) {
            throw new IllegalArgumentException(
                    "Stock quantity cannot be null"
            );
        }

        if (stockQuantity < 0) {
            throw new IllegalArgumentException(
                    "Stock quantity cannot be negative"
            );
        }
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount to increase must be positive"
            );
        }

        this.stockQuantity += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Amount to decrease must be positive"
            );
        }

        if (amount > this.stockQuantity) {
            throw new IllegalStateException(
                    "Not enough stock to decrease by " + amount
            );
        }

        this.stockQuantity -= amount;
    }

    public void changePrice(BigDecimal newPrice) {
        validatePrice(newPrice);
        this.price = newPrice;
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isInStock() {
        return this.stockQuantity > 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}