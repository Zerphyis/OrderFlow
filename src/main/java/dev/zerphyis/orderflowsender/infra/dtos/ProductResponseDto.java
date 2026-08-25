package dev.zerphyis.orderflowsender.infra.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDto(UUID id,
                                 String name,
                                 String description,
                                 BigDecimal price,
                                 String category,
                                 String sku,
                                 Integer stockQuantity) {
}
