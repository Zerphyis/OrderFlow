package dev.zerphyis.orderflowsender.domain.interfaceCases;

import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;

import java.util.UUID;

public interface FindByIdProductInterfaceCase {
    ProductResponseDto execute(UUID id);
}
