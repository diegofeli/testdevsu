package com.api.client.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ClientInfo")
public class ApiClientInfo extends  ApiPersonInfo implements Serializable {

    private static final long serialVersionUID = -6262721006808757214L;

    @NotBlank
    @Schema(description = "Password of the Client", required = true)
    private String password;

}
