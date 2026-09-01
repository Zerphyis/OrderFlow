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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ListProductUseCase useCase;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {

        product1 = new Product(
                UUID.randomUUID(),
                "Notebook Dell",
                "Notebook profissional",
                "Eletrônicos",
                "DELL-001",
                BigDecimal.valueOf(3500),
                20
        );

        product2 = new Product(
                UUID.randomUUID(),
                "Mouse Logitech",
                "Mouse sem fio",
                "Eletrônicos",
                "LOG-001",
                BigDecimal.valueOf(150),
                50
        );
    }

    @Test
    void shouldListProductsSuccessfully() {

        when(repository.findAll(0, 10))
                .thenReturn(List.of(product1, product2));

        List<ProductResponseDto> response =
                useCase.execute(0, 10);

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals(product1.getId(), response.get(0).id());
        assertEquals(product1.getName(), response.get(0).name());

        assertEquals(product2.getId(), response.get(1).id());
        assertEquals(product2.getName(), response.get(1).name());

        verify(repository).findAll(0, 10);
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoProducts() {

        when(repository.findAll(0, 10))
                .thenReturn(List.of());

        List<ProductResponseDto> response =
                useCase.execute(0, 10);

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(repository).findAll(0, 10);
    }

    @Test
    void shouldThrowExceptionWhenPageIsNegative() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(-1, 10)
                );

        assertEquals(
                "Page cannot be negative",
                exception.getMessage()
        );

        verify(repository, never()).findAll(anyInt(), anyInt());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsZero() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(0, 0)
                );

        assertEquals(
                "Size must be greater than zero",
                exception.getMessage()
        );

        verify(repository, never()).findAll(anyInt(), anyInt());
    }

    @Test
    void shouldThrowExceptionWhenSizeIsNegative() {

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> useCase.execute(0, -10)
                );

        assertEquals(
                "Size must be greater than zero",
                exception.getMessage()
        );

        verify(repository, never()).findAll(anyInt(), anyInt());
    }
}
