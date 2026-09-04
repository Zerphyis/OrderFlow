package dev.zerphyis.orderflowsender.infra.controller.product;

import dev.zerphyis.orderflowsender.aplication.exceptions.product.SkuNotFoundException;
import dev.zerphyis.orderflowsender.domain.interfaceCases.*;
import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
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
class ProductServiceTest {

    @Mock
    private CreateProductInterfaceCase createProductUseCase;

    @Mock
    private UpdateProductInterfaceCase updateProductUseCase;

    @Mock
    private RemoveProductInterfaceCase removeProductUseCase;

    @Mock
    private FindByProductCategoryInterfaceCase findProductsByCategoryUseCase;

    @Mock
    private FindBySkuProductInterfaceCase findProductBySkuUseCase;

    @Mock
    private ActiveProducInterfaceCase activateProductUseCase;

    @Mock
    private ListProductInterfaceCase listProductUseCase;

    @Mock
    private FindByIdProductInterfaceCase findByIdProductUseCase;

    @InjectMocks
    private ProductService productService;

    private UUID productId;

    private ProductResponseDto productResponse;

    private ProductRequestDto productRequest;

    private UpdateProductRequestDto updateRequest;

    @BeforeEach
    void setUp() {

        productId = UUID.randomUUID();

        productResponse = new ProductResponseDto(productId, "Notebook Dell", "Notebook profissional", BigDecimal.valueOf(3500), "Eletrônicos", "DELL-001", 20);

        productRequest = new ProductRequestDto("Notebook Dell", "Notebook profissional", BigDecimal.valueOf(3500), "Eletrônicos", "DELL-001", 20);

        updateRequest = new UpdateProductRequestDto("Notebook Dell Atualizado", "Notebook profissional atualizado", BigDecimal.valueOf(4000), "Informática", "DELL-002");
    }


    @Test
    void shouldCreateProductSuccessfully() {

        when(createProductUseCase.execute(productRequest)).thenReturn(productResponse);

        ProductResponseDto response = productService.create(productRequest);

        assertNotNull(response);
        assertEquals(productResponse, response);

        verify(createProductUseCase).execute(productRequest);
    }

