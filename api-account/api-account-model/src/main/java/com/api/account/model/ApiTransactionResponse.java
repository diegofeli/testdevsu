package com.api.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "TransactionInfo", description = "Transaction details for API operations")
public class ApiTransactionResponse {

    @Schema(description = "Transaction id", required = true)
    private Integer id;

    @NotNull
    @Schema(description = "Total amount of the transaction", required = true)
    private BigDecimal totalAmount;

    @Schema(description = "Optional description of the transaction")
    private String description;

    @NotNull
    @Schema(description = "Transaction type ", required = true)
    private String transactionType;

    @Schema(description = "Whether the transaction is enabled", example = "true")
    private Boolean enabled = true;
}
