package dev.zerphyis.orderflowsender.infra.controller.product;

import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
import dev.zerphyis.orderflowsender.infra.rabbit.RabbitMqProducer;
import dev.zerphyis.orderflowsender.infra.rabbit.StockReservedEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ProductResponseDto> create(@RequestBody ProductRequestDto requestDto) {
        ProductResponseDto created = productService.create(requestDto);

        rabbitMqProducer.sendStockReserved(
                new StockReservedEvent(created.id(), created.stockQuantity())
        );

        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.list(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(
            @PathVariable UUID id,
            @RequestBody UpdateProductRequestDto requestDto
    ) {
        return ResponseEntity.ok(productService.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable UUID id) {
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
