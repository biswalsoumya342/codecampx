package com.codecampx.codecampx.payload.codesnippet;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippetDescriptionInputDto {

    @NotBlank(message = "Description Required")
    private String description;
}
