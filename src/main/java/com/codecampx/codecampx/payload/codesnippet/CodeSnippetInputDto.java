package com.codecampx.codecampx.payload.codesnippet;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippetInputDto {

    @NotBlank(message = "Select Language")
    private  String language;

    @NotBlank(message = "Write Code For Execution")
    private String code;

    @NotBlank(message = "Description Required")
    private String description;
}
