package dev.zerphyis.orderflowsender.infra.controller.product;

import dev.zerphyis.orderflowsender.aplication.exceptions.SkuNotFoundException;
import dev.zerphyis.orderflowsender.domain.interfaceCases.*;
import dev.zerphyis.orderflowsender.infra.dtos.ProductRequestDto;
import dev.zerphyis.orderflowsender.infra.dtos.ProductResponseDto;
import dev.zerphyis.orderflowsender.infra.dtos.UpdateProductRequestDto;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final CreateProductInterfaceCase createProductUseCase;
    private final UpdateProductInterfaceCase updateProductUseCase;
    private final RemoveProductInterfaceCase removeProductUseCase;
    private final FindByProductCategoryInterfaceCase findProductsByCategoryUseCase;
    private final FindBySkuProductInterfaceCase findProductBySkuUseCase;
    private final ActiveProducInterfaceCase activateProductUseCase;
    private final ListProductInterfaceCase listProductUseCase;
    private final FindByIdProductInterfaceCase findByIdProductUseCase;

    public ProductService(CreateProductInterfaceCase createProductUseCase, UpdateProductInterfaceCase updateProductUseCase, RemoveProductInterfaceCase removeProductUseCase, FindByProductCategoryInterfaceCase findProductsByCategoryUseCase, FindBySkuProductInterfaceCase findProductBySkuUseCase, ActiveProducInterfaceCase activateProductUseCase, ListProductInterfaceCase listProductUseCase, FindByIdProductInterfaceCase findByIdProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.removeProductUseCase = removeProductUseCase;
        this.findProductsByCategoryUseCase = findProductsByCategoryUseCase;
        this.findProductBySkuUseCase = findProductBySkuUseCase;
        this.activateProductUseCase = activateProductUseCase;
        this.listProductUseCase = listProductUseCase;
        this.findByIdProductUseCase = findByIdProductUseCase;
    }

    @CacheEvict(value = "productsList", allEntries = true)
    public ProductResponseDto create(ProductRequestDto requestDto) {
        return createProductUseCase.execute(requestDto);
    }

    @Cacheable(value = "product", key = "#id")
    public ProductResponseDto findById(UUID id) {
        return findByIdProductUseCase.execute(id);
    }

    @Cacheable(value = "productsList", key = "#page + '-' + #size")
    public List<ProductResponseDto> list(int page, int size) {
        return listProductUseCase.execute(page, size);
    }

    @Caching(put = {@CachePut(value = "product", key = "#id")}, evict = {@CacheEvict(value = "productsList", allEntries = true)})
    public ProductResponseDto update(UUID id, UpdateProductRequestDto requestDto) {
        return updateProductUseCase.execute(id, requestDto);
    }

    @Caching(evict = {@CacheEvict(value = "product", key = "#id"), @CacheEvict(value = "productsList", allEntries = true)})
    public void remove(UUID id) {
        removeProductUseCase.execute(id);
    }


    @Cacheable(value = "productsByCategory", key = "#category")
    public List<ProductResponseDto> findByCategory(String category) {
        return findProductsByCategoryUseCase.execute(category);
    }

    @Cacheable(value = "productBySku", key = "#sku")
    public ProductResponseDto findBySku(String sku) {
        return findProductBySkuUseCase.execute(sku).orElseThrow(() -> new SkuNotFoundException(sku));
    }

    @Caching(put = {@CachePut(value = "product", key = "#id")}, evict = {@CacheEvict(value = "productsList", allEntries = true), @CacheEvict(value = "productsByCategory", allEntries = true), @CacheEvict(value = "productBySku", allEntries = true)})
    public ProductResponseDto activate(UUID id) {
        return activateProductUseCase.execute(id);
    }

}