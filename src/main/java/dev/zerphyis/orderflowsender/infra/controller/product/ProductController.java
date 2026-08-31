package dev.zerphyis.orderflowsender.infra.controller.product;

import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
import dev.zerphyis.orderflowsender.infra.rabbit.RabbitMqProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final RabbitMqProducer rabbitMqProducer;

    public ProductController(
            ProductService productService,
            RabbitMqProducer rabbitMqProducer
    ) {
        this.productService = productService;
        this.rabbitMqProducer = rabbitMqProducer;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> create(
            @Valid @RequestBody ProductRequestDto requestDto
    ) {
        ProductResponseDto created = productService.create(requestDto);

        return ResponseEntity
                .status(201)
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                productService.findById(id)
        );
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> findByCategory(
            @PathVariable String category
    ) {
        return ResponseEntity.ok(
                productService.findByCategory(category)
        );
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductResponseDto> findBySku(
            @PathVariable String sku
    ) {
        return ResponseEntity.ok(
                productService.findBySku(sku)
        );
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(
                productService.list(page, size)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateProductRequestDto requestDto
    ) {
        return ResponseEntity.ok(
                productService.update(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(
            @PathVariable UUID id
    ) {
        productService.remove(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProductResponseDto> activate(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                productService.activate(id)
        );
    }
}