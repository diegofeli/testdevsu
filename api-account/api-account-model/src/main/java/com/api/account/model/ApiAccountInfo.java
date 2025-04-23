package com.api.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "AccountInfo", description = "Account details for API operations")
public class ApiAccountInfo {

    @NotBlank
    @Schema(description = "Account number", required = true)
    private String number;

    @NotNull
    @Schema(description = "Initial balance of the account", required = true)
    private BigDecimal openingBalance;

    @NotNull
    @Schema(description = "Balance of the account", required = true)
    private BigDecimal balance;

    @Schema(description = "Optional description about the account")
    private String description;

    @NotNull
    @Schema(description = "Identity client who owns the account", required = true)
    private String clientIdentity;

    @NotNull
    @Schema(description = "the account type", required = true)
    private String typeAccount;

    @NotNull
    @Schema(description = "Whether the account is enabled")
    private Boolean enabled;
}
