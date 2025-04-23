package com.api.account.model;

import com.api.persistence.domain.Client;
import com.api.persistence.domain.Transaction;
import com.api.persistence.domain.TypeAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AccountResponse", description = "Account details for API operations")
public class ApiAccountResponse {

    @Schema(description = "Account number")
    private String number;

    @Schema(description = "Initial balance of the account")
    private BigDecimal openingBalance;

    @Schema(description = "Balance of the account")
    private BigDecimal balance;

    @Schema(description = "Optional description about the account")
    private String description;

    @Schema(description = "Id client who owns the account")
    private ApiClientResponse client;

    @Schema(description = "List of transactions")
    private List<ApiTransactionResponse> transactionList;

    @Schema(description = "Account type")
    private String typeAccount;

    @Schema(description = "Whether the account is enabled")
    private Boolean enabled;
}
