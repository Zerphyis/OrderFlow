package dev.zerphyis.orderflowsender.aplication.dtos;

import java.math.BigDecimal;

public record UpdateProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        String category,
        String sku
) {
}
