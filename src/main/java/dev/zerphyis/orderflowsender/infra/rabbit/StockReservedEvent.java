package dev.zerphyis.orderflowsender.infra.rabbit;

import java.util.UUID;

public record StockReservedEvent(UUID productId,
                                 Integer quantity) {
}
