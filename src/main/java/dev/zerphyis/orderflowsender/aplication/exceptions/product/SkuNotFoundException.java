package dev.zerphyis.orderflowsender.aplication.exceptions.product;

public class SkuNotFoundException extends RuntimeException {
    public SkuNotFoundException(String sku) {

        super("SKU not found : " +sku );
    }
}
