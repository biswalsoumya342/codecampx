package com.codecampx.codecampx.payload.codesnippet;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippetDto {

    private String id;

    @NotBlank(message = "Select Code Language")
    private String language;

    @NotBlank(message = "Code Must Required To Save")
    private String code;

    private boolean isShared;

    private LocalDateTime createdAt;

    private String shareLink;
}
