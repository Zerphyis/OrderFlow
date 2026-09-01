package dev.zerphyis.orderflowsender.aplication.usecases;

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
class ActiveProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ActiveProductUseCase useCase;

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

        product.deactivate();
    }

    @Test
    void shouldActivateProductSuccessfully() {

        when(repository.findById(productId))
                .thenReturn(Optional.of(product));

        when(repository.save(product))
                .thenReturn(product);

        ProductResponseDto response =
                useCase.execute(productId);

        assertNotNull(response);
        assertTrue(product.isActive());

        assertEquals(productId, response.id());
        assertEquals("Notebook Dell", response.name());
        assertEquals("Notebook profissional", response.description());
        assertEquals(BigDecimal.valueOf(3500), response.price());
        assertEquals("Eletrônicos", response.category());
        assertEquals("DELL-001", response.sku());
        assertEquals(20, response.stockQuantity());

        verify(repository).findById(productId);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(repository.findById(productId))
                .thenReturn(Optional.empty());

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(productId)
                );

        assertEquals(
                "Product not found: " + productId,
                exception.getMessage()
        );

        verify(repository).findById(productId);
        verify(repository, never()).save(any(Product.class));
    }
}
