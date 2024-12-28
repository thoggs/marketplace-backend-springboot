package codesumn.com.marketplace_backend.application.controllers;

import codesumn.com.marketplace_backend.application.dtos.params.FilterCriteriaParamDTO;
import codesumn.com.marketplace_backend.domain.input.ProductServicePort;
import codesumn.com.marketplace_backend.application.dtos.record.ProductInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.ProductRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServicePort productServicePort;

    @Autowired
    public ProductController(ProductServicePort productServicePort) {
        this.productServicePort = productServicePort;
    }

    @GetMapping
    public ResponseEntity<PaginationResponseDto<List<ProductRecordDto>>> index(
            @Valid @ParameterObject @ModelAttribute FilterCriteriaParamDTO parameters
    ) throws IOException {
        return new ResponseEntity<>(productServicePort.getAll(
                parameters.getPage(),
                parameters.getPageSize(),
                parameters.getSearchTerm(),
                parameters.getSortField()
        ), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductRecordDto>> show(@PathVariable UUID id) {
        return new ResponseEntity<>(productServicePort.getProductById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<ProductRecordDto>> store(
            @RequestBody @Valid ProductInputRecordDto productInputRecordDto
    ) {
        return new ResponseEntity<>(productServicePort.createProduct(productInputRecordDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductRecordDto>> update(
            @PathVariable UUID id,
            @RequestBody @Valid ProductInputRecordDto productInputRecordDto
    ) {
        return new ResponseEntity<>(productServicePort.updateProduct(id, productInputRecordDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<ProductRecordDto>> destroy(@PathVariable UUID id) {
        return new ResponseEntity<>(productServicePort.deleteProduct(id), HttpStatus.OK);
    }
}
