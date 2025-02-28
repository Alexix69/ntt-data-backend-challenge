package com.ntt_data.backend_challenge.client_person.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDTO {

    private Long id;

    @NotBlank
    private String name;

    private String gender;
    private Integer age;

    @NotBlank
    private String identification;

    private String address;
    private String phone;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank
    private String password;

    @NotNull
    private Boolean status;
}

