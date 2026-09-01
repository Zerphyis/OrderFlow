package dev.zerphyis.orderflowsender.aplication.usecases;

import static org.junit.jupiter.api.Assertions.*;

import dev.zerphyis.orderflowsender.domain.entity.Product;
import dev.zerphyis.orderflowsender.domain.repository.ProductRepository;
import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateProductUseCaseTest {
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private CreateProductUseCase useCase;
    private ProductRequestDto requestDto;
    private Product savedProduct;

    @BeforeEach
    void setUp() {
        requestDto = new ProductRequestDto("Notebook Dell Inspiron", "Notebook para uso profissional", BigDecimal.valueOf(3500.00), "Eletrônicos", "DELL-INS-001", 20);
        savedProduct = new Product(UUID.randomUUID(), requestDto.name(), requestDto.description(), requestDto.category(), requestDto.sku(), requestDto.price(), requestDto.stockQuantity());
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(repository.save(any(Product.class))).thenReturn(savedProduct);
        ProductResponseDto response = useCase.execute(requestDto);
        assertNotNull(response);
        assertEquals(savedProduct.getId(), response.id());
        assertEquals(savedProduct.getName(), response.name());
        assertEquals(savedProduct.getDescription(), response.description());
        assertEquals(savedProduct.getPrice(), response.price());
        assertEquals(savedProduct.getCategory(), response.category());
        assertEquals(savedProduct.getSku(), response.sku());
        assertEquals(savedProduct.getStockQuantity(), response.stockQuantity());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldPropagateExceptionWhenRepositorySaveFails() {
        when(repository.save(any(Product.class))).thenThrow(new RuntimeException("Database error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> useCase.execute(requestDto));
        assertEquals("Database error", exception.getMessage());
        verify(repository, times(1)).save(any(Product.class));
    }

}