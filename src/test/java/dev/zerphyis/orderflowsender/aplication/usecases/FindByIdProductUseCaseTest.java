
        package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.product.ProductNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindByProductCategoryUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private FindByIdProductUseCase useCase;

    private UUID productId;
    private Product product;

    @BeforeEach
    void setUp() {

        productId = UUID.randomUUID();

        product = new Product(
                productId,
                "Notebook Dell",
                "Notebook profissional",
                "Eletrônicos",
                "DELL-001",
                BigDecimal.valueOf(3500),
                20
        );
    }

    @Test
    void shouldFindProductByIdSuccessfully() {

        when(repository.findById(productId))
                .thenReturn(Optional.of(product));

        ProductResponseDto response = useCase.execute(productId);

        assertNotNull(response);
        assertEquals(product.getId(), response.id());
        assertEquals(product.getName(), response.name());
        assertEquals(product.getDescription(), response.description());
        assertEquals(product.getPrice(), response.price());
        assertEquals(product.getCategory(), response.category());
        assertEquals(product.getSku(), response.sku());
        assertEquals(product.getStockQuantity(), response.stockQuantity());

        verify(repository, times(1)).findById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(repository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> useCase.execute(productId)
        );

        verify(repository, times(1)).findById(productId);
    }
}

