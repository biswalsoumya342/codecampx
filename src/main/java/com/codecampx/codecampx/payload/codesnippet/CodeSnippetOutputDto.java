package com.codecampx.codecampx.payload.codesnippet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippetOutputDto {

    @JsonIgnore
    private String id;

    private String language;

    private String code;

    private String description;
}
