
package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.ProductNotFoundException;
import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductUseCase useCase;

    private UUID productId;
    private Product product;
    private UpdateProductRequestDto requestDto;

    @BeforeEach
    void setUp() {

        productId = UUID.randomUUID();

        product = new Product(
                productId,
                "Notebook antigo",
                "Descrição antiga",
                "Eletrônicos",
                "OLD-001",
                BigDecimal.valueOf(3000),
                20
        );

        requestDto = new UpdateProductRequestDto(
                "Notebook novo",
                "Descrição nova",
                BigDecimal.valueOf(4000),
                "Informática",
                "NEW-001"
        );
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        when(repository.findById(productId))
                .thenReturn(Optional.of(product));

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ProductResponseDto response =
                useCase.execute(productId, requestDto);

        assertNotNull(response);

        assertEquals("Notebook novo", response.name());
        assertEquals("Descrição nova", response.description());
        assertEquals(BigDecimal.valueOf(4000), response.price());
        assertEquals("Informática", response.category());
        assertEquals("NEW-001", response.sku());

        // O estoque não deve ser alterado pelo Update
        assertEquals(20, response.stockQuantity());

        verify(repository).findById(productId);
        verify(repository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {

        when(repository.findById(productId))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> useCase.execute(productId, requestDto)
        );

        verify(repository).findById(productId);
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    void shouldSaveTheUpdatedProduct() {

        when(repository.findById(productId))
                .thenReturn(Optional.of(product));

        when(repository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        useCase.execute(productId, requestDto);

        ArgumentCaptor<Product> captor =
                ArgumentCaptor.forClass(Product.class);

        verify(repository).save(captor.capture());

        Product savedProduct = captor.getValue();

        assertEquals("Notebook novo", savedProduct.getName());
        assertEquals("Descrição nova", savedProduct.getDescription());
        assertEquals(BigDecimal.valueOf(4000), savedProduct.getPrice());
        assertEquals("Informática", savedProduct.getCategory());
        assertEquals("NEW-001", savedProduct.getSku());

        assertEquals(20, savedProduct.getStockQuantity());
    }
}

