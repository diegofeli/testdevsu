package com.api.client.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PersonInfo")
public class ApiPersonInfo implements Serializable {

    private static final long serialVersionUID = -6262721006808757214L;

    @NotNull
    @Schema(description = "Person identity", required = true)
    private String identity;

    @Schema(description = "Phone of the Person")
    private String phone;

    @Schema(description = "Direction of the Person")
    private String direction;

    @Schema(description = "Age of the Person")
    private Integer age;

    @NotBlank
    @Schema(description = "Name of the Person", required = true)
    private String name;

    @NotNull
    @Schema(description = "Status of Person", required = true)
    private Boolean enabled;

    @Schema(description = "Country Code of Person")
    private Integer countryCode;

}
