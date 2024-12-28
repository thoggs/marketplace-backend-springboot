package codesumn.com.marketplace_backend.domain.input;

import codesumn.com.marketplace_backend.application.dtos.record.ProductInputRecordDto;
import codesumn.com.marketplace_backend.application.dtos.record.ProductRecordDto;
import codesumn.com.marketplace_backend.application.dtos.response.PaginationResponseDto;
import codesumn.com.marketplace_backend.application.dtos.response.ResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ProductServicePort {
    PaginationResponseDto<List<ProductRecordDto>> getAll(
            int page,
            int pageSize,
            String searchTerm,
            String sorting
    ) throws IOException;

    ResponseDto<ProductRecordDto> getProductById(UUID id);

    ResponseDto<ProductRecordDto> createProduct(ProductInputRecordDto input);

    ResponseDto<ProductRecordDto> updateProduct(UUID id, ProductInputRecordDto input);

    ResponseDto<ProductRecordDto> deleteProduct(UUID id);
}
