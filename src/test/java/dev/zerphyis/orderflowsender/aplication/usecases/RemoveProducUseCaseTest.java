package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.ProductNotFoundException;
import dev.zerphyis.orderflowsender.aplication.usecases.RemoveProducUseCase;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
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
class RemoveProducUseCaseTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private RemoveProducUseCase useCase;

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
    void shouldRemoveProductSuccessfully() {

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(productRepository.save(product))
                .thenReturn(product);

        useCase.execute(productId);

        assertFalse(product.isActive());

        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        ProductNotFoundException exception =
                assertThrows(
                        ProductNotFoundException.class,
                        () -> useCase.execute(productId)
                );

        assertEquals(
                "Product not found with id: " + productId,
                exception.getMessage()
        );

        verify(productRepository).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }
}
