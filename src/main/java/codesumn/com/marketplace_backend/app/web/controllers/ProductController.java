package codesumn.com.marketplace_backend.app.web.controllers;

import codesumn.com.marketplace_backend.app.models.ProductModel;
import codesumn.com.marketplace_backend.dtos.query.ProductSpecificationsDto;
import codesumn.com.marketplace_backend.dtos.record.MetadataPaginationRecordDto;
import codesumn.com.marketplace_backend.dtos.record.ProductInputRecordDto;
import codesumn.com.marketplace_backend.dtos.record.ProductRecordDto;
import codesumn.com.marketplace_backend.dtos.response.PaginationDto;
import codesumn.com.marketplace_backend.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.exceptions.ResourceNotFoundException;
import codesumn.com.marketplace_backend.repository.ProductRepository;
import codesumn.com.marketplace_backend.shared.parsers.SortParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final SortParser sortParser;

    public ProductController(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.sortParser = new SortParser(objectMapper);
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDto<List<ProductRecordDto>>> index(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) String sorting
    ) throws IOException {
        String decodedSorting = (sorting != null && !sorting.trim().isEmpty() && !"[]".equals(sorting))
                ? URLDecoder.decode(sorting, StandardCharsets.UTF_8)
                : null;

        Sort sort = (decodedSorting != null && !decodedSorting.trim().isEmpty() && !"[]".equals(decodedSorting))
                ? sortParser.parseSorting(decodedSorting)
                : Sort.by(Sort.Order.asc("name"));

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        Specification<ProductModel> spec = (searchTerm != null && !searchTerm.trim().isEmpty())
                ? ProductSpecificationsDto.searchWithTerm(searchTerm)
                : null;

        Page<ProductModel> productPage = (spec != null)
                ? productRepository.findAll(spec, pageable)
                : productRepository.findAll(pageable);

        List<ProductRecordDto> productRecords = productPage.getContent().stream()
                .map(product -> new ProductRecordDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getPrice(),
                        product.getImage(),
                        product.getStock()
                ))
                .collect(Collectors.toList());

        PaginationDto pagination = new PaginationDto(
                productPage.getNumber() + 1,
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages()
        );

        MetadataPaginationRecordDto metadata = new MetadataPaginationRecordDto(
                pagination,
                Collections.emptyList()
        );

        PaginationResponseDto<List<ProductRecordDto>> response = PaginationResponseDto.create(productRecords, metadata);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductRecordDto>> show(@PathVariable UUID id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        ProductRecordDto productRecord = new ProductRecordDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getImage(),
                product.getStock()
        );

        ResponseDto<ProductRecordDto> response = ResponseDto.create(productRecord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<ProductRecordDto>> store(
            @RequestBody @Valid ProductInputRecordDto productInputRecordDto
    ) {
        ProductModel newProduct = new ProductModel(productInputRecordDto);

        productRepository.save(newProduct);

        ProductRecordDto productRecord = new ProductRecordDto(
                newProduct.getId(),
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getCategory(),
                newProduct.getPrice(),
                newProduct.getImage(),
                newProduct.getStock()
        );

        ResponseDto<ProductRecordDto> response = ResponseDto.create(productRecord);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductRecordDto>> update(
            @PathVariable UUID id,
            @RequestBody @Valid ProductInputRecordDto productInputRecordDto
    ) {
        ProductModel existingProduct = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        existingProduct.setName(productInputRecordDto.name());
        existingProduct.setDescription(productInputRecordDto.description());
        existingProduct.setCategory(productInputRecordDto.category());
        existingProduct.setPrice(productInputRecordDto.price());
        existingProduct.setImage(productInputRecordDto.image());
        existingProduct.setStock(productInputRecordDto.stock());

        productRepository.save(existingProduct);

        ProductRecordDto updatedProductRecord = new ProductRecordDto(
                existingProduct.getId(),
                existingProduct.getName(),
                existingProduct.getDescription(),
                existingProduct.getCategory(),
                existingProduct.getPrice(),
                existingProduct.getImage(),
                existingProduct.getStock()
        );

        ResponseDto<ProductRecordDto> response = ResponseDto.create(updatedProductRecord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductRecordDto>> destroy(@PathVariable UUID id) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        productRepository.delete(product);

        ProductRecordDto productRecord = new ProductRecordDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getImage(),
                product.getStock()
        );

        ResponseDto<ProductRecordDto> response = ResponseDto.create(productRecord);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
