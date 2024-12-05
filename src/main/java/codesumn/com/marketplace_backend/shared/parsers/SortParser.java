package codesumn.com.marketplace_backend.shared.parsers;

import codesumn.com.marketplace_backend.application.dtos.params.SortingParamDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SortParser {
    private final ObjectMapper objectMapper;

    public SortParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Sort parseSorting(String sorting) throws IOException {
        if (sorting == null || sorting.isEmpty()) {
            return Sort.unsorted();
        }

        List<SortingParamDTO> sortingParams = objectMapper.readValue(sorting, new TypeReference<>() {
        });
        List<Sort.Order> orders = sortingParams
                .stream()
                .map(param -> new Sort.Order(param.isDesc()
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC, param.getId()))
                .collect(Collectors.toList());
        return Sort.by(orders);
    }
}

