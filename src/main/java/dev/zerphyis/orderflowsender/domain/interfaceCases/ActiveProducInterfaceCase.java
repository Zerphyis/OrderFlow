package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.UUID;

public interface ActiveProducInterfaceCase {
    ProductResponseDto execute(UUID id);
}
