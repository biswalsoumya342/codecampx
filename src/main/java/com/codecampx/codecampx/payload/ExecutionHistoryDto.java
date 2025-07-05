package com.codecampx.codecampx.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionHistoryDto {
    private String id;

    @NotBlank(message = "Choose A language For Execution")
    private  String language;

    @NotBlank(message = "Write Code")
    private String code;

    private String output;

    private boolean status;

    private LocalDateTime createdAt;
}
