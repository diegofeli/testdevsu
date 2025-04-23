package com.api.account.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "StatementInfo", description = "Statement details for API operations")
public class ApiStatementInfo {

    @NotNull
    @Schema(description = "Identity of the client", required = true)
    private String identity;

    @NotNull
    @Schema(description = "Initial date to get information")
    private Date initialDate;

    @NotNull
    @Schema(description = "Initial date to get information")
    private Date finalDate;
}
