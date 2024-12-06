package codesumn.com.marketplace_backend.application.dtos.params;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterCriteriaParamDTO {

    private String searchTerm;
    private String role;
    private String sortField;
    private Boolean sortDescending;

    @Min(1)
    private Integer page = 1;

    @Min(1)
    private Integer pageSize = 10;
}
