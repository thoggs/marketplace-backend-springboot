package codesumn.com.marketplace_backend.application.adapters.input;

import codesumn.com.marketplace_backend.application.dtos.record.MetadataPaginationRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.ProductInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.ProductRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import codesumn.com.marketplace_backend.domain.input.ProductServicePort;
import codesumn.com.marketplace_backend.domain.models.ProductModel;
import codesumn.com.marketplace_backend.domain.output.ProductPersistencePort;
import codesumn.com.marketplace_backend.infrastructure.adapters.persistence.specifications.ProductSpecifications;
import codesumn.com.marketplace_backend.shared.exceptions.errors.ResourceNotFoundException;
import codesumn.com.marketplace_backend.shared.parsers.SortParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceAdapter implements ProductServicePort {
    private final ProductPersistencePort productPersistencePort;
    private final SortParser sortParser;

    @Autowired
    public ProductServiceAdapter(ProductPersistencePort productPersistencePort, SortParser sortParser) {
        this.productPersistencePort = productPersistencePort;
        this.sortParser = sortParser;
    }

    @Override
    public PaginationResponseDto<List<ProductRecordDto>> getAll(
            int page,
            int pageSize,
            String searchTerm,
            String sorting
    ) throws IOException {
        String decodedSorting = (sorting != null && !sorting.trim().isEmpty() && !"[]".equals(sorting))
                ? URLDecoder.decode(sorting, StandardCharsets.UTF_8)
                : null;

        Sort sort = (decodedSorting != null && !decodedSorting.trim().isEmpty() && !"[]".equals(decodedSorting))
                ? sortParser.parseSorting(decodedSorting)
                : Sort.by(Sort.Order.asc("name"));

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        Specification<ProductModel> spec = (searchTerm != null && !searchTerm.trim().isEmpty())
                ? ProductSpecifications.searchWithTerm(searchTerm)
                : null;

        Page<ProductModel> productPage = (spec != null)
                ? productPersistencePort.findAllWithSpecAndPageable(spec, pageable)
                : productPersistencePort.findAllWithPageable(pageable);

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

        return PaginationResponseDto.create(productRecords, metadata);
    }

    @Override
    public ResponseDto<ProductRecordDto> getProductById(UUID id) {
        ProductModel product = productPersistencePort.findById(id)
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

        return ResponseDto.create(productRecord);
    }

    @Override
    public ResponseDto<ProductRecordDto> createProduct(ProductInputRecordDto input) {
        ProductModel newProduct = new ProductModel(input);

        productPersistencePort.save(newProduct);

        ProductRecordDto productRecord = new ProductRecordDto(
                newProduct.getId(),
                newProduct.getName(),
                newProduct.getDescription(),
                newProduct.getCategory(),
                newProduct.getPrice(),
                newProduct.getImage(),
                newProduct.getStock()
        );

        return ResponseDto.create(productRecord);
    }

    @Override
    public ResponseDto<ProductRecordDto> updateProduct(UUID id, ProductInputRecordDto input) {
        ProductModel existingProduct = productPersistencePort.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        existingProduct.setName(input.name());
        existingProduct.setDescription(input.description());
        existingProduct.setCategory(input.category());
        existingProduct.setPrice(input.price());
        existingProduct.setImage(input.image());
        existingProduct.setStock(input.stock());

        productPersistencePort.save(existingProduct);

        ProductRecordDto updatedProductRecord = new ProductRecordDto(
                existingProduct.getId(),
                existingProduct.getName(),
                existingProduct.getDescription(),
                existingProduct.getCategory(),
                existingProduct.getPrice(),
                existingProduct.getImage(),
                existingProduct.getStock()
        );

        return ResponseDto.create(updatedProductRecord);
    }

    @Override
    public ResponseDto<ProductRecordDto> deleteProduct(UUID id) {
        ProductModel product = productPersistencePort.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        productPersistencePort.delete(product);

        ProductRecordDto productRecord = new ProductRecordDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getImage(),
                product.getStock()
        );

        return ResponseDto.create(productRecord);
    }
}
