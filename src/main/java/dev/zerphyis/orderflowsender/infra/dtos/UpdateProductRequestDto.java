package dev.zerphyis.orderflowsender.infra.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductRequestDto(

        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or greater")
        BigDecimal price,

        @NotBlank(message = "Category is required")
        String category,

        @NotBlank(message = "SKU is required")
        String sku
) {
}