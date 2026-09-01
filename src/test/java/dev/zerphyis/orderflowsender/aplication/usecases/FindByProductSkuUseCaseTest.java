package dev.zerphyis.orderflowsender.aplication.usecases;

import dev.zerphyis.orderflowsender.aplication.exceptions.SkuNotFoundException;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindByProductSkuUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private FindByProductSkuUseCase useCase;

    private Product product;

    @BeforeEach
    void setUp() {

        product = new Product(UUID.randomUUID(), "Notebook Dell", "Notebook profissional", "Eletrônicos", "DELL-001", BigDecimal.valueOf(3500), 20);
    }

    @Test
    void shouldFindProductBySkuSuccessfully() {

        when(repository.findBySku("DELL-001")).thenReturn(Optional.of(product));

        Optional<ProductResponseDto> response = useCase.execute("DELL-001");

        assertTrue(response.isPresent());

        ProductResponseDto dto = response.get();

        assertEquals(product.getId(), dto.id());
        assertEquals(product.getName(), dto.name());
        assertEquals(product.getDescription(), dto.description());
        assertEquals(product.getPrice(), dto.price());
        assertEquals(product.getCategory(), dto.category());
        assertEquals(product.getSku(), dto.sku());
        assertEquals(product.getStockQuantity(), dto.stockQuantity());

        verify(repository).findBySku("DELL-001");
    }

    @Test
    void shouldReturnEmptyWhenSkuDoesNotExist() {

        when(repository.findBySku("INVALID")).thenReturn(Optional.empty());

        Optional<ProductResponseDto> response = useCase.execute("INVALID");

        assertTrue(response.isEmpty());

        verify(repository).findBySku("INVALID");
    }

    @Test
    void shouldThrowExceptionWhenSkuIsNull() {

        assertThrows(SkuNotFoundException.class, () -> useCase.execute(null));

        verify(repository, never()).findBySku(anyString());
    }

    @Test
    void shouldThrowExceptionWhenSkuIsBlank() {

        assertThrows(SkuNotFoundException.class, () -> useCase.execute("   "));

        verify(repository, never()).findBySku(anyString());
    }
}
