package com.api.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StatementResponse", description = "Statement details for API operations")
public class ApiStatementResponse {

    @Schema(description = "Account", required = true)
    private List<ApiAccountResponse> accountResponses;

}
