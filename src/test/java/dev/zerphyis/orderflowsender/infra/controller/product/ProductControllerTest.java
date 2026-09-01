package dev.zerphyis.orderflowsender.infra.controller.product;

import dev.zerphyis.orderflowsender.aplication.exceptions.ProductNotFoundException;
import dev.zerphyis.orderflowsender.aplication.exceptions.SkuNotFoundException;
import dev.zerphyis.orderflowsender.infra.config.HandlerController;
import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
import dev.zerphyis.orderflowsender.infra.rabbit.RabbitMqProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Mock
    private RabbitMqProducer rabbitMqProducer;

    private ObjectMapper objectMapper;

    private UUID productId;

    private ProductResponseDto productResponse;

    private ProductRequestDto productRequest;

    private UpdateProductRequestDto updateRequest;

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();

        ProductController controller =
                new ProductController(
                        productService,
                        rabbitMqProducer
                );

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new HandlerController())
                .build();

        productId = UUID.randomUUID();

        productResponse = new ProductResponseDto(
                productId,
                "Notebook Dell",
                "Notebook profissional",
                BigDecimal.valueOf(3500),
                "Eletrônicos",
                "DELL-001",
                20
        );

        productRequest = new ProductRequestDto(
                "Notebook Dell",
                "Notebook profissional",
                BigDecimal.valueOf(3500),
                "Eletrônicos",
                "DELL-001",
                20
        );

        updateRequest = new UpdateProductRequestDto(
                "Notebook Dell Atualizado",
                "Notebook profissional atualizado",
                BigDecimal.valueOf(4000),
                "Informática",
                "DELL-002"
        );
    }


    @Test
    void shouldCreateProductSuccessfully() throws Exception {

        when(productService.create(any(ProductRequestDto.class)))
                .thenReturn(productResponse);

        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        productRequest
                                ))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(productId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Notebook Dell"))
                .andExpect(jsonPath("$.description")
                        .value("Notebook profissional"))
                .andExpect(jsonPath("$.price")
                        .value(3500))
                .andExpect(jsonPath("$.category")
                        .value("Eletrônicos"))
                .andExpect(jsonPath("$.sku")
                        .value("DELL-001"))
                .andExpect(jsonPath("$.stockQuantity")
                        .value(20));

        verify(productService)
                .create(any(ProductRequestDto.class));
    }

    @Test
    void shouldReturnBadRequestWhenCreateRequestIsInvalid()
            throws Exception {

        ProductRequestDto invalidRequest =
                new ProductRequestDto(
                        "",
                        "",
                        BigDecimal.ZERO,
                        "",
                        "",
                        -1
                );

        mockMvc.perform(
                        post("/api/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        invalidRequest
                                ))
                )
                .andExpect(status().isBadRequest());

        verify(productService, never())
                .create(any(ProductRequestDto.class));
    }


    @Test
    void shouldFindProductByIdSuccessfully()
            throws Exception {

        when(productService.findById(productId))
                .thenReturn(productResponse);

        mockMvc.perform(
                        get("/api/products/{id}", productId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(productId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Notebook Dell"))
                .andExpect(jsonPath("$.sku")
                        .value("DELL-001"));

        verify(productService)
                .findById(productId);
    }

    @Test
    void shouldReturnBadRequestWhenIdIsInvalid()
            throws Exception {

        mockMvc.perform(
                        get("/api/products/{id}", "invalid-id")
                )
                .andExpect(status().isBadRequest());

        verify(productService, never())
                .findById(any(UUID.class));
    }

    @Test
    void shouldPropagateExceptionWhenProductIsNotFound()
            throws Exception {

        when(productService.findById(productId))
                .thenThrow(
                        new ProductNotFoundException(productId)
                );

        mockMvc.perform(
                        get("/api/products/{id}", productId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Product not found"))
                .andExpect(jsonPath("$.message")
                        .value("Product not found with id: " + productId));

        verify(productService)
                .findById(productId);
    }


    @Test
    void shouldFindProductsByCategorySuccessfully()
            throws Exception {

        when(productService.findByCategory("Eletrônicos"))
                .thenReturn(List.of(productResponse));

        mockMvc.perform(
                        get("/api/products/category/{category}",
                                "Eletrônicos")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Notebook Dell"))
                .andExpect(jsonPath("$[0].category")
                        .value("Eletrônicos"));

        verify(productService)
                .findByCategory("Eletrônicos");
    }

    @Test
    void shouldReturnEmptyListWhenCategoryHasNoProducts()
            throws Exception {

        when(productService.findByCategory("Informática"))
                .thenReturn(List.of());

        mockMvc.perform(
                        get("/api/products/category/{category}",
                                "Informática")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(productService)
                .findByCategory("Informática");
    }


    @Test
    void shouldFindProductBySkuSuccessfully()
            throws Exception {

        when(productService.findBySku("DELL-001"))
                .thenReturn(productResponse);

        mockMvc.perform(
                        get("/api/products/sku/{sku}",
                                "DELL-001")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(productId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Notebook Dell"))
                .andExpect(jsonPath("$.sku")
                        .value("DELL-001"));

        verify(productService)
                .findBySku("DELL-001");
    }

    @Test
    void shouldPropagateExceptionWhenSkuIsNotFound()
            throws Exception {

        when(productService.findBySku("INVALID"))
                .thenThrow(
                        new SkuNotFoundException("INVALID")
                );

        mockMvc.perform(
                        get("/api/products/sku/{sku}", "INVALID")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("SKU not found"))
                .andExpect(jsonPath("$.message")
                        .value("SKU not found : INVALID"));

        verify(productService)
                .findBySku("INVALID");
    }


    @Test
    void shouldListProductsSuccessfully()
            throws Exception {

        when(productService.list(0, 10))
                .thenReturn(List.of(productResponse));

        mockMvc.perform(
                        get("/api/products")
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name")
                        .value("Notebook Dell"));

        verify(productService)
                .list(0, 10);
    }

    @Test
    void shouldUseDefaultPaginationValues()
            throws Exception {

        when(productService.list(0, 10))
                .thenReturn(List.of(productResponse));

        mockMvc.perform(
                        get("/api/products")
                )
                .andExpect(status().isOk());

        verify(productService)
                .list(0, 10);
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoProducts()
            throws Exception {

        when(productService.list(0, 10))
                .thenReturn(List.of());

        mockMvc.perform(
                        get("/api/products")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(productService)
                .list(0, 10);
    }


    @Test
    void shouldUpdateProductSuccessfully()
            throws Exception {

        ProductResponseDto updatedProduct =
                new ProductResponseDto(
                        productId,
                        "Notebook Dell Atualizado",
                        "Notebook profissional atualizado",
                        BigDecimal.valueOf(4000),
                        "Informática",
                        "DELL-002",
                        20
                );

        when(productService.update(
                eq(productId),
                any(UpdateProductRequestDto.class)
        )).thenReturn(updatedProduct);

        mockMvc.perform(
                        put("/api/products/{id}", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        updateRequest
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(productId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Notebook Dell Atualizado"))
                .andExpect(jsonPath("$.price")
                        .value(4000))
                .andExpect(jsonPath("$.category")
                        .value("Informática"))
                .andExpect(jsonPath("$.sku")
                        .value("DELL-002"))
                .andExpect(jsonPath("$.stockQuantity")
                        .value(20));

        verify(productService)
                .update(
                        eq(productId),
                        any(UpdateProductRequestDto.class)
                );
    }

    @Test
    void shouldReturnBadRequestWhenUpdateRequestIsInvalid()
            throws Exception {

        UpdateProductRequestDto invalidRequest =
                new UpdateProductRequestDto(
                        "",
                        "",
                        BigDecimal.ZERO,
                        "",
                        ""
                );

        mockMvc.perform(
                        put("/api/products/{id}", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(
                                        invalidRequest
                                ))
                )
                .andExpect(status().isBadRequest());

        verify(productService, never())
                .update(
                        any(UUID.class),
                        any(UpdateProductRequestDto.class)
                );
    }


    @Test
    void shouldRemoveProductSuccessfully()
            throws Exception {

        doNothing()
                .when(productService)
                .remove(productId);

        mockMvc.perform(
                        delete("/api/products/{id}", productId)
                )
                .andExpect(status().isNoContent());

        verify(productService)
                .remove(productId);
    }

    @Test
    void shouldPropagateExceptionWhenRemoveFails()
            throws Exception {

        doThrow(new ProductNotFoundException(productId))
                .when(productService)
                .remove(productId);

        mockMvc.perform(
                        delete("/api/products/{id}", productId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Product not found"))
                .andExpect(jsonPath("$.message")
                        .value("Product not found with id: " + productId));

        verify(productService)
                .remove(productId);
    }


    @Test
    void shouldActivateProductSuccessfully()
            throws Exception {

        when(productService.activate(productId))
                .thenReturn(productResponse);

        mockMvc.perform(
                        patch("/api/products/{id}/activate", productId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(productId.toString()))
                .andExpect(jsonPath("$.name")
                        .value("Notebook Dell"))
                .andExpect(jsonPath("$.sku")
                        .value("DELL-001"));

        verify(productService)
                .activate(productId);
    }

    @Test
    void shouldPropagateExceptionWhenActivationFails()
            throws Exception {

        when(productService.activate(productId))
                .thenThrow(
                        new ProductNotFoundException(productId)
                );

        mockMvc.perform(
                        patch("/api/products/{id}/activate", productId)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("Product not found"))
                .andExpect(jsonPath("$.message")
                        .value("Product not found with id: " + productId));

        verify(productService)
                .activate(productId);
    }
}