    @Test
    void shouldPropagateExceptionWhenCreateFails() {

        when(createProductUseCase.execute(productRequest)).thenThrow(new RuntimeException("Create failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.create(productRequest));

        assertEquals("Create failed", exception.getMessage());

        verify(createProductUseCase).execute(productRequest);
    }


    @Test
    void shouldFindProductByIdSuccessfully() {

        when(findByIdProductUseCase.execute(productId)).thenReturn(productResponse);

        ProductResponseDto response = productService.findById(productId);

        assertNotNull(response);
        assertEquals(productResponse, response);

        verify(findByIdProductUseCase).execute(productId);
    }

    @Test
    void shouldPropagateExceptionWhenFindByIdFails() {

        when(findByIdProductUseCase.execute(productId)).thenThrow(new RuntimeException("Product not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.findById(productId));

        assertEquals("Product not found", exception.getMessage());

        verify(findByIdProductUseCase).execute(productId);
    }


    @Test
    void shouldListProductsSuccessfully() {

        List<ProductResponseDto> products = List.of(productResponse);

        when(listProductUseCase.execute(0, 10)).thenReturn(products);

        List<ProductResponseDto> response = productService.list(0, 10);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(productResponse, response.get(0));

        verify(listProductUseCase).execute(0, 10);
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoProducts() {

        when(listProductUseCase.execute(0, 10)).thenReturn(List.of());

        List<ProductResponseDto> response = productService.list(0, 10);

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(listProductUseCase).execute(0, 10);
    }

    @Test
    void shouldPropagateExceptionWhenListFails() {

        when(listProductUseCase.execute(0, 10)).thenThrow(new IllegalArgumentException("Page cannot be negative"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> productService.list(0, 10));

        assertEquals("Page cannot be negative", exception.getMessage());

        verify(listProductUseCase).execute(0, 10);
    }


    @Test
    void shouldUpdateProductSuccessfully() {

        ProductResponseDto updatedProduct = new ProductResponseDto(productId, "Notebook Dell Atualizado", "Notebook profissional atualizado", BigDecimal.valueOf(4000), "Informática", "DELL-002", 20);

        when(updateProductUseCase.execute(productId, updateRequest)).thenReturn(updatedProduct);

        ProductResponseDto response = productService.update(productId, updateRequest);

        assertNotNull(response);
        assertEquals(updatedProduct, response);

        verify(updateProductUseCase).execute(productId, updateRequest);
    }

    @Test
    void shouldPropagateExceptionWhenUpdateFails() {

        when(updateProductUseCase.execute(productId, updateRequest)).thenThrow(new RuntimeException("Update failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.update(productId, updateRequest));

        assertEquals("Update failed", exception.getMessage());

        verify(updateProductUseCase).execute(productId, updateRequest);
    }


    @Test
    void shouldRemoveProductSuccessfully() {

        doNothing().when(removeProductUseCase).execute(productId);

        assertDoesNotThrow(() -> productService.remove(productId));

        verify(removeProductUseCase).execute(productId);
    }

    @Test
    void shouldPropagateExceptionWhenRemoveFails() {

        doThrow(new RuntimeException("Remove failed")).when(removeProductUseCase).execute(productId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.remove(productId));

        assertEquals("Remove failed", exception.getMessage());

        verify(removeProductUseCase).execute(productId);
    }


    @Test
    void shouldFindProductsByCategorySuccessfully() {

        List<ProductResponseDto> products = List.of(productResponse);

        when(findProductsByCategoryUseCase.execute("Eletrônicos")).thenReturn(products);

        List<ProductResponseDto> response = productService.findByCategory("Eletrônicos");

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(productResponse, response.get(0));

        verify(findProductsByCategoryUseCase).execute("Eletrônicos");
    }

    @Test
    void shouldReturnEmptyListWhenCategoryHasNoProducts() {

        when(findProductsByCategoryUseCase.execute("Informática")).thenReturn(List.of());

        List<ProductResponseDto> response = productService.findByCategory("Informática");

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(findProductsByCategoryUseCase).execute("Informática");
    }

    @Test
    void shouldPropagateExceptionWhenCategorySearchFails() {

        when(findProductsByCategoryUseCase.execute("Eletrônicos")).thenThrow(new RuntimeException("Category search failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.findByCategory("Eletrônicos"));

        assertEquals("Category search failed", exception.getMessage());

        verify(findProductsByCategoryUseCase).execute("Eletrônicos");
    }


    @Test
    void shouldFindProductBySkuSuccessfully() {

        when(findProductBySkuUseCase.execute("DELL-001")).thenReturn(Optional.of(productResponse));

        ProductResponseDto response = productService.findBySku("DELL-001");

        assertNotNull(response);
        assertEquals(productResponse, response);

        verify(findProductBySkuUseCase).execute("DELL-001");
    }

    @Test
    void shouldThrowExceptionWhenSkuDoesNotExist() {

        when(findProductBySkuUseCase.execute("INVALID")).thenReturn(Optional.empty());

        SkuNotFoundException exception = assertThrows(SkuNotFoundException.class, () -> productService.findBySku("INVALID"));

        assertNotNull(exception);

        verify(findProductBySkuUseCase).execute("INVALID");
    }

    @Test
    void shouldPropagateExceptionWhenFindBySkuFails() {

        when(findProductBySkuUseCase.execute("DELL-001")).thenThrow(new RuntimeException("SKU search failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.findBySku("DELL-001"));

        assertEquals("SKU search failed", exception.getMessage());

        verify(findProductBySkuUseCase).execute("DELL-001");
    }

    @Test
    void shouldActivateProductSuccessfully() {

        ProductResponseDto activatedProduct = new ProductResponseDto(productId, "Notebook Dell", "Notebook profissional", BigDecimal.valueOf(3500), "Eletrônicos", "DELL-001", 20);

        when(activateProductUseCase.execute(productId)).thenReturn(activatedProduct);

        ProductResponseDto response = productService.activate(productId);

        assertNotNull(response);
        assertEquals(activatedProduct, response);

        verify(activateProductUseCase).execute(productId);
    }

    @Test
    void shouldPropagateExceptionWhenActivationFails() {

        when(activateProductUseCase.execute(productId)).thenThrow(new RuntimeException("Activation failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.activate(productId));

        assertEquals("Activation failed", exception.getMessage());

        verify(activateProductUseCase).execute(productId);
    }
}
